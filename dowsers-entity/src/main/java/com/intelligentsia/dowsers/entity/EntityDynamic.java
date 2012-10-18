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

import org.intelligentsia.dowsers.core.IdentifierFactoryProvider;
import org.intelligentsia.dowsers.core.ReadOnlyIterator;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

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
	private final String identity;
	/**
	 * Map of attributes.
	 */
	private final Map<String, Object> attributes;

	/**
	 * Build a new instance of {@link EntityDynamic}.
	 */
	public EntityDynamic() {
		this(IdentifierFactoryProvider.generateNewIdentifier());
	}

	/**
	 * Build a new instance of {@link EntityDynamic} with an empty attribute's
	 * collection.
	 * 
	 * @param identity
	 *            entity's identity.
	 * @throws NullPointerException
	 *             if identity is null
	 * @throws IllegalArgumentException
	 *             if identifier is empty
	 */
	public EntityDynamic(final String identity) throws NullPointerException, IllegalArgumentException {
		this(identity, new LinkedHashMap<String, Object>());
	}

	/**
	 * Build a new instance of {@link EntityDynamic} with an empty attribute's
	 * collection.
	 * 
	 * @param identity
	 *            entity's identity.
	 * @param attributes
	 *            Map of attributes. Map is not copied.
	 * @throws NullPointerException
	 *             if one of parameter is null
	 * @throws IllegalArgumentException
	 *             if identifier is empty
	 */

	public EntityDynamic(final String identity, final Map<String, Object> attributes) throws NullPointerException, IllegalArgumentException {
		super();
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(identity)));
		this.identity = identity;
		this.attributes = Preconditions.checkNotNull(attributes);
	}

	@Override
	public final String identity() {
		return identity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <Value> Value attribute(final String name) throws NullPointerException {
		return (Value) attributes.get(Preconditions.checkNotNull(name));
	}

	@Override
	public <Value> Entity attribute(final String name, final Value value) throws NullPointerException {
		attributes.put(Preconditions.checkNotNull(name), value);
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

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("identity", identity).toString();
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

	/**
	 * @return {@link Map} of attributes.
	 */
	protected Map<String, Object> attributes() {
		return attributes;
	}
}
