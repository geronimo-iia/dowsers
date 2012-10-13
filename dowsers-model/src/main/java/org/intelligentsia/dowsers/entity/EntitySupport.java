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
package org.intelligentsia.dowsers.entity;

import org.intelligentsia.dowsers.entity.meta.MetaEntityContext;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * EntitySupport implements common methods of {@link Entity}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public abstract class EntitySupport implements Entity, Comparable<Entity> {
	/**
	 * Entity identity.
	 */
	protected final String identity;
	/**
	 * {@link MetaEntityContext} associated.
	 */
	protected final transient MetaEntityContext metaEntityContext;

	/**
	 * Build a new instance of Entity.
	 * 
	 * @param identity
	 *            entity's identity.
	 * @param metaEntityContext
	 *            {@link MetaEntityContext} associated with this instance.
	 * @throws NullPointerException
	 *             if identifier or metaEntityContext is null
	 */
	public EntitySupport(final String identity, final MetaEntityContext metaEntityContext) throws NullPointerException {
		super();
		this.identity = Preconditions.checkNotNull(identity);
		this.metaEntityContext = Preconditions.checkNotNull(metaEntityContext);
	}

	@Override
	public final String identity() {
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
	public final MetaEntityContext metaEntityContext() {
		return metaEntityContext;
	}
}
