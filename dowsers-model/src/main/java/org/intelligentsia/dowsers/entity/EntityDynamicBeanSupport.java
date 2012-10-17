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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.intelligentsia.dowsers.core.reflection.Reflection;
import org.intelligentsia.dowsers.entity.meta.MetaEntityContext;

import com.google.common.annotations.VisibleForTesting;

/**
 * EntityDynamicBeanSupport is the base class for all POJO {@link Entity} with
 * additional dynamic properties.
 * 
 * 
 * First implementation of reflexive way. May we have a better deal with
 * Attribute Handler way...
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityDynamicBeanSupport extends EntityDynamicSupport {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = 4852179303357826154L;

	public EntityDynamicBeanSupport(String identity, MetaEntityContext metaEntityContext) throws NullPointerException, IllegalArgumentException {
		super(identity, metaEntityContext);
	}

	public EntityDynamicBeanSupport(String identity, MetaEntityContext metaEntityContext, Map<String, Object> attributes) throws NullPointerException, IllegalArgumentException {
		super(identity, metaEntityContext, attributes);
	}

	/**
	 * @throws IllegalStateException
	 *             if an error occur when invoking method or field.
	 * @see org.intelligentsia.dowsers.entity.Entity#attribute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <Value> Value attribute(final String name) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		Value value = super.attribute(name);
		if (value == null) {
			final Method method = findGetter(name);
			if (method != null) {
				try {
					value = (Value) method.invoke(this);
				} catch (final Throwable e) {
					throw new IllegalStateException(e);
				}
			} else {
				final Field field = Reflection.findField(getClass(), name);
				if (field != null) {
					try {
						value = (Value) field.get(this);
					} catch (final Throwable e) {
						throw new IllegalStateException(e);
					}
				}
			}
		}
		return value;
	}

	@Override
	public <Value> Entity attribute(final String name, final Value value) throws NullPointerException {
		final Method method = findSetter(name, value.getClass());
		if (method != null) {
			try {
				method.invoke(this, value);
			} catch (final Throwable e) {
				throw new IllegalStateException(e);
			}
		} else {
			final Field field = Reflection.findField(getClass(), name);
			if (field != null) {
				try {
					field.set(this, value);
				} catch (final Throwable e) {
					throw new IllegalStateException(e);
				}
			} else {
				super.attribute(name, value);
			}
		}
		return this;
	}

	/**
	 * Try to find a setter method associated with the specified name. Try to
	 * find {name}(<T>) or 'set'{Capitalize({name})}(<T>)
	 * 
	 * @param name
	 *            attribute name
	 * @param class1
	 *            attribut type
	 * @return a {@link Method} instance or null if none is found
	 */
	@VisibleForTesting
	Method findSetter(final String name, final Class<? extends Object> argClazz) {
		final String methodName = Reflection.capitalize("set", name);
		final Method[] methods = getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			final Method method = methods[i];
			if ((methodName.equals(method.getName()) || name.equals(method.getName())) && (method.getParameterTypes().length == 1) && argClazz.isAssignableFrom(method.getParameterTypes()[0]) && (Void.TYPE == method.getReturnType())) {
				if (!method.isAccessible()) {
					method.setAccessible(true);
				}
				return method;
			}
		}
		return null;
	}

	/**
	 * Try to find a getter method associated with the specified name. Try to
	 * find {name}():<T> or 'get'{Capitalize({name})}():<T>
	 * 
	 * @param name
	 *            attribute name
	 * @return a {@link Method} instance or null if none is found
	 */
	@VisibleForTesting
	Method findGetter(final String name) {
		final String methodName = Reflection.capitalize("get", name);
		final Method[] methods = getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			final Method method = methods[i];
			if ((methodName.equals(method.getName()) || name.equals(method.getName())) && (method.getParameterTypes().length == 0)) {
				if (!method.isAccessible()) {
					method.setAccessible(true);
				}
				return method;
			}
		}
		return null;
	}

}
