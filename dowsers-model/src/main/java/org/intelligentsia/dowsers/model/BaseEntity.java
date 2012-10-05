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

import java.util.Map;

import org.intelligentsia.dowsers.core.IdentifierFactoryProvider;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
 * BaseEntity implements {@link Entity}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class BaseEntity implements Entity {
	/**
	 * Entity identity.
	 */
	private final String identity;
	/**
	 * Map of properties.
	 */
	private Map<String, Property<?>> properties = Maps.newHashMap();

	/**
	 * Build a new instance of BaseEntity.java.
	 */
	public BaseEntity() {
		this(IdentifierFactoryProvider.generateNewIdentifier());
	}

	/**
	 * Build a new instance of Entity. All change in this entity will be bounded
	 * by an aggregate.
	 * 
	 * @param identity
	 *            entity's identity.
	 * 
	 * @throws NullPointerException
	 *             if identifier is null
	 */
	public BaseEntity(final String identity) throws NullPointerException {
		super();
		this.identity = Preconditions.checkNotNull(identity);
	}

	@Override
	public final String getIdentity() {
		return identity;
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
		return Objects.equal(other.getIdentity(), identity);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("identity", identity).toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Property<T> getProperty(String name) throws NullPointerException {
		return (Property<T>) properties.get(name);
	}

}
