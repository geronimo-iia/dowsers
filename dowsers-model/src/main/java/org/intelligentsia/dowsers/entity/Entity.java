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

import org.intelligentsia.dowsers.entity.meta.MetaEntityContextAccessor;

/**
 * Entity.
 * 
 * An entity is defined by:
 * <ul>
 * <li>An unique identity. Her identity does not change when any of its
 * attributes change</li>
 * <li>A set of attributes. Attributes are characteristics of entities</li>
 * </ul>
 * 
 * Examples: Customer, Order, ...
 * 
 * <p>
 * What is a value object (an Attribute) ?
 * </p>
 * <ul>
 * <li>“A Value Object cannot live on its own without an Entity.”</li>
 * <li>Eric Evans:<br />
 * “An object that represents a descriptive aspect of the domain with no
 * conceptual identity is called a VALUE OBJECT. VALUE OBJECTS are instantiated
 * to represent elements of the design that we care about only for what they
 * are, not who or which they are.” [Evans 2003]</li>
 * </ul>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface Entity extends MetaEntityContextAccessor {
	/**
	 * @return {@link Entity}'s identity
	 */
	String getIdentity();

	/**
	 * Return typed value of specified attribute name.
	 * 
	 * @param name
	 *            The name of the attribute to get.
	 * @return object value instance of specified name or null if none is found.
	 * @throws NullPointerException
	 *             if name is null
	 * @throws IllegalArgumentException
	 *             if argument is invalid
	 * @param <Value>
	 *            Object value class
	 */
	public <Value> Value attribute(String name) throws NullPointerException, IllegalArgumentException;

	/**
	 * Set typed value of specified attribute name.
	 * 
	 * @param name
	 *            The name of the attribute to set.
	 * @param value
	 *            A value to set for the attribute.
	 * @throws NullPointerException
	 *             if name is null
	 * @throws IllegalArgumentException
	 *             if argument is invalid
	 * @param <Value>
	 *            Object value class
	 * @return this {@link Entity} instance.
	 */
	public <Value> Entity attribute(String name, final Value value) throws NullPointerException, IllegalArgumentException;

}
