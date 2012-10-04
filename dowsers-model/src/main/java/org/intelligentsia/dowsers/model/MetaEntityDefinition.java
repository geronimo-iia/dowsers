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

import org.intelligentsia.dowsers.entity.Entity;
import org.intelligentsia.keystone.api.artifacts.Version;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
 * {@link MetaEntityDefinition} is a version of an {@link MetaEntity}.
 * 
 * TODO extends BaseEntity and thinks about a MetaEntity as an Entity.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MetaEntityDefinition implements MetaEntity {

	/**
	 * Meta entity name.
	 */
	private final String name;

	/**
	 * Meta entity version.
	 */
	private final Version version;
	/**
	 * Map of meta properties defined by {@link MetaEntityDefinition#version}.
	 */
	private final Map<String, MetaProperty> metaProperties;

	/**
	 * Build a new instance of MetaEntityDefinition.java.
	 * 
	 * @param definition
	 * @throws NullPointerException
	 *             if definition is null
	 */
	public MetaEntityDefinition(MetaEntityDefinition definition) throws NullPointerException {
		super();
		name = Preconditions.checkNotNull(definition).name;
		version = definition.version;
		metaProperties = definition.metaProperties;
	}

	/**
	 * Build a new instance of {@link MetaEntityDefinition}.
	 * 
	 * @param name
	 *            entity name
	 * @param version
	 *            entity meta definition version
	 * @param metaProperties
	 *            collection of {@link MetaProperty} define by version
	 * @throws NullPointerException
	 *             if name, version, metaProperties is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 */
	public MetaEntityDefinition(String name, Version version, Collection<MetaProperty> metaProperties) throws NullPointerException, IllegalArgumentException {
		super();
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		this.name = name;
		this.version = Preconditions.checkNotNull(version);
		this.metaProperties = fill(Preconditions.checkNotNull(metaProperties));
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Define {@link MetaEntityDefinition} version of {@link Entity}.
	 * 
	 * @return {@link Version} instance.
	 */
	public Version getVersion() {
		return version;
	}

	@Override
	public boolean contains(final String name) throws NullPointerException, IllegalArgumentException {
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		return metaProperties.containsKey(name);
	}

	@Override
	public MetaProperty getMetaProperty(final String name) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		MetaProperty metaProperty = metaProperties.get(name);
		Preconditions.checkState(metaProperty != null);
		return metaProperty;
	}

	@Override
	public Iterator<MetaProperty> getMetaProperties() {
		final Iterator<MetaProperty> iterator = metaProperties.values().iterator();
		return new Iterator<MetaProperty>() {

			@Override
			public void remove() {
				throw new IllegalStateException("Forbidden operation");
			}

			@Override
			public MetaProperty next() {
				return iterator.next();
			}

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}
		};

	}

	/**
	 * Transform a {@link Collection} of {@link MetaProperty} into a {@link Map}
	 * of {name, {@link MetaProperty} >
	 * 
	 * @param metaProperties
	 * @return a {@link Map} instance.
	 */
	private Map<String, MetaProperty> fill(Collection<MetaProperty> metaProperties) {
		Map<String, MetaProperty> result = Maps.newHashMap();
		for (MetaProperty metaEntity : metaProperties) {
			result.put(metaEntity.getName(), metaEntity);
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((metaProperties == null) ? 0 : metaProperties.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MetaEntityDefinition other = (MetaEntityDefinition) obj;
		if (metaProperties == null) {
			if (other.metaProperties != null)
				return false;
		} else if (!metaProperties.equals(other.metaProperties))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MetaEntityDefinition [name=" + name + ", version=" + version + ", metaProperties=" + metaProperties + "]";
	}

}
