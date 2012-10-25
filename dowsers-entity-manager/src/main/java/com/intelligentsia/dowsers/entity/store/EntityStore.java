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

/**
 * EntityStore declare methods to store Entity.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface EntityStore {

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
	public <T extends Entity> T find(Class<T> expectedType, String identity) throws EntityNotFoundException, NullPointerException;

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
	public <T extends Entity> void store(T entity) throws NullPointerException, ConcurrencyException;

	/**
	 * Remove specified entity.
	 * 
	 * @param entity
	 * @throws NullPointerException
	 *             if entity is null
	 */
	public <T extends Entity> void remove(T entity) throws NullPointerException;

}
