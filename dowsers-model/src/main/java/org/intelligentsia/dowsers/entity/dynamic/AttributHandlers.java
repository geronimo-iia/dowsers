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
package org.intelligentsia.dowsers.entity.dynamic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * AttributHandlers.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public enum AttributHandlers {
	;

	public static AttributHandler newMappedAttributHandler(final String name, final Map<String, Object> data) {
		return new AttributHandler() {

			@Override
			public <T> void set(T value) {
				data.put(name, value);
			}

			@SuppressWarnings("unchecked")
			@Override
			public <T> T get() {
				return (T) data.get(name);
			}
		};
	}

	public static AttributHandler newFieldAttributHandler(final Field field, final Object instance) {
		return new AttributHandler() {

			@Override
			public <T> void set(T value) {
				try {
					field.set(instance, value);
				} catch (final Throwable e) {
					throw new IllegalStateException(e);
				}
			}

			@SuppressWarnings("unchecked")
			@Override
			public <T> T get() {
				try {
					return (T) field.get(instance);
				} catch (final Throwable e) {
					throw new IllegalStateException(e);
				}
			}
		};
	}

	public static AttributHandler newGetterMethodAttributHandler(final Method method, final Object instance) {
		return new AttributHandler() {

			@SuppressWarnings("unchecked")
			@Override
			public <T> T get() {
				try {
					return (T) method.invoke(instance);
				} catch (final Throwable e) {
					throw new IllegalStateException(e);
				}
			}

			@Override
			public <T> void set(T value) {
				throw new IllegalStateException();
			}

		};
	}

	public static AttributHandler newSetterMethodAttributHandler(final Method method, final Object instance) {
		return new AttributHandler() {

			@Override
			public <T> T get() {
				throw new IllegalStateException();
			}

			@Override
			public <T> void set(T value) {
				try {
					method.invoke(instance, value);
				} catch (final Throwable e) {
					throw new IllegalStateException(e);
				}
			}

		};
	}
}
