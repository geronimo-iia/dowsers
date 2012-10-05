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

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.intelligentsia.dowsers.core.ReadOnlyIterator;
import org.intelligentsia.keystone.api.artifacts.Version;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * {@link MetaEntityContextDefinition} implements {@link MetaEntityContext}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class MetaEntityContextDefinition implements MetaEntityContext {

	/**
	 * Meta entity name.
	 */
	private final String name;

	/**
	 * Meta root entity version.
	 */
	private final Version rootVersion;

	/**
	 * Map of MetaEntityDefinition.
	 */
	private final Map<Version, MetaEntityDefinition> metaEntityDefinitions;

	private Set<String> extendedPropertyNames;

	/**
	 * 
	 * Build a new instance of MetaEntityContextDefinition.java.
	 * 
	 * @param name
	 *            entity name
	 * @param rootVersion
	 *            root version if {@link MetaEntity} definition
	 * @param metaProperties
	 *            collection of {@link MetaProperty} define by root version
	 * @param extendedMetaEntityDefinitions
	 *            extended {@link MetaEntityDefinition}'s
	 * @throws NullPointerException
	 *             if name, version, metaProperties is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 */
	public MetaEntityContextDefinition(String name, Version rootVersion, Collection<MetaProperty> metaProperties, Collection<MetaEntityDefinition> extendedMetaEntityDefinitions) throws NullPointerException, IllegalArgumentException {
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		this.name = name;
		this.rootVersion = Preconditions.checkNotNull(rootVersion);
		ImmutableMap.Builder<Version, MetaEntityDefinition> builder = new ImmutableMap.Builder<Version, MetaEntityDefinition>().put(rootVersion, new MetaEntityDefinition(name, rootVersion, Preconditions.checkNotNull(metaProperties)));
		if (extendedMetaEntityDefinitions != null) {
			ImmutableSet.Builder<String> extendedPropertyNamesBuilder = new ImmutableSet.Builder<String>();
			for (MetaEntityDefinition metaEntityDefinition : extendedMetaEntityDefinitions) {
				builder.put(metaEntityDefinition.getVersion(), metaEntityDefinition);
				extendedPropertyNamesBuilder.addAll(metaEntityDefinition.getMetaPropertyNames());
			}
			extendedPropertyNames = extendedPropertyNamesBuilder.build();
		} else {
			extendedPropertyNames = ImmutableSet.of();
		}
		metaEntityDefinitions = builder.build();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean contains(String name) throws NullPointerException, IllegalArgumentException {
		Iterator<MetaEntityDefinition> iterator = metaEntityDefinitions.values().iterator();
		boolean result = false;
		while (iterator.hasNext() && !(result = iterator.next().contains(name))) {
		}
		return result;
	}

	@Override
	public MetaProperty getMetaProperty(String name) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		Iterator<MetaEntityDefinition> iterator = metaEntityDefinitions.values().iterator();
		MetaProperty result = null;
		while (iterator.hasNext() && ((result = iterator.next().getMetaProperty(name)) == null)) {
		}
		return result;
	}

	@Override
	public ReadOnlyIterator<MetaProperty> getMetaProperties() {
		final Iterator<MetaEntityDefinition> iterator = metaEntityDefinitions.values().iterator();
		return new ReadOnlyIterator<MetaProperty>() {

			ReadOnlyIterator<MetaProperty> current = iterator.next().getMetaProperties();

			@Override
			public boolean hasNext() {
				boolean result = current.hasNext();
				if (!result && iterator.hasNext()) {
					current = iterator.next().getMetaProperties();
					result = current.hasNext();
				}
				return result;
			}

			@Override
			public MetaProperty next() {
				return current.next();
			}
		};
	}

	@Override
	public Version getRootVersion() {
		return rootVersion;
	}

	@Override
	public ReadOnlyIterator<Version> getVersions() {
		return ReadOnlyIterator.newReadOnlyIterator(metaEntityDefinitions.keySet().iterator());
	}

	@Override
	public MetaEntity getMetaEntity(Version version) throws NullPointerException, IllegalStateException {
		return metaEntityDefinitions.get(version);
	}

	@Override
	public ReadOnlyIterator<String> getMetaPropertyNames() {
		final Iterator<MetaEntityDefinition> iterator = metaEntityDefinitions.values().iterator();
		return new ReadOnlyIterator<String>() {

			ReadOnlyIterator<String> current = iterator.next().getMetaPropertyNames();

			@Override
			public boolean hasNext() {
				boolean result = current.hasNext();
				if (!result && iterator.hasNext()) {
					current = iterator.next().getMetaPropertyNames();
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
	public Set<String> getAllExtendedPropertyNames() {
		return extendedPropertyNames;
	}

}
