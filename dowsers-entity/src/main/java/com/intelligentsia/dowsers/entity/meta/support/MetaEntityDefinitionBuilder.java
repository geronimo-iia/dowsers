/**
 *        Licensed to the Apache Software Foundation (ASF) under one
 *        or more contributor license agreements.  See the NOTICE file
 *        distributed with this work for additional information
 *        regarding copyright ownership.  The ASF licenses this file
 *        to you under the Apache License, Version 2.0 (the
 *        "License"); you may not use this file except in compliance
 *        with the License.  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing,
 *        software distributed under the License is distributed on an
 *        "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *        KIND, either express or implied.  See the License for the
 *        specific language governing permissions and limitations
 *        under the License.
 *
 */
package com.intelligentsia.dowsers.entity.meta.support;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.intelligentsia.dowsers.core.Builder;
import org.intelligentsia.dowsers.core.reflection.ClassInformation;
import org.intelligentsia.keystone.api.artifacts.Version;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.intelligentsia.dowsers.entity.meta.MetaAttribute;

/**
 * MetaEntityDefinitionBuilder implements {@link Builder} for
 * {@link MetaEntityDefinition}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MetaEntityDefinitionBuilder implements Builder<MetaEntityDefinition>, Iterable<MetaAttribute> {

	/**
	 * Meta entity name.
	 */
	private String name;

	/**
	 * Meta entity version.
	 */
	private Version version;
	/**
	 * Set of meta properties.
	 */
	private Collection<MetaAttribute> metaAttributes = Sets.newLinkedHashSet();

	/**
	 * {@link MetaEntityContextBuilder} instance.
	 */
	private final MetaEntityContextBuilder metaEntityContextBuilder;

	/**
	 * Build a new instance of MetaEntityDefinitionBuilder.java.
	 * 
	 * @param metaEntityDefinition
	 *            base definition to copy.
	 */
	public MetaEntityDefinitionBuilder(final MetaEntityDefinition metaEntityDefinition) {
		this(metaEntityDefinition.name(), metaEntityDefinition.version(), metaEntityDefinition.metaAttributes());
	}

	/**
	 * Build a new instance of MetaEntityDefinitionBuilder.java.
	 * 
	 * @param name
	 *            entity class name
	 * @param version
	 *            entity version
	 * @param metaAttributes
	 *            a {@link Collection} of {@link MetaAttribute}.
	 * @throws NullPointerException
	 *             if one of parameters is null
	 */
	public MetaEntityDefinitionBuilder(final String name, final Version version, final Collection<MetaAttribute> metaAttributes) throws NullPointerException {
		super();
		this.name = Preconditions.checkNotNull(name);
		this.version = Preconditions.checkNotNull(version);
		this.metaAttributes = Preconditions.checkNotNull(metaAttributes);
		metaEntityContextBuilder = null;
	}

	/**
	 * Build a new instance of MetaEntityDefinitionBuilder.java.
	 */
	public MetaEntityDefinitionBuilder() {
		super();
		metaEntityContextBuilder = null;
	}

	/**
	 * Build a new instance of MetaEntityDefinitionBuilder.java.
	 * 
	 * @param name
	 *            entity class name
	 * @param version
	 *            entity version
	 * @param metaEntityContextBuilder
	 *            {@link MetaEntityContextBuilder} instance.
	 * @throws NullPointerException
	 *             if one of parameters is null
	 */
	MetaEntityDefinitionBuilder(final String name, final Version version, final MetaEntityContextBuilder metaEntityContextBuilder) throws NullPointerException {
		this.name = Preconditions.checkNotNull(name);
		this.version = Preconditions.checkNotNull(version);
		this.metaEntityContextBuilder = metaEntityContextBuilder;
	}

	/**
	 * @see {@link MetaEntityDefinition#MetaEntityDefinition(String, Version, java.util.Collection) }
	 * @see org.intelligentsia.dowsers.core.Builder#build()
	 * 
	 * @throws NullPointerException
	 *             if name, version, metaAttributes is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 */
	@Override
	public MetaEntityDefinition build() throws NullPointerException, IllegalArgumentException {
		final MetaEntityDefinition definition = new MetaEntityDefinition(name, version, metaAttributes);
		if (metaEntityContextBuilder != null) {
			metaEntityContextBuilder.metaEntityDefinitions(definition);
		}
		return definition;
	}

	/**
	 * @return name.
	 */
	public String name() {
		return name;
	}

	/**
	 * Set name.
	 * 
	 * @param name
	 * @return this {@link MetaEntityDefinitionBuilder} instance.
	 * @throws NullPointerException
	 *             if name is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 */
	public MetaEntityDefinitionBuilder name(final String name) throws NullPointerException, IllegalArgumentException {
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		this.name = name;
		return this;
	}

	/**
	 * @return {@link Version}
	 */
	public Version version() {
		return version;
	}

	/**
	 * Set version.
	 * 
	 * @param version
	 * @return this {@link MetaEntityDefinitionBuilder} instance.
	 * @throws NullPointerException
	 *             if version is null
	 */
	public MetaEntityDefinitionBuilder version(final Version version) throws NullPointerException {
		this.version = Preconditions.checkNotNull(version);
		return this;
	}

	/**
	 * @return {@link Collection} of {@link MetaAttribute}.
	 */
	public Collection<MetaAttribute> metaAttributes() {
		return metaAttributes;
	}

	/**
	 * Add a set of {@link MetaAttribute}.
	 * 
	 * @param metaAttributes
	 *            {@link Collection} of {@link MetaAttribute} to add.
	 * @return this {@link MetaEntityDefinitionBuilder} instance.
	 * @throws NullPointerException
	 *             if metaAttributes is null
	 */
	public MetaEntityDefinitionBuilder metaAttributes(final Collection<MetaAttribute> metaAttributes) throws NullPointerException {
		this.metaAttributes = Preconditions.checkNotNull(metaAttributes);
		return this;
	}

	/**
	 * Add a set of {@link MetaAttribute}.
	 * 
	 * @param metaAttributes
	 *            {@link Collection} of {@link MetaAttribute} to add.
	 * @return this {@link MetaEntityDefinitionBuilder} instance.
	 * @throws NullPointerException
	 *             if metaAttributes is null
	 */
	public MetaEntityDefinitionBuilder metaAttributes(final MetaAttribute... metaAttributes) throws NullPointerException {
		this.metaAttributes.addAll(Arrays.asList(Preconditions.checkNotNull(metaAttributes)));
		return this;
	}

	/**
	 * Add a new instance of <code>MetaAttributeDefinition</code>.
	 * 
	 * @param name
	 *            attribute name
	 * @param valueClass
	 *            value class
	 * @param defaultValue
	 *            default value
	 * @return this {@link MetaEntityDefinitionBuilder} instance.
	 * @throws NullPointerException
	 *             if name or valueClass is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 * @throws IllegalStateException
	 *             if value is not assignable to specified value class
	 */
	public MetaEntityDefinitionBuilder metaAttribute(final String name, final ClassInformation valueClass, final Object defaultValue) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		this.metaAttributes.add(new MetaAttributeDefinition(name, valueClass, defaultValue));
		return this;
	}

	/**
	 * Add a new instance of <code>MetaAttributeDefinition</code>.
	 * 
	 * @param name
	 *            attribute name
	 * @param valueClass
	 *            value class
	 * @param defaultValue
	 *            default value
	 * @return this {@link MetaEntityDefinitionBuilder} instance.
	 * @throws NullPointerException
	 *             if name or valueClass is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 * @throws IllegalStateException
	 *             if value is not assignable to specified value class
	 */
	public MetaEntityDefinitionBuilder metaAttribute(final String name, final Class<?> valueClass, final Object defaultValue) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		this.metaAttributes.add(new MetaAttributeDefinition(name, valueClass, defaultValue));
		return this;
	}

	/**
	 * Add a new instance of <code>MetaAttributeDefinition</code>.
	 * 
	 * @param name
	 *            attribute name
	 * @param valueClass
	 *            value class
	 * @return this {@link MetaEntityContextBuilder} instance.
	 * @throws NullPointerException
	 *             if name or valueClass is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 */
	public MetaEntityDefinitionBuilder metaAttribute(final String name, final ClassInformation valueClass) throws NullPointerException, IllegalArgumentException {
		this.metaAttributes(new MetaAttributeDefinition(name, valueClass, null));
		return this;
	}

	/**
	 * Add a new instance of <code>MetaAttributeDefinition</code>.
	 * 
	 * @param name
	 *            attribute name
	 * @param valueClass
	 *            value class
	 * @return this {@link MetaEntityContextBuilder} instance.
	 * @throws NullPointerException
	 *             if name or valueClass is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 */
	public MetaEntityDefinitionBuilder metaAttribute(final String name, final Class<?> valueClass) throws NullPointerException, IllegalArgumentException {
		this.metaAttributes(new MetaAttributeDefinition(name, valueClass, null));
		return this;
	}

	@Override
	public Iterator<MetaAttribute> iterator() {
		return metaAttributes.iterator();
	}

}
