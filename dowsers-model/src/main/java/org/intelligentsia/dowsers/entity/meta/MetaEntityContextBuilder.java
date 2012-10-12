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

	private final String name;
	private final Version rootVersion;
	private Collection<MetaAttribute> metaAttributes;
	private final Collection<MetaEntityDefinition> extendedMetaEntityDefinitions;

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
	public MetaEntityContextBuilder(final String name, final Version rootVersion) throws NullPointerException, IllegalArgumentException {
		this(name, rootVersion, null, null);

	}

	/**
	 * 
	 * Build a new instance of MetaEntityContextBuilder.java.
	 * 
	 * @param name
	 * @param rootVersion
	 * @param metaAttributes
	 * @param extendedMetaEntityDefinitions
	 *            * @throws NullPointerException if name or rootVersionis null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 */
	public MetaEntityContextBuilder(final String name, final Version rootVersion, final Collection<MetaAttribute> metaAttributes, final Collection<MetaEntityDefinition> extendedMetaEntityDefinitions) throws NullPointerException,
			IllegalArgumentException {
		super();
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		this.name = name;
		this.rootVersion = Preconditions.checkNotNull(rootVersion);
		this.metaAttributes = (metaAttributes != null ? metaAttributes : new LinkedHashSet<MetaAttribute>());
		this.extendedMetaEntityDefinitions = extendedMetaEntityDefinitions != null ? extendedMetaEntityDefinitions : new LinkedHashSet<MetaEntityDefinition>();
	}

	@Override
	public MetaEntityContext build() {
		return new MetaEntityContextDefinition(name, rootVersion, metaAttributes, extendedMetaEntityDefinitions);
	}

	/**
	 * 
	 * @param metaAttributes
	 * @return this {@link MetaEntityContextBuilder} instance
	 * @throws NullPointerException
	 *             if metaAttributes is null
	 */
	public MetaEntityContextBuilder setMetaAttributes(final Collection<MetaAttribute> metaAttributes) throws NullPointerException {
		this.metaAttributes = Preconditions.checkNotNull(metaAttributes);
		return this;
	}

	public MetaEntityContextBuilder add(final MetaAttribute metaAttribute) throws NullPointerException {
		this.metaAttributes.add(metaAttribute);
		return this;
	}

	public MetaEntityContextBuilder add(final MetaAttribute... metaAttributes) throws NullPointerException {
		this.metaAttributes.addAll(Arrays.asList(Preconditions.checkNotNull(metaAttributes)));
		return this;
	}

	/**
	 * 
	 * @param extendedMetaEntityDefinitions
	 * @return this {@link MetaEntityContextBuilder} instance
	 */
	public MetaEntityContextBuilder setExtendedMetaEntityDefinitions(final Collection<MetaEntityDefinition> extendedMetaEntityDefinitions) {
		return this;
	}

	/**
	 * Add an extended {@link MetaEntityDefinition}.
	 * 
	 * @param version
	 * @return {@link MetaEntityDefinitionBuilder} instance.
	 */
	public MetaEntityDefinitionBuilder add(final Version version) {
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
	public MetaEntityContextBuilder add(final MetaEntityDefinition definition) throws NullPointerException {
		Preconditions.checkState(name.equals(Preconditions.checkNotNull(definition).getName()));
		extendedMetaEntityDefinitions.add(definition);
		return this;
	}
}
