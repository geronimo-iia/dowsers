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

import com.google.common.base.Preconditions;

/**
 * EntityDecorator implements Decorator pattern for {@link Entity}.
 * 
 * @see http://en.wikipedia.org/wiki/Decorator_pattern.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityDecorator implements Entity {

	/**
	 * Decorated {@link Entity} instance.
	 */
	protected final Entity entity;

	/**
	 * Build a new instance of {@link EntityDecorator}.
	 * 
	 * @param entity
	 *            decorated entity
	 * @throws NullPointerException
	 *             if entity is null
	 */
	public EntityDecorator(final Entity entity) throws NullPointerException {
		super();
		this.entity = Preconditions.checkNotNull(entity);
	}

	@Override
	public String identity() {
		return entity.identity();
	}

	@Override
	public MetaEntityContext metaEntityContext() {
		return entity.metaEntityContext();
	}

	@Override
	public <Value> Value attribute(final String name) throws NullPointerException, IllegalArgumentException {
		return entity.attribute(name);
	}

	/**
	 * @return {@link EntityDecorator} instance.
	 * @see org.intelligentsia.dowsers.entity.Entity#attribute(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public <Value> Entity attribute(final String name, final Value value) throws NullPointerException, IllegalArgumentException {
		entity.attribute(name, value);
		return this;
	}

}
