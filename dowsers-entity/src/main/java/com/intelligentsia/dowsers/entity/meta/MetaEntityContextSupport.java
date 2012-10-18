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
package com.intelligentsia.dowsers.entity.meta;

import java.util.Arrays;
import java.util.Collection;

import org.intelligentsia.dowsers.core.ReadOnlyIterator;
import org.intelligentsia.keystone.api.artifacts.Version;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

/**
 * MetaEntityContextSupport implements MetaEntityContext.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class MetaEntityContextSupport implements MetaEntityContext {
	/**
	 * Meta entity name.
	 */
	private transient final String name;

	/**
	 * {@link Version} of {@link MetaEntity} definition.
	 */
	private transient final Version version;

	/**
	 * {@link ImmutableMultimap} instance of {@link Version}, Collection of
	 * {@link MetaAttribute}
	 */
	private transient final ImmutableMultimap<Version, MetaAttribute> definition;

	private transient final ImmutableSet<String> definitionAttributeNames;

	private transient final ImmutableSet<String> allExtendedAttributeNames;

	public MetaEntityContextSupport(final MetaEntity definition, final MetaEntity... extendedDefinitions) throws NullPointerException {
		this(definition, extendedDefinitions != null ? Arrays.asList(extendedDefinitions) : null);
	}

	public MetaEntityContextSupport(final MetaEntity definition, final Collection<MetaEntity> extendedDefinitions) throws NullPointerException {
		super();
		// definition
		Preconditions.checkNotNull(definition);
		this.name = definition.name();
		this.version = definition.version();
		this.definitionAttributeNames = definition.attributeNames();
		final ImmutableMultimap.Builder<Version, MetaAttribute> builder = ImmutableMultimap.builder();
		builder.putAll(version, definition.metaAttributes());
		// extended
		final ImmutableSet.Builder<String> nameBuilder = ImmutableSet.builder();
		if (extendedDefinitions != null) {
			for (final MetaEntity metaEntity : extendedDefinitions) {
				builder.putAll(metaEntity.version(), metaEntity.metaAttributes());
				nameBuilder.addAll(metaEntity.attributeNames());
			}
		}
		// final set
		this.definition = builder.build();
		this.allExtendedAttributeNames = nameBuilder.build();
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public Version version() {
		return version;
	}

	@Override
	public ReadOnlyIterator<Version> versions() {
		return ReadOnlyIterator.newReadOnlyIterator(definition.keySet().iterator());
	}

	@Override
	public boolean containsVersion(final Version version) throws NullPointerException {
		return definition.containsKey(version);
	}

	@Override
	public ReadOnlyIterator<MetaAttribute> iterator() {
		return ReadOnlyIterator.newReadOnlyIterator(definition.values().iterator());
	}

	@Override
	public ImmutableCollection<MetaAttribute> metaAttributes(final Version version) throws NullPointerException {
		return ImmutableSet.copyOf(definition.get(version));
	}

	@Override
	public boolean containsAttribute(final String name) throws NullPointerException {
		return definitionAttributeNames.contains(name) || allExtendedAttributeNames.contains(name);
	}

	@Override
	public ImmutableSet<String> definitionAttributeNames() {
		return definitionAttributeNames;
	}

	@Override
	public ImmutableSet<String> allExtendedAttributeNames() {
		return allExtendedAttributeNames;
	}

	/**
	 * Builder implements builder pattern for {@link MetaEntityContext}.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
	 */
	public static class Builder {

		private MetaEntity definition;
		private final Collection<MetaEntity> extendedMetaEntityDefinitions;

		/**
		 * Build a new instance of MetaEntityContextBuilder.
		 */
		public Builder() {
			super();
			extendedMetaEntityDefinitions = Lists.newLinkedList();
		}

		public MetaEntityContext build() {
			return new MetaEntityContextSupport(definition, extendedMetaEntityDefinitions);
		}

		public Builder definition(final MetaEntity metaEntity) {
			definition = metaEntity;
			return this;
		}

		public Builder addExtendedDefinition(final MetaEntity metaEntity) {
			extendedMetaEntityDefinitions.add(metaEntity);
			return this;
		}

	}

}
