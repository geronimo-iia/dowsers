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

import java.util.Iterator;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContext;

/**
 * EntityDecorator implements decorator pattern on {@link EntityDynamic}.
 * 
 * 
 * TODO not sure that a good idea
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityDecorator implements Entity {

	protected final EntityDynamic entity;

	/**
	 * Build a new instance of EntityDecorator.java.
	 * 
	 * @param entity
	 * @throws NullPointerException
	 *             if entity is null
	 */
	public EntityDecorator(final EntityDynamic entity) throws NullPointerException {
		super();
		this.entity = Preconditions.checkNotNull(entity);
	}

	@Override
	public final String identity() {
		return entity.identity();
	}

	@Override
	public <Value> Value attribute(final String name) throws NullPointerException {
		return entity.attribute(name);
	}

	@Override
	public <Value> Entity attribute(final String name, final Value value) throws NullPointerException {
		return entity.attribute(name, value);
	}

	@Override
	public int hashCode() {
		return entity.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		// find good instance
		Object other = obj;
		if (getClass().isAssignableFrom(obj.getClass())) {
			other = ((EntityDecorator) obj).entity;
		}
		return entity.equals(other);
	}

	@Override
	public String toString() {
		return entity.toString();
	}

	public int compareTo(final Entity o) {
		return entity.compareTo(o);
	}

	public Iterator<Object> iterator() {
		return entity.iterator();
	}

	@Override
	public boolean contains(final String name) throws NullPointerException, IllegalArgumentException {
		return entity.contains(name);
	}

	@Override
	public ImmutableSet<String> attributeNames() {
		return entity.attributeNames();
	}

	@Override
	public MetaEntityContext metaEntityContext() {
		return entity.metaEntityContext();
	}

}
