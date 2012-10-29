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
	 * @return an new {@link Reference} on specified entity
	 * @throws {@link IllegalArgumentException} if any is not an entity
	 *         representation
	 */
	public static Reference identify(final Object any) throws IllegalArgumentException {
		if (Proxy.isProxyClass(any.getClass())) {
			final EntityProxy entityProxy = (EntityProxy) Proxy.getInvocationHandler(any);
			return entityProxy.identity();
		}
		if (EntityProxy.class.isAssignableFrom(any.getClass())) {
			final EntityProxy entityProxy = (EntityProxy) any;
			return entityProxy.identity();
		}
		if (Entity.class.isAssignableFrom(any.getClass())) {
			final Entity entity = (Entity) any;
			return entity.identity();
		}
		throw new IllegalArgumentException("Argument is not an entity");
	}

	//
	// /**
	// * @param any
	// * entity representation
	// * @return an urn which identify an {@link Entity}.
	// * @throws {@link IllegalArgumentException} if any is not an entity
	// * representation
	// */
	// public static String newEntityReference(final Object any) throws
	// IllegalArgumentException {
	// return newAttributeReference(any, IDENTITY);
	// }
	//
	// /**
	// * @param clazz
	// * entity class
	// * @param identity
	// * identifier part
	// * @return a reference on specified entity instance
	// */
	// public static String newEntityReference(final Class<?> clazz, final
	// String identity) {
	// return newAttributeReference(clazz, IDENTITY, identity);
	// }
	//
	// /**
	// * @param clazz
	// * @return a entity collection reference (aka class)
	// */
	// public static String newEntityCollectionReference(final Class<?> clazz) {
	// return newAttributeReference(clazz, null, null);
	// }
	//
	// /**
	// * @param any
	// * entity representation
	// * @param attributeName
	// * attribute Name to reference
	// * @return an urn which identify an attribute of specified entity.
	// * @throws {@link IllegalArgumentException} if any is not an entity
	// * representation
	// */
	// public static String newAttributeReference(final Object any, final String
	// attributeName) throws IllegalArgumentException {
	// if (Proxy.isProxyClass(any.getClass())) {
	// final EntityProxy entityProxy = (EntityProxy)
	// Proxy.getInvocationHandler(any);
	// return newAttributeReference(entityProxy, attributeName);
	// }
	// if (EntityProxy.class.isAssignableFrom(any.getClass())) {
	// final EntityProxy entityProxy = (EntityProxy) any;
	// return newAttributeReference(entityProxy, attributeName);
	// }
	// if (Entity.class.isAssignableFrom(any.getClass())) {
	// final Entity entity = (Entity) any;
	// return newAttributeReference(entity.getClass(), attributeName,
	// entity.identity());
	// }
	// throw new IllegalArgumentException("Argument is not an entity");
	//
	// }
	//
	// /**
	// * @param entityProxy
	// * proxy of entity representation
	// * @param attributeName
	// * attribute Name to reference
	// * @return an urn which identify an attribute of specified entity.
	// */
	// public static String newAttributeReference(final EntityProxy entityProxy,
	// final String attributeName) {
	// return newAttributeReference(entityProxy.getInterfaceName(),
	// attributeName, entityProxy.identity());
	// }
	//
	// /**
	// * @param className
	// * entity class name
	// * @param attributeName
	// * attribute name to reference
	// * @param identity
	// * @return an urn which identify an attribute of specified entity.
	// */
	// public static String newAttributeReference(final Class<?> clazz, final
	// String attributeName, final String identity) {
	// final StringBuilder builder = new
	// StringBuilder(URN_DOWSERS).append(clazz.getName()).append(URN_SEPARATOR);
	// if (attributeName != null) {
	// builder.append(attributeName);
	// }
	// if (identity != null) {
	// builder.append(URN_IDENTIFIER_SEPARATOR).append(identity);
	// }
	// return builder.toString();
	// }
	//
	// /**
	// * @param uri
	// * @return identity part of an urn entity reference
	// */
	// public static String getIdentity(final URI uri) {
	// return uri.getFragment();
	// }
	//
	// /**
	// * @param uri
	// * @return identity part of an urn entity reference
	// */
	// public static String getIdentity(final String urn) {
	// final int index = urn.indexOf(URN_IDENTIFIER_SEPARATOR);
	// return index < 0 ? null : urn.substring(index + 1);
	// }
	//
	// /**
	// * @param uri
	// * @return entity part of an urn reference
	// */
	// public static String getEntityClassName(final URI uri) {
	// final String ssp = uri.getSchemeSpecificPart();
	// return ssp.substring(ssp.indexOf(URN_SEPARATOR) + 1,
	// ssp.lastIndexOf(URN_SEPARATOR));
	// }
	//
	// /**
	// * @param uri
	// * @return entity part of an urn reference
	// */
	// public static String getEntityClassName(final String urn) {
	// return urn.substring(URN_DOWSERS.length(),
	// urn.lastIndexOf(URN_SEPARATOR));
	// }
	//
	// /**
	// * @param uri
	// * @return attribute name of an urn attribute reference
	// */
	// public static String getAttributeName(final URI uri) {
	// final String ssp = uri.getSchemeSpecificPart();
	// return ssp.substring(ssp.lastIndexOf(URN_SEPARATOR) + 1);
	// }
	//
	// /**
	// * @param uri
	// * @return attribute name of an urn attribute reference
	// */
	// public static String getAttributeName(final String urn) {
	// }
	// final int index = urn.indexOf(URN_IDENTIFIER_SEPARATOR);
	// return index < 0 ? urn.substring(urn.lastIndexOf(URN_SEPARATOR) + 1) :
	// urn.substring(urn.lastIndexOf(URN_SEPARATOR) + 1, index);

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
