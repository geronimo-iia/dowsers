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
package com.intelligentsia.dowsers.entity.store;

import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * EntityStore declare methods to store Entity representation.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface EntityStore {

	/**
	 * Find {@link Reference} entity with the specified type.
	 * 
	 * @param expectedType
	 * @return an {@link Iterable} {@link Reference}.
	 * @throws NullPointerException
	 *             if expectedType is null
	 */
	public Iterable<Reference> find(Class<?> expectedType) throws NullPointerException;

	/**
	 * Find entities with the specified attribute value.
	 * 
	 * Reference contains
	 * <ul>
	 * <li>entity class name that we looking for</li>
	 * <li>attribute name to filter on</li>
	 * <li>value</li>
	 * </ul>
	 * This look for all entity of type {@link Reference#getEntityClassName()}
	 * with attribute name {@link Reference#getAttributeName()} equals to
	 * {@link Reference#getIdentity()}.
	 * 
	 * @param expectedType
	 *            expected type entity
	 * @param reference
	 *            reference what we looking for
	 * 
	 * @return an entity instance of the expected type and identity
	 * 
	 * @throws EntityNotFoundException
	 *             if no entity with specified identity and type exists.
	 * @throws NullPointerException
	 *             if expectedType or identity is null
	 * @throws IllegalArgumentException
	 *             if reference is not an identifier
	 */
	public Iterable<Reference> find(Reference reference) throws NullPointerException;

	/**
	 * Find entity with the specified identity.
	 * 
	 * @param expectedType
	 *            expected type entity
	 * @param reference
	 *            reference what we looking for.
	 * @return an entity instance of the expected type and identity
	 * 
	 * @throws EntityNotFoundException
	 *             if no entity with specified identity and type exists.
	 * @throws NullPointerException
	 *             if expectedType or identity is null
	 * @throws IllegalArgumentException
	 *             if reference is not an identifier
	 */
	public <T> T find(Class<T> expectedType, Reference reference) throws EntityNotFoundException, NullPointerException, IllegalArgumentException;

	/**
	 * Store specified entity.
	 * 
	 * @param entity
	 *            entity to store
	 * @throws NullPointerException
	 *             if entity is null
	 * @throws ConcurrencyException
	 *             if entity to store is old dated
	 * @throws IllegalArgumentException
	 *             if entity is not an {@link Entity} representation
	 */
	public <T> void store(T entity) throws NullPointerException, ConcurrencyException, IllegalArgumentException;

	/**
	 * Remove specified entity.
	 * 
	 * @param entity
	 * @throws NullPointerException
	 *             if entity is null
	 * @throws IllegalArgumentException
	 *             if entity is not an {@link Entity} representation
	 */
	public <T> void remove(T entity) throws NullPointerException, IllegalArgumentException;

	/**
	 * Remove specified referenced entity.
	 * 
	 * @param reference
	 *            entity reference
	 * @throws NullPointerException
	 *             if reference is null
	 * @throws IllegalArgumentException
	 *             if reference is not an identifier
	 */
	public void remove(Reference reference) throws NullPointerException, IllegalArgumentException;

}
