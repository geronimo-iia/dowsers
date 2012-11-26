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
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaEntityProvider;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * MetaEntityStore declare methods to store {@link MetaEntity} representation.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public interface MetaEntityStore extends MetaEntityProvider {

	/**
	 * Store specified MetaEntity.
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
	public void store(MetaEntity entity) throws NullPointerException, ConcurrencyException, IllegalArgumentException;

	/**
	 * Remove specified entity.
	 * 
	 * @param entity
	 * @throws NullPointerException
	 *             if entity is null
	 * @throws IllegalArgumentException
	 *             if entity is not an {@link Entity} representation
	 */
	public void remove(MetaEntity entity) throws NullPointerException, IllegalArgumentException;

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
