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
package com.intelligentsia.dowsers.entity.manager;

import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.store.ConcurrencyException;
import com.intelligentsia.dowsers.entity.store.EntityNotFoundException;

/**
 * <code>EntityManager</code> declare methods to manage {@link Entity}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public interface EntityManager {
	/**
	 * Create a new entity instance
	 * 
	 * @param expectedType
	 *            expected type entity
	 * @return an entity instance of the expected type
	 * @throws NullPointerException
	 *             if expectedType is null
	 */
	public <T> T newInstance(Class<T> expectedType) throws NullPointerException;

	/**
	 * Find entity with the specified identity.
	 * 
	 * @param expectedType
	 *            expected type entity
	 * @param identity
	 *            identity what we looking for
	 * @return an entity instance of the expected type and identity
	 * 
	 * @throws EntityNotFoundException
	 *             if no entity with specified identity and type exists.
	 * @throws NullPointerException
	 *             if expectedType or identity is null
	 */
	public <T> T find(Class<T> expectedType, String identity) throws EntityNotFoundException, NullPointerException;

	/**
	 * Find entity with the specified reference.
	 * 
	 * @param reference
	 *            entity identifier
	 * @return an entity representation of the expected type and reference
	 * @throws EntityNotFoundException
	 * @throws NullPointerException
	 *             if reference is null
	 * @throws IllegalArgumentException
	 *             if reference is not an identifier
	 */
	public <T> T find(Reference reference) throws EntityNotFoundException, NullPointerException, IllegalArgumentException;

	/**
	 * Store specified entity.
	 * 
	 * @param entity
	 *            entity to store
	 * @throws NullPointerException
	 *             if entity is null
	 * @throws ConcurrencyException
	 *             if entity to store is old dated
	 */
	public <T> void store(T entity) throws NullPointerException, ConcurrencyException;

	/**
	 * Remove specified entity.
	 * 
	 * @param entity
	 * @throws NullPointerException
	 *             if entity is null
	 */
	public <T> void remove(T entity) throws NullPointerException;

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
