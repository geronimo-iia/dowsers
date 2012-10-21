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
import java.util.Iterator;

import org.intelligentsia.dowsers.core.ReadOnlyIterator;
import org.intelligentsia.keystone.api.artifacts.Version;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.intelligentsia.dowsers.entity.Entity;

/**
 * {@link MetaEntityContext} define methods to gain access on {@link MetaEntity}
 * of an {@link Entity}.
 * 
 * A {@link MetaEntityContext} is a {@link MetaEntity} composite of
 * {@link MetaEntity}. Each component is an extends of meta data given by super
 * {@link MetaEntity} instance.
 * 
 * We call version of {@link MetaEntity} definition the fisrst and initial
 * {@link MetaEntity} set to a {@link MetaEntityContext}.
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class MetaEntityContext implements Iterable<MetaAttribute> {
	/**
	 * Meta entity name.
	 */
	private transient final String name;

	/**
	 * {@link ImmutableSet} of {@link Version}.
	 */
	private transient final ImmutableSet<Version> versions;

	/**
	 * {@link ImmutableMap} instance of {@link String}, {@link MetaAttribute}
	 */
	private transient final ImmutableMap<String, MetaAttribute> definition;

	/**
	 * {@link ImmutableSet} of definition attribute name.
	 */
	private transient final ImmutableSet<String> definitionAttributeNames;

	/**
	 * {@link ImmutableSet} of extended attribute name.
	 */
	private transient final ImmutableSet<String> extendedAttributeNames;

	/**
	 * Build a new instance of MetaEntityContext.
	 * 
	 * @param definition
	 *            base definition
	 * @param extendedDefinitions
	 *            all extended definitions
	 * @throws NullPointerException
	 *             if definition is null
	 */
	public MetaEntityContext(final MetaEntity definition, final MetaEntity... extendedDefinitions) throws NullPointerException {
		this(definition, extendedDefinitions != null ? Arrays.asList(extendedDefinitions) : null);
	}

	/**
	 * Build a new instance of MetaEntityContext.
	 * 
	 * @param definition
	 *            base definition
	 * @param extendedDefinitions
	 *            all extended definitions
	 * @throws NullPointerException
	 *             if definition is null
	 */
	public MetaEntityContext(final MetaEntity definition, final Collection<MetaEntity> extendedDefinitions) throws NullPointerException {
		super();
		// definition
		Preconditions.checkNotNull(definition);
		this.name = definition.name();
		this.definitionAttributeNames = definition.metaAttributeNames();
		// versions
		final ImmutableSet.Builder<Version> versionBuilder = ImmutableSet.builder();
		versionBuilder.add(definition.version());
		// meta attribute of definition
		final ImmutableMap.Builder<String, MetaAttribute> builder = ImmutableMap.builder();
		Iterator<MetaAttribute> iterator = definition.metaAttributes().iterator();
		while (iterator.hasNext()) {
			final MetaAttribute attribute = iterator.next();
			builder.put(attribute.name(), attribute);
		}
		// extended attribute of definition
		final ImmutableSet.Builder<String> nameBuilder = ImmutableSet.builder();
		if (extendedDefinitions != null) {
			for (final MetaEntity metaEntity : extendedDefinitions) {
				versionBuilder.add(metaEntity.version());
				nameBuilder.addAll(metaEntity.metaAttributeNames());
				iterator = metaEntity.metaAttributes().iterator();
				while (iterator.hasNext()) {
					final MetaAttribute attribute = iterator.next();
					builder.put(attribute.name(), attribute);
				}
			}
		}
		// final set
		this.versions = versionBuilder.build();
		this.definition = builder.build();
		this.extendedAttributeNames = nameBuilder.build();
	}

	/**
	 * Returns a textual class name of the entity.
	 * 
	 * @return non-<code>null</code>, empty or non-empty string
	 */
	public String name() {
		return name;
	}

	/**
	 * @return {@link Version} of {@link MetaEntity} definition.
	 */
	public Version version() {
		return versions.iterator().next();
	}

	/**
	 * @return an ordered {@link ReadOnlyIterator} on {@link Version} which
	 *         compose this {@link MetaEntityContext}. First item will always be
	 *         the root {@link MetaEntity} definition of this
	 *         {@link MetaEntityContext}.
	 */
	public ReadOnlyIterator<Version> versions() {
		return ReadOnlyIterator.newReadOnlyIterator(versions.iterator());
	}

	/**
	 * @param version
	 *            version
	 * @return {@see Boolean#TRUE} if this context has specified version of
	 *         {@link MetaEntity}.
	 * @throws NullPointerException
	 *             if version is null
	 */
	public boolean containsVersion(final Version version) throws NullPointerException {
		return versions.contains(version);
	}

	@Override
	public ReadOnlyIterator<MetaAttribute> iterator() {
		return ReadOnlyIterator.newReadOnlyIterator(definition.values().iterator());
	}

	/**
	 * @param name
	 *            attribute name
	 * @return {@see Boolean#TRUE} if this entity has specified named attribute.
	 * @throws NullPointerException
	 *             if name is null
	 */
	public boolean containsMetaAttribute(final String name) throws NullPointerException {
		return definition.containsKey(name);
	}

	/**
	 * Get {@link MetaAttribute} of specified name.
	 * 
	 * @param attributeName
	 *            attribute name
	 * @return {@link MetaAttribute} instance or specified name or null if none
	 *         is found
	 * @throws NullPointerException
	 *             if attributName id null
	 * @throws {@link IllegalArgumentException} if attributName is empty
	 */
	public MetaAttribute metaAttribute(final String attributeName) throws NullPointerException, IllegalArgumentException {
		return definition.get(attributeName);
	}

	/**
	 * @return a {@link ImmutableSet} of attributes names define by version of
	 *         {@link MetaEntity} definition.
	 */
	public ImmutableSet<String> definitionAttributeNames() {
		return definitionAttributeNames;
	}

	/**
	 * @return a {@link ImmutableCollection} of attributes names define by all
	 *         extended {@link MetaEntity} version.
	 */
	public ImmutableSet<String> allExtendedAttributeNames() {
		return extendedAttributeNames;
	}

	/**
	 * @return a new {@link Builder} instance.
	 */
	public static Builder builder() {
		return new Builder();
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
			return new MetaEntityContext(definition, extendedMetaEntityDefinitions);
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
