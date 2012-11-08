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
package com.intelligentsia.dowsers.entity.reference;

import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.ServiceLoader;

import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.EntityProxy;

/**
 * References generate {@link Entity} or attribute's {@link Entity} reference.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public enum References {
	;

	/**
	 * {@link ReferenceFactory} instance.
	 */
	private static ReferenceFactory REFERENCE_FACTORY = lookup(Thread.currentThread().getContextClassLoader());

	/**
	 * Generate new References using configured {@link ReferenceFactory}
	 * provider.
	 * 
	 * @param clazz
	 *            entity class name
	 * @return an new {@link Reference} for specified class name
	 */
	public static Reference newReference(final Class<?> clazz) {
		return REFERENCE_FACTORY.newReference(clazz);
	}

	/**
	 * Obtain a {@link Reference} on an {@link Entity} representation
	 * 
	 * @param any
	 *            an {@link Entity} representation
	 * @return an new {@link Reference} on specified entity
	 * @throws {@link IllegalArgumentException} if any is not an entity
	 *         representation
	 */
	public static Reference identify(final Object any) throws IllegalArgumentException {
		return discover(any).identity();
	}

	/**
	 * Discover underlying {@link Entity} instance.
	 * 
	 * @param any
	 *            an {@link Entity} representation
	 * @return an {@link Entity} instance
	 * @throws IllegalArgumentException
	 *             if any is not an entity representation
	 */
	public static Entity discover(final Object any) throws IllegalArgumentException {
		if (Proxy.isProxyClass(any.getClass())) {
			final EntityProxy entityProxy = (EntityProxy) Proxy.getInvocationHandler(any);
			return entityProxy;
		}
		if (EntityProxy.class.isAssignableFrom(any.getClass())) {
			final EntityProxy entityProxy = (EntityProxy) any;
			return entityProxy;
		}
		if (Entity.class.isAssignableFrom(any.getClass())) {
			final Entity entity = (Entity) any;
			return entity;
		}
		throw new IllegalArgumentException("Argument is not an entity");
	}

	/**
	 * Try to locate a ReferenceFactory implementation using specific class
	 * loader.
	 * 
	 * @param classLoader
	 * @return a ReferenceFactory instance or null or none was found.
	 */
	private static ReferenceFactory lookup(final ClassLoader classLoader) {
		final ServiceLoader<ReferenceFactory> loader = ServiceLoader.load(ReferenceFactory.class, classLoader);
		final Iterator<ReferenceFactory> iterator = loader.iterator();
		ReferenceFactory factory = null;
		while ((factory == null) && iterator.hasNext()) {
			try {
				factory = iterator.next();
			} catch (final Throwable e) {
			}
		}
		if (factory == null) {
			throw new RuntimeException("No ReferenceFactory Implementation found");
			// factory = new ReferenceFactoryProvider();
		}
		return factory;
	}
}
