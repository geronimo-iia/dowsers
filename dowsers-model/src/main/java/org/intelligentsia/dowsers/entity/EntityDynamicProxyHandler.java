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
package org.intelligentsia.dowsers.entity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import org.intelligentsia.dowsers.entity.meta.MetaEntityContextAccessor;

/**
 * EntityDynamicProxyHandler implements {@link InvocationHandler}.
 * 
 * TODO add doc on methods mapping
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityDynamicProxyHandler implements InvocationHandler, Entity {

	private final Entity entity;
	private final Class<?> interfaceName;
	private final boolean isEntity;
	private final boolean isMetaEntityContextAccessorAware;

	/**
	 * Build a new instance of EntityDynamicProxyHandler.java.
	 * 
	 * @param interfaceName
	 *            public interface name
	 * @param entity
	 *            implementation instance
	 */
	public EntityDynamicProxyHandler(final Class<?> interfaceName, final Entity entity) {
		this.interfaceName = interfaceName;
		this.entity = entity;
		isEntity = Entity.class.isAssignableFrom(interfaceName);
		isMetaEntityContextAccessorAware = MetaEntityContextAccessor.class.isAssignableFrom(interfaceName);
	}

	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		final String methodName = method.getName();
		// MetaEntityContext stuff
		if (isMetaEntityContextAccessorAware && "getMetaEntityContext".equals(methodName)) {
			return entity.metaEntityContext();
		}
		// Entity stuff
		if (isEntity) {
			if ("identity".equals(methodName)) {
				return entity.identity();
			}
		}
		// attributes catch
		if (methodName.equals("attribute")) {
			if (args.length == 1) {
				return entity.attribute((String) args[0]);
			}
			entity.attribute((String) args[0], args[1]);
			return proxy;
		}
		// Dynamic Stuff based on Getter/Setter pattern
		if (methodName.startsWith("get")) {
			return entity.attribute(toFieldName(methodName));
		} else if (methodName.startsWith("set")) {
			entity.attribute(toFieldName(methodName), args[0]);
			// setter should return void
			return proxy;
		}
		// object method base
		if (methodName.equals("hashCode")) {
			return new Integer(entity.hashCode());
		} else if (methodName.equals("equals")) {
			return entity.equals(args[0]);
		} else if (methodName.equals("toString")) {
			return interfaceName + "#" + entity.identity();
		}
		// Dynamic attributes call
		if (entity.metaEntityContext().contains(methodName)) {
			if (method.getParameterTypes().length == 0) {
				return entity.attribute(methodName);
			}
			if (method.getParameterTypes().length == 1) {
				entity.attribute(methodName, args[0]);
				return proxy;
			}
		}
		// Oops WTF ?
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
	public MetaEntityContext metaEntityContext() {
		return entity.metaEntityContext();
	}

	@Override
	public String identity() {
		return entity.identity();
	}

	@Override
	public <Value> Value attribute(final String name) throws NullPointerException {
		return entity.attribute(name);
	}

	@Override
	public <Value> Entity attribute(final String name, final Value value) throws NullPointerException {
		entity.attribute(name, value);
		return this;
	}

	protected static String toFieldName(final String methodName) {
		final String name = methodName.substring(3);
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}
}
