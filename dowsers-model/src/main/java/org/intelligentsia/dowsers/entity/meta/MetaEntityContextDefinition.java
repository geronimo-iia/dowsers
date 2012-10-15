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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.intelligentsia.dowsers.core.ReadOnlyIterator;
import org.intelligentsia.dowsers.entity.Entity;
import org.intelligentsia.keystone.api.artifacts.Version;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * {@link MetaEntityContextDefinition} implements {@link MetaEntityContext}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MetaEntityContextDefinition implements MetaEntityContext, Entity {

	/**
	 * Meta entity name.
	 */
	private final String name;

	/**
	 * Meta root entity version.
	 */
	private final Version version;

	/**
	 * Map of MetaEntityDefinition.
	 */
	private final Map<Version, MetaEntityDefinition> metaEntityDefinitions;

	/**
	 * Set of extended attributes names.
	 */
	private Set<String> extendedAttributesNames;

	/**
	 * 
	 * Build a new instance of MetaEntityContextDefinition.java.
	 * 
	 * @param name
	 *            entity name
	 * @param version
	 *            version of {@link MetaEntity} definition
	 * @param metaAttributes
	 *            collection of {@link MetaAttribute} define by root version
	 * @param extendedMetaEntityDefinitions
	 *            extended {@link MetaEntityDefinition}'s
	 * @throws NullPointerException
	 *             if name, version, metaProperties is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 */
	public MetaEntityContextDefinition(final String name, final Version version, final Collection<MetaAttribute> metaAttributes, final Collection<MetaEntityDefinition> extendedMetaEntityDefinitions) throws NullPointerException,
			IllegalArgumentException {
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		this.name = name;
		this.version = Preconditions.checkNotNull(version);
		final ImmutableMap.Builder<Version, MetaEntityDefinition> builder = new ImmutableMap.Builder<Version, MetaEntityDefinition>().put(version, new MetaEntityDefinition(name, version, Preconditions.checkNotNull(metaAttributes)));
		if (extendedMetaEntityDefinitions != null) {
			// tranform collection into a map and build extendedAttributesNames.
			final ImmutableSet.Builder<String> extendedPropertyNamesBuilder = new ImmutableSet.Builder<String>();
			for (final MetaEntityDefinition metaEntityDefinition : extendedMetaEntityDefinitions) {
				builder.put(metaEntityDefinition.version(), metaEntityDefinition);
				extendedPropertyNamesBuilder.addAll(metaEntityDefinition.metaAttributeNames());
			}
			extendedAttributesNames = extendedPropertyNamesBuilder.build();
		} else {
			extendedAttributesNames = ImmutableSet.of();
		}
		metaEntityDefinitions = builder.build();
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public boolean contains(final String name) throws NullPointerException, IllegalArgumentException {
		final Iterator<MetaEntityDefinition> iterator = metaEntityDefinitions.values().iterator();
		boolean result = false;
		while (iterator.hasNext() && !(result = iterator.next().contains(name))) {
		}
		return result;
	}

	@Override
	public MetaAttribute metaAttributes(final String name) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		final Iterator<MetaEntityDefinition> iterator = metaEntityDefinitions.values().iterator();
		MetaAttribute result = null;
		while (iterator.hasNext() && ((result = iterator.next().metaAttributes(name)) == null)) {
		}
		return result;
	}

	@Override
	public ImmutableCollection<MetaAttribute> metaAttributes() {
		final ImmutableSet.Builder<MetaAttribute> builder = ImmutableSet.builder();
		final Iterator<MetaEntityDefinition> iterator = metaEntityDefinitions.values().iterator();
		while (iterator.hasNext()) {
			builder.addAll(iterator.next());
		}
		return builder.build();
	}

	/**
	 * @return a {@link ReadOnlyIterator} on {@link MetaAttribute}.
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<MetaAttribute> iterator() {
		final Iterator<MetaEntityDefinition> iterator = metaEntityDefinitions.values().iterator();
		return new ReadOnlyIterator<MetaAttribute>() {
			// current iterator on meta entity definition
			Iterator<MetaAttribute> current = iterator.next().iterator();

			@Override
			public boolean hasNext() {
				boolean result = current.hasNext();
				if (!result && iterator.hasNext()) {
					current = iterator.next().iterator();
					result = current.hasNext();
				}
				return result;
			}

			@Override
			public MetaAttribute next() {
				return current.next();
			}
		};
	}

	@Override
	public Version version() {
		return version;
	}

	@Override
	public ReadOnlyIterator<Version> versions() {
		return ReadOnlyIterator.newReadOnlyIterator(metaEntityDefinitions.keySet().iterator());
	}

	@Override
	public MetaEntity metaEntity(final Version version) throws NullPointerException {
		return metaEntityDefinitions.get(version);
	}

	@Override
	public ReadOnlyIterator<String> metaAttributeNames() {
		final Iterator<MetaEntityDefinition> iterator = metaEntityDefinitions.values().iterator();
		return new ReadOnlyIterator<String>() {

			ReadOnlyIterator<String> current = iterator.next().metaAttributeNames();

			@Override
			public boolean hasNext() {
				boolean result = current.hasNext();
				if (!result && iterator.hasNext()) {
					current = iterator.next().metaAttributeNames();
					result = current.hasNext();
				}
				return result;
			}

			@Override
			public String next() {
				return current.next();
			}
		};
	}

	@Override
	public Set<String> allExtendedPropertyNames() {
		return extendedAttributesNames;
	}

	@Override
	public String identity() {
		return null;
	}

	@Override
	public <Value> Value attribute(final String name) throws NullPointerException, IllegalArgumentException {
		return null;
	}

	@Override
	public <Value> Entity attribute(final String name, final Value value) throws NullPointerException, IllegalArgumentException {
		return null;
	}

	@Override
	public MetaEntityContext metaEntityContext() {
		return null;
	}

}
