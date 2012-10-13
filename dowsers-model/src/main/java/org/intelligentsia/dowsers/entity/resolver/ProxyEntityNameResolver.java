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
package org.intelligentsia.dowsers.entity.resolver;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.intelligentsia.dowsers.entity.EntityDynamicProxyHandler;

import com.google.common.base.Objects;

/**
 * ProxyEntityNameResolver implements {@link EntityNameResolver} and try to
 * resolve name with information from {@link EntityDynamicProxyHandler}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class ProxyEntityNameResolver extends EntityNameResolverSupport {
	/**
	 * Build a new instance of ProxyEntityNameResolver.java.
	 */
	public ProxyEntityNameResolver() {
		super();
	}

	@Override
	public String resolveEntityName(final Object entity) {
		if (Proxy.isProxyClass(entity.getClass())) {
			final InvocationHandler handler = Proxy.getInvocationHandler(entity);
			if (EntityDynamicProxyHandler.class.isAssignableFrom(handler.getClass())) {
				final EntityDynamicProxyHandler myHandler = (EntityDynamicProxyHandler) handler;
				return myHandler.getEntity().metaEntityContext().getName();
			}
		}
		if (hasNext()) {
			return next().resolveEntityName(entity);
		}
		return null;
	}

	@Override
	public final int hashCode() {
		return Objects.hashCode(getClass());
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
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Objects.toStringHelper(this).toString();
	}
}
