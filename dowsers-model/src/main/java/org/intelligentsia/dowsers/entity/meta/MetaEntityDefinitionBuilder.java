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
package org.intelligentsia.dowsers.entity.meta;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import org.intelligentsia.dowsers.core.Builder;
import org.intelligentsia.keystone.api.artifacts.Version;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

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
	private Set<MetaAttribute> metaAttributes = Sets.newLinkedHashSet();

	/**
	 * {@link MetaEntityContextBuilder} instance.
	 */
	private final MetaEntityContextBuilder metaEntityContextBuilder;

	/**
	 * Build a new instance of MetaEntityDefinitionBuilder.java.
	 * 
	 * @param metaEntityDefinition
	 *            base definition.
	 */
	public MetaEntityDefinitionBuilder(final MetaEntityDefinition metaEntityDefinition) {
		this(metaEntityDefinition.getName(), metaEntityDefinition.getVersion(), Sets.newLinkedHashSet(new Iterable<MetaAttribute>() {
			@Override
			public Iterator<MetaAttribute> iterator() {
				return metaEntityDefinition.getMetaAttributes();
			}
		}));
	}

	/**
	 * Build a new instance of MetaEntityDefinitionBuilder.java.
	 * 
	 * @param name
	 * @param version
	 * @param metaAttributes
	 * @throws NullPointerException
	 *             if one of parameters is null
	 */
	public MetaEntityDefinitionBuilder(final String name, final Version version, final Set<MetaAttribute> metaProperties) throws NullPointerException {
		super();
		this.name = Preconditions.checkNotNull(name);
		this.version = Preconditions.checkNotNull(version);
		this.metaAttributes = Preconditions.checkNotNull(metaProperties);
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
	 * @param version
	 * @param metaEntityContextBuilder
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
			metaEntityContextBuilder.add(definition);
		}
		return definition;
	}

	/**
	 * 
	 * @param name
	 * @throws NullPointerException
	 *             if name is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 */
	public MetaEntityDefinitionBuilder setName(final String name) throws NullPointerException, IllegalArgumentException {
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		this.name = name;
		return this;
	}

	public MetaEntityDefinitionBuilder setVersion(final Version version) throws NullPointerException {
		this.version = Preconditions.checkNotNull(version);
		return this;
	}

	public Set<MetaAttribute> getMetaAttributes() {
		return metaAttributes;
	}

	public MetaEntityDefinitionBuilder setMetaAttributes(final Set<MetaAttribute> metaAttributes) throws NullPointerException {
		this.metaAttributes = Preconditions.checkNotNull(metaAttributes);
		return this;
	}

	public MetaEntityDefinitionBuilder add(final MetaAttribute metaAttribute) throws NullPointerException {
		this.metaAttributes.add(metaAttribute);
		return this;
	}

	public MetaEntityDefinitionBuilder add(final MetaAttribute... metaAttributes) throws NullPointerException {
		this.metaAttributes.addAll(Arrays.asList(Preconditions.checkNotNull(metaAttributes)));
		return this;
	}

	@Override
	public Iterator<MetaAttribute> iterator() {
		return metaAttributes.iterator();
	}

}
