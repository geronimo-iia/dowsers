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

import java.lang.reflect.Proxy;

import com.intelligentsia.dowsers.entity.serializer.EntityProxyHandler;

/**
 * EntityFactories.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public enum EntityFactories {
	;

	public static EntityFactory<EntityDynamic> newEntityDynamicFactory() {
		return new EntityFactory<EntityDynamic>() {

			@Override
			public EntityDynamic newInstance(final String identity) throws NullPointerException, IllegalArgumentException {
				return new EntityDynamic(identity);
			}

			@Override
			public EntityDynamic newInstance() {
				return new EntityDynamic();
			}
		};
	}

	/**
	 * Build a new {@link EntityFactory} which build {@link EntityProxy} for
	 * specified class name and use {@link EntityDynamic} as implementation.
	 * 
	 * @param className
	 *            entity class Name
	 * @return a {@link EntityFactory} instance.
	 */
	public static <T> EntityFactory<T> newEntityProxyDynamicFactory(final Class<T> className) {
		return newEntityProxyDynamicFactory(className, newEntityDynamicFactory());
	}

	/**
	 * Build a new {@link EntityFactory} which build {@link EntityProxy} for
	 * specified class name and use implementation given by specified
	 * {@link EntityFactory}.
	 * 
	 * @param className
	 *            entity class Name
	 * @param factory
	 *            entity factory
	 * @return a {@link EntityFactory} instance.
	 */
	public static <T, Y extends Entity> EntityFactory<T> newEntityProxyDynamicFactory(final Class<T> className, final EntityFactory<Y> factory) {
		return new EntityFactory<T>() {

			@SuppressWarnings("unchecked")
			@Override
			public T newInstance(final String identity) throws NullPointerException, IllegalArgumentException {
				return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { className, EntityProxyHandler.class }, new EntityProxy(className, factory.newInstance(identity)));
			}

			@SuppressWarnings("unchecked")
			@Override
			public T newInstance() {
				return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { className, EntityProxyHandler.class }, new EntityProxy(className, factory.newInstance()));
			}
		};
	}

	/**
	 * EntityFactory.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	public interface EntityFactory<T> {

		/**
		 * @return an {@link Entity} instance.
		 */
		T newInstance();

		/**
		 * @param identity
		 *            entity's identity.
		 * @return an {@link Entity} instance.
		 * @throws NullPointerException
		 *             if identity is null
		 * @throws IllegalArgumentException
		 *             if identifier is empty
		 * 
		 */
		T newInstance(final String identity) throws NullPointerException, IllegalArgumentException;

	}
}
