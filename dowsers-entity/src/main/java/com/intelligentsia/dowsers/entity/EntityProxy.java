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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.intelligentsia.dowsers.core.reflection.Reflection;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.serializer.EntityProxyHandler;

/**
 * EntityProxy implements {@link InvocationHandler}.
 * 
 * TODO add doc on methods mapping
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityProxy implements InvocationHandler, EntityProxyHandler {

	/**
	 * Exposed interface.
	 */
	private final transient Class<?> interfaceName;

	/**
	 * Inner instance of Entity.
	 */
	private final transient Entity entity;

	/**
	 * Build a new instance of EntityProxy.
	 * 
	 * @param interfaceName
	 *            public interface name
	 * @param entity
	 *            implementation instance
	 */
	public EntityProxy(final Class<?> interfaceName, final Entity entity) {
		this.interfaceName = interfaceName;
		this.entity = entity;
	}

	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		final String methodName = method.getName();
		// object method base
		if (methodName.equals("hashCode")) {
			return new Integer(entity.hashCode());
		} else if (methodName.equals("equals")) { // Equals Stuff
			Object object = args[0];
			if (Proxy.isProxyClass(object.getClass())) {
				final InvocationHandler handler = Proxy.getInvocationHandler(object);
				if (EntityProxy.class.isAssignableFrom(handler.getClass())) {
					object = ((EntityProxy) handler).entity;
				}
			}
			return entity.equals(object);
		} else if (methodName.equals("toString")) { // To String Stuff
			return entity.toString();
		}
		// Entity stuff
		if ("identity".equals(methodName)) {
			return entity.identity();
		}
		if (methodName.equals("attribute")) {
			if (args.length == 1) {
				return entity.attribute((String) args[0]);
			}
			entity.attribute((String) args[0], args[1]);
			return proxy;
		}
		if (methodName.equals("contains")) {
			return entity.contains((String) args[0]);
		}
		if (methodName.equals("attributeNames")) {
			return entity.attributeNames();
		}
		if ("metaEntityContext".equals(methodName)) {
			return entity.metaEntityContext();
		}

		// Dynamic Stuff based on Getter/Setter pattern
		if (methodName.startsWith("get")) {
			return entity.attribute(Reflection.toFieldName(methodName));
		} else if (methodName.startsWith("set")) {
			entity.attribute(Reflection.toFieldName(methodName), args[0]);
			return proxy;
		}

		// properties pattern without get/set prefix
		if (entity.metaEntityContext().containsMetaAttribute(methodName)) {
			if (method.getParameterTypes().length == 0) {
				return entity.attribute(methodName);
			}
			if (method.getParameterTypes().length == 1) {
				entity.attribute(methodName, args[0]);
				return proxy;
			}
		}

		// Oops WTF ?
		// TODO try to find method on Entity instance and call it

		return null;
	}

	public Entity getEntity() {
		return entity;
	}

	public Class<?> getInterfaceName() {
		return interfaceName;
	}

	@Override
	public Reference identity() {
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

	@Override
	public ImmutableSet<String> attributeNames() {
		return entity.attributeNames();
	}

	@Override
	public MetaEntityContext metaEntityContext() {
		return entity.metaEntityContext();
	}

	@Override
	public boolean contains(final String name) throws NullPointerException, IllegalArgumentException {
		return entity.contains(name);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(entity, interfaceName.getName());
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final EntityProxy other = (EntityProxy) obj;
		return Objects.equal(interfaceName.getName(), other.interfaceName.getName()) && Objects.equal(entity, other.entity);
	}

}
