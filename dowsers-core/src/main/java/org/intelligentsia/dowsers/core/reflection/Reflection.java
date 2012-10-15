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

import java.lang.reflect.Field;

/**
 * Reflection.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public enum Reflection {
	;

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
}
