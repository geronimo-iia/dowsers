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
package org.intelligentsia.dowsers.core.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reflection.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public enum Reflection {
	;

	/**
	 * @param clazz
	 * @return accessible default constructor or null if none was found.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Constructor<T> findDefaultConstructor(final Class<T> clazz) {
		for (final Constructor<?> constructor : clazz.getConstructors()) {
			if (constructor.getParameterTypes().length == 0) {
				if (!constructor.isAccessible()) {
					constructor.setAccessible(true);
				}
				return (Constructor<T>) constructor;
			}
		}
		return null;
	}

	/**
	 * Capitalize first letter.
	 * 
	 * @param name
	 *            name to capitalize
	 * @param prefix
	 *            prefix to add
	 * @return capitalized string.
	 */
	public static String capitalize(final String prefix, final String name) {
		return new StringBuilder(prefix).append(name.substring(0, 1).toUpperCase()).append(name.substring(1)).toString();
	}

	/**
	 * UnCapitalize first letter.
	 * 
	 * @param name
	 *            name to uncapitalized
	 * @return uncapitalized string.
	 */
	public static String uncapitalize(final String name) {
		return new StringBuilder(name.substring(0, 1).toLowerCase()).append(name.substring(1)).toString();
	}

	/**
	 * Extract field name from method name according getter/setter pattern.
	 * 
	 * @param methodName
	 * @return name
	 */
	public static String toFieldName(final String methodName) {
		final String name = methodName.substring(3);
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}

	/**
	 * Extract field name from method name by removing specified prefix if
	 * exists and lower first char.
	 * 
	 * @param methodName
	 * @param prefix
	 *            prefix to remove
	 * @return name
	 */
	public static String toFieldName(final String methodName, String prefix) {
		if (methodName.startsWith(prefix)) {
			final String name = methodName.substring(prefix.length());
			return name.substring(0, 1).toLowerCase() + name.substring(1);
		}
		return methodName.substring(0, 1).toLowerCase();
	}

	/**
	 * Find specified field name on specified class. Set field accessible if
	 * it's necessary.
	 * 
	 * @param clazz
	 *            class where to search
	 * @param name
	 *            field name to search
	 * @return {@link Field} instance or null if none is found.
	 */
	public static Field findField(final Class<?> clazz, final String name) {
		final Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			final Field field = fields[i];
			if (name.equals(field.getName())) {
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}
				return field;
			}
		}
		return null;
	}

	/**
	 * Find specified Method name on specified class. Set Method accessible if
	 * it's necessary.
	 * 
	 * @param clazz
	 *            class where to search
	 * @param name
	 *            Method name to search
	 * @return {@link Method} instance or null if none is found.
	 */
	public static Method findMethod(final Class<?> clazz, final String name) {
		final Method[] methods = clazz.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			final Method method = methods[i];
			if (name.equals(method.getName())) {
				if (!method.isAccessible()) {
					method.setAccessible(true);
				}
				return method;
			}
		}
		return null;
	}

	/**
	 * Get all generic class of specified instance.
	 * 
	 * @param clazz
	 *            class to analyse
	 * @return a {@link List} of generic {@link Class}>
	 */
	public static List<Class<?>> findGenericClass(final Class<?> clazz) {
		final Type type = clazz.getGenericSuperclass();
		if (type == null) {
			return new ArrayList<Class<?>>(0);
		}
		final Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
		if (type instanceof ParameterizedType) {
			final ParameterizedType parameterizedType = (ParameterizedType) type;
			final Class<?> rawType = (Class<?>) parameterizedType.getRawType();
			final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
			final TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
			for (int i = 0; i < actualTypeArguments.length; i++) {
				resolvedTypes.put(typeParameters[i], actualTypeArguments[i]);
			}
		}
		// finally, for each actual type argument provided to baseClass,
		// determine (if possible)
		// the raw class for that type argument.
		Type[] actualTypeArguments;
		if (type instanceof Class) {
			actualTypeArguments = ((Class<?>) type).getTypeParameters();
		} else {
			actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
		}
		final List<Class<?>> typeArgumentsAsClasses = new ArrayList<Class<?>>();
		// resolve types by chasing down type variables.
		for (Type baseType : actualTypeArguments) {
			while (resolvedTypes.containsKey(baseType)) {
				baseType = resolvedTypes.get(baseType);
			}
			typeArgumentsAsClasses.add(getClass(baseType));
		}
		return typeArgumentsAsClasses;
	}

	/**
	 * Get all generic class of specified instance.
	 * 
	 * @param instance
	 *            object instance to analyse
	 * @return a {@link List} of generic {@link Class}>
	 */
	public static List<Class<?>> findGenericClass(final Object instance) {
		return findGenericClass(instance.getClass());
	}

	/**
	 * Get the underlying class for a type, or null if the type is a variable
	 * type.
	 * 
	 * @param type
	 *            the type
	 * @return the underlying class
	 */
	private static Class<?> getClass(final Type type) {
		if (type instanceof Class) {
			return (Class<?>) type;
		} else if (type instanceof ParameterizedType) {
			return getClass(((ParameterizedType) type).getRawType());
		} else if (type instanceof GenericArrayType) {
			final Type componentType = ((GenericArrayType) type).getGenericComponentType();
			final Class<?> componentClass = getClass(componentType);
			if (componentClass != null) {
				return Array.newInstance(componentClass, 0).getClass();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}
