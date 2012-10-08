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

import org.intelligentsia.dowsers.model.meta.MetaEntityContext;
import org.intelligentsia.dowsers.model.meta.MetaEntityContextAccessor;

/**
 * EntityProxyHandler implements {@link InvocationHandler}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityProxyHandler implements InvocationHandler, Entity {

	private final Entity entity;
	private final Class<?> interfaceName;
	private final boolean isEntity;
	private final boolean isMetaEntityContextAccessorAware;

	/**
	 * Build a new instance of EntityProxyHandler.java.
	 * 
	 * @param interfaceName
	 *            public interface name
	 * @param entity
	 *            implementation instance
	 */
	public EntityProxyHandler(final Class<?> interfaceName, final Entity entity) {
		this.interfaceName = interfaceName;
		this.entity = entity;
		isEntity = Entity.class.isAssignableFrom(interfaceName);
		isMetaEntityContextAccessorAware = MetaEntityContextAccessor.class.isAssignableFrom(interfaceName);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		final String methodName = method.getName();
		// MetaEntityContext stuff
		if (isMetaEntityContextAccessorAware && "getMetaEntityContext".equals(methodName)) {
			return entity.getMetaEntityContext();
		}
		// Entity stuff
		if (isEntity) {
			if ("getIdentity".equals(methodName)) {
				return entity.getIdentity();
			}
			if ("getProperty".equals(methodName)) {
				return entity.getProperty((String) args[0]);
			}
			if ("setProperty".equals(methodName)) {
				entity.setProperty((String) args[0], args[1]);
				return null;
			}
		}
		// Dynamic Stuff
		if (methodName.startsWith("get")) {
			return entity.getProperty(toFieldName(methodName));
		} else if (methodName.startsWith("set")) {
			entity.setProperty(toFieldName(methodName), args[0]);
			return null;
		}
		// object method base
		if (methodName.equals("hashCode")) {
			return new Integer(entity.hashCode());
		} else if (methodName.equals("equals")) {
			return entity.equals(args[0]);
		} else if (methodName.equals("toString")) {
			return interfaceName + "#" + entity.getIdentity();
		}
		return null;

	}

	/**
	 * @return underlying entity instance.
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * @return public interface name.
	 */
	public Class<?> getInterfaceName() {
		return interfaceName;
	}

	@Override
	public MetaEntityContext getMetaEntityContext() {
		return entity.getMetaEntityContext();
	}

	@Override
	public String getIdentity() {
		return entity.getIdentity();
	}

	@Override
	public <Value> Value getProperty(String name) throws NullPointerException {
		return entity.getProperty(name);
	}

	@Override
	public <Value> void setProperty(String name, Value value) throws NullPointerException {
		entity.setProperty(name, value);
	}

	protected static String toFieldName(final String methodName) {
		final String name = methodName.substring(3);
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}
}