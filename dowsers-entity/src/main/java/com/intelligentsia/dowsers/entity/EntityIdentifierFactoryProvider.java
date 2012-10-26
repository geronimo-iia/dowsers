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
import java.util.ServiceLoader;

import org.intelligentsia.dowsers.core.IdentifierFactoryProvider;

/**
 * 
 * EntityIdentifierFactoryProvider.
 * 
 * TODO study to replace Entity.identity by this reference
 * 
 */
public final class EntityIdentifierFactoryProvider {
	/**
	 * Inner EntityIdentifierFactory instance.
	 */
	private final EntityIdentifierFactory entityIdentifierFactory;

	public String generateNewIdentifier(Class<?> clazz) {
		return EntityIdentifierFactoryProviderHolder.entityIdentifierFactoryProvider.entityIdentifierFactory.generateNewIdentifier(clazz);
	}

	/**
	 * Singleton Holder Pattern.
	 */
	private static class EntityIdentifierFactoryProviderHolder {
		/**
		 * EntityIdentifierFactoryProvider instance.
		 */
		private static EntityIdentifierFactoryProvider entityIdentifierFactoryProvider = new EntityIdentifierFactoryProvider();
	}

	/**
	 * Entity declare methods to generate new identifier.
	 * 
	 * <pre>
	 * EntityIdentifierFactoryProvider.generateNewIdentifier();
	 * 
	 * <pre>
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	public interface EntityIdentifierFactory {

		/**
		 * @return a new generated identifier.
		 */
		public abstract String generateNewIdentifier(Class<?> clazz);

	}

	private EntityIdentifierFactoryProvider() {
		super();
		EntityIdentifierFactory factory = lookup(Thread.currentThread().getContextClassLoader());
		if (factory == null) {
			factory = lookup(Thread.currentThread().getContextClassLoader());
		}
		if (factory == null) {
			factory = new DefaultEntityIdentifierFactory();
		}
		entityIdentifierFactory = factory;
	}

	/**
	 * Try to locate a IdentifierFactory implementation using specific class
	 * loader.
	 * 
	 * @param classLoader
	 * @return an IdentifierFactory instance or null ir none was found.
	 */
	private EntityIdentifierFactory lookup(final ClassLoader classLoader) {
		final ServiceLoader<EntityIdentifierFactory> loader = ServiceLoader.load(EntityIdentifierFactory.class, classLoader);
		final Iterator<EntityIdentifierFactory> iterator = loader.iterator();
		EntityIdentifierFactory factory = null;
		while ((factory == null) && iterator.hasNext()) {
			try {
				factory = iterator.next();
			} catch (final Throwable e) {
			}
		}
		return factory;
	}

	private class DefaultEntityIdentifierFactory implements EntityIdentifierFactory {

		@Override
		public String generateNewIdentifier(Class<?> clazz) {
			return Reference.newReference(clazz, "identity", IdentifierFactoryProvider.generateNewIdentifier()).toString();
		}

	}
}
