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
package org.intelligentsia.dowsers.core;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.UUID;

/**
 * This provider declare a method in order to generate new identifier using an
 * IdentifierFactory implementation.
 * 
 * <pre>IdentifierFactoryProvider.generateNewIdentifier();
 * 
 * <pre>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public final class IdentifierFactoryProvider {

	/**
	 * Inner IdentifierFactory instance.
	 */
	private final IdentifierFactory identifierFactory;

	/**
	 * Singleton Holder Pattern.
	 */
	private static class IdentifierFactoryHolder {
		/**
		 * IdentifierFactoryProvider instance.
		 */
		private static IdentifierFactoryProvider identifierFactoryProvider = new IdentifierFactoryProvider();
	}

	/**
	 * @return a new generated identifier.
	 */
	public static String generateNewIdentifier() {
		return IdentifierFactoryHolder.identifierFactoryProvider.identifierFactory.generateNewIdentifier();
	}

	/**
	 * Build a new instance of IdentifierFactoryProvider.
	 * 
	 * Lookup with classloader of current thread, if none is found, try with
	 * IdentifierFactory.class, and default with inner implementation using
	 * UUID.
	 */
	private IdentifierFactoryProvider() {
		super();
		IdentifierFactory factory = lookup(Thread.currentThread().getContextClassLoader());
		if (factory == null) {
			factory = lookup(IdentifierFactory.class.getClassLoader());
		}
		if (factory == null) {
			factory = new DefaultIdentifierFactory();
		}
		identifierFactory = factory;
	}

	/**
	 * Try to locate a IdentifierFactory implementation using specific
	 * classloader.
	 * 
	 * @param classLoader
	 * @return an IdentifierFactory instance or null ir none was found.
	 */
	private IdentifierFactory lookup(final ClassLoader classLoader) {
		final ServiceLoader<IdentifierFactory> loader = ServiceLoader.load(IdentifierFactory.class, classLoader);
		final Iterator<IdentifierFactory> iterator = loader.iterator();
		IdentifierFactory identifierFactory = null;
		while ((identifierFactory == null) && iterator.hasNext()) {
			try {
				identifierFactory = iterator.next();
			} catch (final Throwable e) {
			}
		}
		return identifierFactory;
	}

	/**
	 * IdentifierFactory declare methode to generate new identifier.
	 * 
	 * <pre>
	 * IdentifierFactoryProvider.generateNewIdentifier();
	 * 
	 * <pre>
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	public interface IdentifierFactory {

		/**
		 * @return a new generated identifier.
		 */
		public abstract String generateNewIdentifier();

	}

	/**
	 * 
	 * DefaultIdentifierFactory extends abstract and use UUID class as
	 * identifier.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	private class DefaultIdentifierFactory implements IdentifierFactory {

		/**
		 * @see org.intelligentsia.dowsers.domain.IdentifierFactory#generateNewIdentifier()
		 */
		@Override
		public String generateNewIdentifier() {
			return UUID.randomUUID().toString();
		}

	}
}
