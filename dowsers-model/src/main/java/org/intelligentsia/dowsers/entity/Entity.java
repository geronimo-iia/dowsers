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
 * Entity.
 * 
 * An entity:
 * <ul>
 * <li>Something with a unique identity</li>
 * <li>Identity does not change when any of its attributes change</li>
 * <li>Examples: Customer, Order, ...</li>
 * </ul>
 * 
 * At this opposite, what is a value object (aka a Property) ?
 * 
 * “A Value Object cannot live on its own without an Entity.”
 * 
 * Eric Evans:
 * 
 * “An object that represents a descriptive aspect of the domain with no
 * conceptual identity is called a VALUE OBJECT. VALUE OBJECTS are instantiated
 * to represent elements of the design that we care about only for what they
 * are, not who or which they are.” [Evans 2003]
 * 
 * As described in the DDD book, a value object have:
 * <ul>
 * <li>No conceptual identity,</li>
 * <li>Describe characteristic of a thing,</li>
 * <li>Usually immutable,</li>
 * <li>Examples: Address, Money, ...</li>
 * </ul>
 * Value object should:
 * <ul>
 * <li>have all attributes with final keyword</li>
 * <li>have a constructor with attributes</li>
 * <li>implements 'equal' methods. Value objects compare by the values of their
 * attributes, they don't have an identity.</li>
 * <li>implements 'hashCode' methods</li>
 * <li>implements 'toString' methods</li>
 * </ul>
 * 
 * 
 * DDD pattern:
 * 
 * A collection of objects that are bound together by an entity, otherwise known
 * as an aggregate. The aggregate guarantees the consistency of changes being
 * made within the aggregate by forbidding external objects from holding
 * references to its members.
 * 
 * Other reference:
 * 
 * @see DDD: {@link http://en.wikipedia.org/wiki/Domain-driven_design}
 * @see Nacked Object: {@link http://en.wikipedia.org/wiki/Naked_objects}
 * @see CQRS: {@link http://en.wikipedia.org/wiki/Command-query_separation}
 * 
 * 
 * 
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface Entity {

	/**
	 * @return {@link Entity}'s identity
	 */
	String getIdentity();
//
//	/**
//	 * Return typed property for specified name.
//	 * 
//	 * @param name
//	 *            property name
//	 * @param className
//	 *            class name of wished property
//	 * @return a instance of <code>T</code> or null if no property match
//	 *         specified name.
//	 * @throws NullPointerException
//	 *             if name or className is null
//	 * @throws IllegalArgumentException
//	 *             if name or className is empty
//	 * @throws ClassCastException
//	 *             if className did not match property type.
//	 */
//	<T> T getProperty(String name, Class<T> className) throws NullPointerException, IllegalArgumentException, ClassCastException;

	/**
	 * Return typed property for specified name.
	 * 
	 * @param name
	 *            property name
	 * @return a instance of <code>T</code> or null if no property match
	 *         specified name.
	 * @throws NullPointerException
	 *             if name is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 */
	<T> T getProperty(String name) throws NullPointerException, IllegalArgumentException;

	/**
	 * Set new value of specified property name.
	 * 
	 * @param name
	 *            property name
	 * @param value
	 *            value to set
	 * @throws NullPointerException
	 *             if name is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 * @throws ClassCastException
	 *             if value type did not match property type.
	 * @throws IllegalStateException
	 *             if property with specified name did not exists
	 */
	<T> void setProperty(String name, T value) throws NullPointerException, IllegalArgumentException, ClassCastException, IllegalStateException;

}
