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

import com.google.common.base.Preconditions;
import com.intelligentsia.dowsers.entity.Entity;

/**
 * EntityStoreDecorator implements decorator pattern on {@link EntityStore}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityStoreDecorator implements EntityStore {
	protected final EntityStore entityStore;

	public EntityStoreDecorator(EntityStore entityStore) throws NullPointerException {
		super();
		this.entityStore = Preconditions.checkNotNull(entityStore);
	}

	public <T extends Entity> T find(Class<T> expectedType, String identity) throws EntityNotFoundException, NullPointerException {
		return entityStore.find(expectedType, identity);
	}

	public <T extends Entity> void store(T entity) throws NullPointerException, ConcurrencyException {
		entityStore.store(entity);
	}

	public <T extends Entity> void remove(T entity) throws NullPointerException {
		entityStore.remove(entity);
	}

}
