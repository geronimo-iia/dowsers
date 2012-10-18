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
package org.intelligentsia.dowsers.entity.factory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.intelligentsia.dowsers.entity.Entity;
import org.intelligentsia.dowsers.entity.meta.MetaEntityContextNotFoundException;

import com.google.common.base.Preconditions;

/**
 * EntityFactoryMapping implements a configured {@link EntityFactory}.
 * 
 * EntityFactoryMapping use a default {@link EntityFactory} to create an
 * {@link Entity} instance if no match was found in set of [class name,
 * {@link EntityFactory} .
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityFactoryMapping implements EntityFactory, Iterable<EntityFactory> {

	private final EntityFactory defaultEntityFactory;

	private final Map<String, EntityFactory> entityFactories;

	/**
	 * Build a new instance of EntityFactoryMapping.java.
	 * 
	 * @param defaultEntityFactory
	 * @throws NullPointerException
	 */
	public EntityFactoryMapping(final EntityFactory defaultEntityFactory) {
		this(defaultEntityFactory, new HashMap<String, EntityFactory>());
	}

	/**
	 * Build a new instance of EntityFactoryMapping.java.
	 * 
	 * @param defaultEntityFactory
	 *            default {@link EntityFactory} instance
	 * @param entityFactories
	 *            map of class name, {@link EntityFactory} instance.
	 * @throws NullPointerException
	 *             if defaultEntityFactory or entityFactories is null
	 */
	public EntityFactoryMapping(final EntityFactory defaultEntityFactory, final Map<String, EntityFactory> entityFactories) throws NullPointerException {
		this.defaultEntityFactory = Preconditions.checkNotNull(defaultEntityFactory);
		this.entityFactories = Preconditions.checkNotNull(entityFactories);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity> T newInstance(final Class<?> className, final String identity) throws NullPointerException, MetaEntityContextNotFoundException {
		EntityFactory factory = entityFactories.get(className.getName());
		if (factory == null) {
			factory = defaultEntityFactory;
		}
		return (T) factory.newInstance(className, identity);
	}

	/**
	 * @return default {@link EntityFactory}.
	 */
	public EntityFactory getDefaultEntityFactory() {
		return defaultEntityFactory;
	}

	/**
	 * Add a new {@link EntityFactory} associated with specified class name.
	 * 
	 * @param className
	 *            class Name
	 * @param entityFactory
	 *            {@link EntityFactory} instance.
	 * @return this {@link EntityFactoryMapping} instance.
	 * @throws NullPointerException
	 *             if className or entityFactory is null
	 */
	public EntityFactoryMapping add(final Class<?> className, final EntityFactory entityFactory) throws NullPointerException {
		entityFactories.put(Preconditions.checkNotNull(className).getSimpleName(), Preconditions.checkNotNull(entityFactory));
		return this;
	}

	@Override
	public Iterator<EntityFactory> iterator() {
		return entityFactories.values().iterator();
	}

}
