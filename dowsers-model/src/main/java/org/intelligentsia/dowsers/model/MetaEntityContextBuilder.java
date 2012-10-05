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
import java.util.Collection;
import java.util.HashSet;

import org.intelligentsia.dowsers.core.Builder;
import org.intelligentsia.keystone.api.artifacts.Version;

import com.google.common.base.Preconditions;

/**
 * {@link MetaEntityContextBuilder} implements builder pattern for
 * {@link MetaEntityContext}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class MetaEntityContextBuilder implements Builder<MetaEntityContext> {

	private String name;
	private Version rootVersion;
	private Collection<MetaProperty> metaProperties;
	private Collection<MetaEntityDefinition> extendedMetaEntityDefinitions;

	/**
	 * 
	 * Build a new instance of MetaEntityContextBuilder.java.
	 * 
	 * @param name
	 * @param rootVersion
	 * @throws NullPointerException
	 *             if name or rootVersionis null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 */
	public MetaEntityContextBuilder(String name, Version rootVersion) throws NullPointerException, IllegalArgumentException {
		this(name, rootVersion, null, null);

	}

	/**
	 * 
	 * Build a new instance of MetaEntityContextBuilder.java.
	 * 
	 * @param name
	 * @param rootVersion
	 * @param metaProperties
	 * @param extendedMetaEntityDefinitions
	 *            * @throws NullPointerException if name or rootVersionis null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 */
	public MetaEntityContextBuilder(String name, Version rootVersion, Collection<MetaProperty> metaProperties, Collection<MetaEntityDefinition> extendedMetaEntityDefinitions) throws NullPointerException, IllegalArgumentException {
		super();
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		this.name = name;
		this.rootVersion = Preconditions.checkNotNull(rootVersion);
		this.metaProperties = (metaProperties != null ? metaProperties : new HashSet<MetaProperty>());
		this.extendedMetaEntityDefinitions = extendedMetaEntityDefinitions != null ? extendedMetaEntityDefinitions : new HashSet<MetaEntityDefinition>();
	}

	@Override
	public MetaEntityContext build() {
		return new MetaEntityContextDefinition(name, rootVersion, metaProperties, extendedMetaEntityDefinitions);
	}

	/**
	 * 
	 * @param metaProperties
	 * @return this {@link MetaEntityContextBuilder} instance
	 * @throws NullPointerException
	 *             if metaProperties is null
	 */
	public MetaEntityContextBuilder setMetaProperties(Collection<MetaProperty> metaProperties) throws NullPointerException {
		this.metaProperties = Preconditions.checkNotNull(metaProperties);
		return this;
	}

	public MetaEntityContextBuilder add(MetaProperty metaProperty) throws NullPointerException {
		this.metaProperties.add(metaProperty);
		return this;
	}

	public MetaEntityContextBuilder add(MetaProperty... metaProperties) throws NullPointerException {
		this.metaProperties.addAll(Arrays.asList(Preconditions.checkNotNull(metaProperties)));
		return this;
	}

	/**
	 * 
	 * @param extendedMetaEntityDefinitions
	 * @return this {@link MetaEntityContextBuilder} instance
	 */
	public MetaEntityContextBuilder setExtendedMetaEntityDefinitions(Collection<MetaEntityDefinition> extendedMetaEntityDefinitions) {
		return this;
	}

	/**
	 * Add an extended {@link MetaEntityDefinition}.
	 * 
	 * @param version
	 * @return {@link MetaEntityDefinitionBuilder} instance.
	 */
	public MetaEntityDefinitionBuilder add(Version version) {
		return new MetaEntityDefinitionBuilder(name, version, this);
	}

	/**
	 * Add an extended {@link MetaEntityDefinition}.
	 * 
	 * @param definition
	 * @return this {@link MetaEntityContextBuilder} instance
	 * @throws NullPointerException
	 *             if definition is null
	 */
	public MetaEntityContextBuilder add(MetaEntityDefinition definition) throws NullPointerException {
		Preconditions.checkState(name.equals(Preconditions.checkNotNull(definition).getName()));
		extendedMetaEntityDefinitions.add(definition);
		return this;
	}
}
