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
package org.intelligentsia.dowsers.model;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.intelligentsia.dowsers.core.IdentifierFactoryProvider;

import com.google.common.base.Preconditions;

/**
 * EntityManagerUnit.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityManagerUnit implements EntityManager {

	private final MetaEntityContextRepository metaEntityContextRepository;

	public EntityManagerUnit(final MetaEntityContextRepository metaEntityContextRepository) throws NullPointerException {
		super();
		this.metaEntityContextRepository = Preconditions.checkNotNull(metaEntityContextRepository);
	}

	@Override
	public <T> T newInstance(final Class<T> interfaceName, final ClassLoader classLoader) throws NullPointerException {
		return newInstance(interfaceName, classLoader, IdentifierFactoryProvider.generateNewIdentifier());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T newInstance(final Class<T> interfaceName, final ClassLoader classLoader, final String identity) throws NullPointerException {
		return (T) Proxy.newProxyInstance(classLoader, new Class[] { interfaceName }, new EntityInvocationHandler(interfaceName, identity));
	}

	/**
	 * EntityInvocationHandler.
	 */
	private class EntityInvocationHandler implements InvocationHandler, MetaEntityContextAware {

		private final Entity entity;
		private final MetaEntityContext metaEntityContext;
		private final boolean isEntity;

		private final boolean isMetaEntityContextAware;

		public EntityInvocationHandler(final Class<?> interfaceName, final String identity) {
			super();
			metaEntityContext = metaEntityContextRepository.find(interfaceName);
			entity = new AccessLoggerEntityDecorator(new BaseEntity(identity, metaEntityContext), System.out);
			isEntity = Entity.class.isAssignableFrom(interfaceName);
			isMetaEntityContextAware = MetaEntityContextAware.class.isAssignableFrom(interfaceName);
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

			final String methodName = method.getName();
			// MetaEntityContext stuff
			if (isMetaEntityContextAware && "getMetaEntityContext".equals(methodName)) {
				return getMetaEntityContext();
			}
			// Entity stuff
			if (isEntity) {
				if ("getIdentity".equals(methodName)) {
					return entity.getIdentity();
				}
				if ("getProperty".equals(methodName)) {
					return entity.getProperty((String) args[0]);
				}
			}
			// Dynamic Stuff
			if (methodName.startsWith("get")) {
				return entity.getProperty(toFieldName(methodName)).getValue();
			} else if (methodName.startsWith("set")) {
				entity.getProperty(toFieldName(methodName)).setValue(args[0]);
				return null;
			}
			// object method base
			if (methodName.equals("hashCode")) {
				return entity.hashCode();
			} else if (methodName.equals("equals")) {
				return entity.equals(args[0]);
			} else if (methodName.equals("toString")) {
				return entity.toString();
			}
			// default on proxy
			return method.invoke(proxy, args);
		}

		@Override
		public MetaEntityContext getMetaEntityContext() {
			return metaEntityContext;
		}

		public String toFieldName(final String methodName) {
			final String name = methodName.substring(3);
			return name.substring(0, 1).toLowerCase() + name.substring(1);
		}
	}
}
