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

/**
 * Entity.
 * <p>
 * An entity is defined by:
 * </p>
 * <ul>
 * <li>An unique identity. Her identity does not change when any of its
 * attributes change</li>
 * <li>A set of Properties (@see {@link Property})</li>
 * </ul>
 * Examples: Customer, Order, ...
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface Entity {
	/**
	 * @return {@link Entity}'s identity
	 */
	String getIdentity();

	/**
	 * Return typed property for specified name.
	 * 
	 * @param name
	 *            property name
	 * @return a instance of {@link Property} or null if none is found.
	 * @throws NullPointerException
	 *             if name is null
	 */
	Property getProperty(String name) throws NullPointerException;

}
