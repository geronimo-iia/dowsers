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

/**
 * EntityManager declare methods to manage an {@link Entity}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public interface EntityManager {

	/**
	 * Create a new instance of specified class name. Identity of this new
	 * instance will be generated.
	 * 
	 * @param className
	 *            entity class name
	 * @return an instance of specified interface name.
	 * @throws NullPointerException
	 *             if className or classLoader is null
	 * @throws IllegalArgumentException
	 *             if className is empty
	 */
	<T> T newInstance(Class<T> className) throws NullPointerException, IllegalArgumentException;

	/**
	 * Create a new instance of specified class name.
	 * 
	 * @param className
	 *            entity class name
	 * @param identity
	 *            identity of this instance
	 * @return an instance of specified interface name.
	 * @throws NullPointerException
	 *             if className, classLoader or identity is null
	 * @throws IllegalArgumentException
	 *             if className or identity is empty
	 */
	<T> T newInstance(Class<T> className, String identity) throws NullPointerException, IllegalArgumentException;

}
