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
import java.util.Map;

import org.intelligentsia.dowsers.core.ReadOnlyIterator;
import org.intelligentsia.dowsers.entity.Entity;
import org.intelligentsia.keystone.api.artifacts.Version;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

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
	private final Map<String, MetaAttribute> metaAttributes;

	/**
	 * Build a new instance of MetaEntityDefinition.java.
	 * 
	 * @param definition
	 * @throws NullPointerException
	 *             if definition is null
	 */
	public MetaEntityDefinition(final MetaEntityDefinition definition) throws NullPointerException {
		super();
		name = Preconditions.checkNotNull(definition).name;
		version = definition.version;
		metaAttributes = definition.metaAttributes;
	}

	/**
	 * Build a new instance of {@link MetaEntityDefinition}.
	 * 
	 * @param name
	 *            entity name
	 * @param version
	 *            entity meta definition version
	 * @param metaAttributes
	 *            collection of {@link MetaAttribute} define by version
	 * @throws NullPointerException
	 *             if name, version, metaProperties is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 */
	public MetaEntityDefinition(final String name, final Version version, final Collection<MetaAttribute> metaAttributes) throws NullPointerException, IllegalArgumentException {
		super();
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		this.name = name;
		this.version = Preconditions.checkNotNull(version);
		final ImmutableMap.Builder<String, MetaAttribute> builder = new ImmutableMap.Builder<String, MetaAttribute>();
		for (final MetaAttribute metaAttribute : Preconditions.checkNotNull(metaAttributes)) {
			builder.put(metaAttribute.getName(), metaAttribute);
		}
		this.metaAttributes = builder.build();
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
		return metaAttributes.containsKey(name);
	}

	@Override
	public MetaAttribute getMetaAttribute(final String name) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		return metaAttributes.get(name);
	}

	@Override
	public ReadOnlyIterator<MetaAttribute> getMetaAttributes() {
		return ReadOnlyIterator.newReadOnlyIterator(metaAttributes.values().iterator());
	}

	@Override
	public ReadOnlyIterator<String> getMetaAttributeNames() {
		return ReadOnlyIterator.newReadOnlyIterator(metaAttributes.keySet().iterator());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name, version, metaAttributes);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(getClass()).add("name", name).add("version", version).add("metaAttributes", metaAttributes).toString();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MetaEntityDefinition other = (MetaEntityDefinition) obj;
		if (metaAttributes == null) {
			if (other.metaAttributes != null) {
				return false;
			}
		} else if (!metaAttributes.equals(other.metaAttributes)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (version == null) {
			if (other.version != null) {
				return false;
			}
		} else if (!version.equals(other.version)) {
			return false;
		}
		return true;
	}

	@Override
	public String getIdentity() {
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
	public MetaEntityContext getMetaEntityContext() {
		return null;
	}

}
