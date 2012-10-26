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
import java.net.URI;
import java.net.URISyntaxException;

import com.google.common.base.Throwables;

/**
 * Reference generate {@link Entity} or attribute's {@link Entity} reference.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public enum Reference {
	;
	/**
	 * @param any
	 *            entity representation
	 * @return an urn which identify an {@link Entity}.
	 * @throws {@link IllegalArgumentException} if any is not an entity
	 *         representation
	 */
	public static URI newReference(final Object any) throws IllegalArgumentException {
		return newReference(any, "identity");
	}

	/**
	 * @param any
	 *            entity representation
	 * @param attributeName
	 *            attribute Name to reference
	 * @return an urn which identify an attribute of specified entity.
	 * @throws {@link IllegalArgumentException} if any is not an entity
	 *         representation
	 */
	public static URI newReference(final Object any, final String attributeName) throws IllegalArgumentException {
		if (Proxy.isProxyClass(any.getClass())) {
			final EntityProxy entityProxy = (EntityProxy) Proxy.getInvocationHandler(any);
			return newReference(entityProxy, attributeName);
		}
		if (EntityProxy.class.isAssignableFrom(any.getClass())) {
			final EntityProxy entityProxy = (EntityProxy) any;
			return newReference(entityProxy, attributeName);
		}
		if (Entity.class.isAssignableFrom(any.getClass())) {
			final Entity entity = (Entity) any;
			return newReference(entity.getClass(), attributeName, entity.identity());
		}
		throw new IllegalArgumentException("Argument is not an entity");

	}

	/**
	 * @param entityProxy
	 *            proxy of entity representation
	 * @param attributeName
	 *            attribute Name to reference
	 * @return an urn which identify an attribute of specified entity.
	 */
	public static URI newReference(final EntityProxy entityProxy, final String attributeName) {
		return newReference(entityProxy.getInterfaceName(), attributeName, entityProxy.identity());
	}

	/**
	 * @param clazz
	 * @return a entity collection reference (aka class)
	 */
	public static URI newReference(final Class<?> clazz) {
		return newReference(clazz, null, null);
	}

	/**
	 * @param uri
	 * @return identity part of an urn entity reference
	 */
	public static String getIdentity(final URI uri) {
		return uri.getFragment();
	}

	/**
	 * @param uri
	 * @return entity part of an urn reference
	 */
	public static String getEntityPart(final URI uri) {
		final String ssp = uri.getSchemeSpecificPart();
		return ssp.substring(ssp.indexOf(':') + 1, ssp.lastIndexOf(':'));
	}

	/**
	 * @param uri
	 * @return attribute name of an urn attribute reference
	 */
	public static String getAttributPart(final URI uri) {
		final String ssp = uri.getSchemeSpecificPart();
		return ssp.substring(ssp.lastIndexOf(':') + 1);
	}

	/**
	 * @param className
	 *            entity class name
	 * @param attributeName
	 *            attribut name
	 * @param identity
	 * @return an urn which identify an attribute of specified entity.
	 * @throws URISyntaxException
	 */
	public static URI newReference(final Class<?> clazz, final String attributeName, final String identity) {
		StringBuilder builder = new StringBuilder("dowsers:").append(clazz.getName()).append(':');
		if (attributeName != null) {
			builder.append(attributeName);
		}
		try {
			return new URI("urn", builder.toString(), identity);
		} catch (URISyntaxException e) {
			throw Throwables.propagate(e);
		}
	}

}
