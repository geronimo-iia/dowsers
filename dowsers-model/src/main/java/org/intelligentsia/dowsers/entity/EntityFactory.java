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
import java.lang.reflect.Proxy;

public class EntityFactory {

	private static Entity newEntity(String identity) {
		return new ConsoleChangeDecorateur(identity != null ? new BaseEntity(identity) : new BaseEntity());
	}

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<T> interfaceName, ClassLoader classLoader, String identity) {
		// create backup instance
		final Entity entity = newEntity(identity);
		// create dynamic proxy
		return (T) Proxy.newProxyInstance(classLoader, new Class[] { interfaceName }, new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				String methodName = method.getName();
				if (methodName.equals("getIdentity")) {
					return entity.getIdentity();
				} else if (methodName.equals("getProperty")) {
					return entity.getProperty((String) args[0]);
				} else if (methodName.equals("setProperty")) {
					entity.setProperty((String) args[0], args[1]);
				} else if (methodName.startsWith("get")) {
					return entity.getProperty(toFieldName(methodName));
				} else if (methodName.startsWith("set")) {
					entity.setProperty(toFieldName(methodName), args[0]);
				}
				// object method base
				if (methodName.equals("hashCode")) {
					return entity.hashCode();
				} else if (methodName.equals("equals")) {
					return entity.equals(args[0]);
				} else if (methodName.equals("toString")) {
					return entity.toString();
				}
				// what we can do
				return nullValueOf(method.getReturnType());
			}

			private Object nullValueOf(Class<?> returnType) {
				return null;
			}

			private String toFieldName(String methodName) {
				String name = methodName.substring(3);
				return name.substring(0, 1).toLowerCase() + name.substring(1);
			}
		});
	}
}
