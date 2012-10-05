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
package org.intelligentsia.dowsers.model;

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
public class MetaEntityDefinitionBuilder implements Builder<MetaEntityDefinition>, Iterable<MetaProperty> {

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
	private Set<MetaProperty> metaProperties;

	private final MetaEntityContextBuilder metaEntityContextBuilder;

	/**
	 * Build a new instance of MetaEntityDefinitionBuilder.java.
	 * 
	 * @param metaEntityDefinition
	 *            base definition.
	 */
	public MetaEntityDefinitionBuilder(final MetaEntityDefinition metaEntityDefinition) {
		this(metaEntityDefinition.getName(), metaEntityDefinition.getVersion(), Sets.newHashSet(metaEntityDefinition.getMetaProperties()));
	}

	/**
	 * Build a new instance of MetaEntityDefinitionBuilder.java.
	 * 
	 * @param name
	 * @param version
	 * @param metaProperties
	 * @throws NullPointerException
	 *             if one of parameters is null
	 */
	public MetaEntityDefinitionBuilder(final String name, final Version version, final Set<MetaProperty> metaProperties) throws NullPointerException {
		super();
		this.name = Preconditions.checkNotNull(name);
		this.version = Preconditions.checkNotNull(version);
		this.metaProperties = Preconditions.checkNotNull(metaProperties);
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
	 *             if name, version, metaProperties is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 */
	@Override
	public MetaEntityDefinition build() throws NullPointerException, IllegalArgumentException {
		final MetaEntityDefinition definition = new MetaEntityDefinition(name, version, metaProperties);
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

	public Set<MetaProperty> getMetaProperties() {
		return metaProperties;
	}

	public MetaEntityDefinitionBuilder setMetaProperties(final Set<MetaProperty> metaProperties) throws NullPointerException {
		this.metaProperties = Preconditions.checkNotNull(metaProperties);
		return this;
	}

	public MetaEntityDefinitionBuilder add(final MetaProperty metaProperty) throws NullPointerException {
		this.metaProperties.add(metaProperty);
		return this;
	}

	public MetaEntityDefinitionBuilder add(final MetaProperty... metaProperties) throws NullPointerException {
		this.metaProperties.addAll(Arrays.asList(Preconditions.checkNotNull(metaProperties)));
		return this;
	}

	@Override
	public Iterator<MetaProperty> iterator() {
		return metaProperties.iterator();
	}

}
