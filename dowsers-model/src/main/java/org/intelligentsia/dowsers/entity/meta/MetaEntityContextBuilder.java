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

import java.util.Collection;
import java.util.LinkedHashSet;

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

	private final MetaEntityDefinitionBuilder metaEntityDefinitionBuilder;

	private final Collection<MetaEntityDefinition> extendedMetaEntityDefinitions;

	/**
	 * 
	 * Build a new instance of MetaEntityContextBuilder.java.
	 * 
	 * @param name
	 *            entity name
	 * @param version
	 *            entity meta definition version
	 * @throws NullPointerException
	 *             if name or rootVersionis null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 */
	public MetaEntityContextBuilder(final String name, final Version version) throws NullPointerException, IllegalArgumentException {
		this(name, version, new LinkedHashSet<MetaAttribute>(), null);

	}

	/**
	 * Build a new instance of MetaEntityContextBuilder.java.
	 * 
	 * @param name
	 *            entity name
	 * @param version
	 *            entity meta definition version
	 * @param metaAttributes
	 *            collection of {@link MetaAttribute} define by version
	 * @param extendedMetaEntityDefinitions
	 *            a {@link Collection} of {@link MetaEntityDefinition} which
	 *            compose this context
	 * 
	 * @throws NullPointerException
	 *             if name, version, metaProperties is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 */
	public MetaEntityContextBuilder(final String name, final Version version, final Collection<MetaAttribute> metaAttributes, final Collection<MetaEntityDefinition> extendedMetaEntityDefinitions) throws NullPointerException, IllegalArgumentException {
		super();
		metaEntityDefinitionBuilder = new MetaEntityDefinitionBuilder(name, version, metaAttributes);
		this.extendedMetaEntityDefinitions = extendedMetaEntityDefinitions != null ? extendedMetaEntityDefinitions : new LinkedHashSet<MetaEntityDefinition>();
	}

	@Override
	public MetaEntityContext build() {
		return new MetaEntityContextDefinition(metaEntityDefinitionBuilder.name(), metaEntityDefinitionBuilder.version(), metaEntityDefinitionBuilder.metaAttributes(), extendedMetaEntityDefinitions);
	}

	/**
	 * @param metaAttributes
	 *            a {@link Collection} of {@link MetaAttribute} to set
	 * @return this {@link MetaEntityContextBuilder} instance
	 * @throws NullPointerException
	 *             if metaAttributes is null
	 */
	public MetaEntityContextBuilder metaAttributes(final Collection<MetaAttribute> metaAttributes) throws NullPointerException {
		this.metaEntityDefinitionBuilder.metaAttributes(Preconditions.checkNotNull(metaAttributes));
		return this;
	}

	/**
	 * @param metaAttributes
	 *            a {@link Collection} of {@link MetaAttribute} to set
	 * @return this {@link MetaEntityContextBuilder} instance
	 * @throws NullPointerException
	 *             if metaAttributes is null
	 */
	public MetaEntityContextBuilder metaAttributes(final MetaAttribute... metaAttributes) throws NullPointerException {
		this.metaEntityDefinitionBuilder.metaAttributes(Preconditions.checkNotNull(metaAttributes));
		return this;
	}

	/**
	 * Add a {@link Collection} of {@link MetaEntityDefinition}.
	 * 
	 * @param extendedMetaEntityDefinitions
	 *            {@link Collection} of {@link MetaEntityDefinition}. to add
	 * @return this {@link MetaEntityContextBuilder} instance
	 * @throws NullPointerException
	 *             if extendedMetaEntityDefinitions is null
	 */
	public MetaEntityContextBuilder metaEntityDefinitions(final Collection<MetaEntityDefinition> extendedMetaEntityDefinitions) throws NullPointerException {
		extendedMetaEntityDefinitions.addAll(extendedMetaEntityDefinitions);
		return this;
	}

	/**
	 * Build an extended {@link MetaEntityDefinition} for this
	 * {@link MetaEntityDefinitionBuilder} instance.
	 * 
	 * @param version
	 *            version to build
	 * @return {@link MetaEntityDefinitionBuilder} instance.
	 * @throws NullPointerException
	 *             if version is null
	 */
	public MetaEntityDefinitionBuilder buildMetaEntityDefinition(final Version version) throws NullPointerException {
		return new MetaEntityDefinitionBuilder(metaEntityDefinitionBuilder.name(), Preconditions.checkNotNull(version), this);
	}

	/**
	 * Add an extended {@link MetaEntityDefinition}.
	 * 
	 * @param definition
	 *            {@link MetaEntityDefinition} to add
	 * @return this {@link MetaEntityContextBuilder} instance
	 * @throws NullPointerException
	 *             if definition is null
	 * @IllegalStateException if definition's name is not same as this context.
	 */
	public MetaEntityContextBuilder metaEntityDefinitions(final MetaEntityDefinition definition) throws NullPointerException, IllegalStateException {
		Preconditions.checkState(metaEntityDefinitionBuilder.name().equals(Preconditions.checkNotNull(definition).name()));
		extendedMetaEntityDefinitions.add(definition);
		return this;
	}
}
