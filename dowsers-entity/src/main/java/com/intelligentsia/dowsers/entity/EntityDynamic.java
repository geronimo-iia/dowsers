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
package com.intelligentsia.dowsers.entity;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.intelligentsia.dowsers.core.ReadOnlyIterator;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * EntityDynamic.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityDynamic implements Entity, Comparable<Entity>, Serializable, Iterable<Object> {
	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -6285299269530604418L;
	/**
	 * Entity identity.
	 */
	private final transient Reference identity;
	/**
	 * Map of attributes.
	 */
	private final transient Map<String, Object> attributes;

	/**
	 * {@link MetaEntityContext} instance.
	 */
	protected final transient MetaEntityContext metaEntityContext;

	/**
	 * Build a new instance of EntityDynamic.java.
	 * 
	 * @param identity
	 *            entity's identity.
	 * @param metaEntityContext
	 *            meta entity context
	 * @throws NullPointerException
	 *             if one of parameter is null
	 * @throws IllegalArgumentException
	 *             if identifier is not an identifier
	 */
	public EntityDynamic(final Reference identity, final MetaEntityContext metaEntityContext) throws NullPointerException, IllegalArgumentException {
		this(identity, new LinkedHashMap<String, Object>(), metaEntityContext);
	}

	/**
	 * Build a new instance of {@link EntityDynamic} with an empty attribute's
	 * collection.
	 * 
	 * @param identity
	 *            entity's identity.
	 * @param attributes
	 *            Map of attributes. Map is not copied.
	 * @param metaEntityContext
	 *            meta entity context
	 * @throws NullPointerException
	 *             if one of parameter is null
	 * @throws IllegalArgumentException
	 *             if identifier is not an identifier
	 */
	public EntityDynamic(final Reference identity, final Map<String, Object> attributes, final MetaEntityContext metaEntityContext) throws NullPointerException, IllegalArgumentException {
		Preconditions.checkArgument(Preconditions.checkNotNull(identity).isIdentifier());
		this.identity = identity;
		this.attributes = Preconditions.checkNotNull(attributes);
		this.metaEntityContext = Preconditions.checkNotNull(metaEntityContext);
	}

	@Override
	public final Reference identity() {
		return identity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <Value> Value attribute(final String name) throws NullPointerException {
		return (Value) attributes.get(Preconditions.checkNotNull(name));
	}

	@Override
	public <Value> Entity attribute(final String name, final Value value) throws NullPointerException, IllegalArgumentException {
		Preconditions.checkArgument(!"identity".equals(Preconditions.checkNotNull(name)), "Identity is immutable");
		attributes.put(name, value);
		return this;
	}

	/**
	 * hashCode based on identity.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		return Objects.hashCode(identity);
	}

	/**
	 * Entities compare by identity, not by attributes.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Entity other = (Entity) obj;
		return Objects.equal(other.identity(), identity);
	}

	@Override
	public String toString() {
		return identity.toString();
	}

	@Override
	public int compareTo(final Entity o) {
		return identity.compareTo(o.identity());
	}

	@Override
	public Iterator<Object> iterator() {
		return ReadOnlyIterator.newReadOnlyIterator(attributes.values().iterator());
	}

	@Override
	public boolean contains(final String name) throws NullPointerException, IllegalArgumentException {
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		return attributes.containsKey(name);
	}

	@Override
	public ImmutableSet<String> attributeNames() {
		return ImmutableSet.copyOf(attributes.keySet());
	}

	@Override
	public MetaEntityContext metaEntityContext() {
		return metaEntityContext;
	}
}
