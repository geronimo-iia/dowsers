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

import java.util.Map;

import com.google.common.collect.Maps;
import com.intelligentsia.dowsers.entity.Entity;

//TODO finalize
public class ShardingEntityStore extends EntityStoreDecorator {

	@SuppressWarnings("unused")
	private Map<String, EntityStore> stores = Maps.newHashMap();

	/**
	 * Build a new instance of ShardingEntityStore.java.
	 * 
	 * @param entityStore
	 * @throws NullPointerException
	 */
	public ShardingEntityStore(EntityStore entityStore) throws NullPointerException {
		super(entityStore);
	}

	@Override
	public <T extends Entity> T find(Class<T> expectedType, String identity) throws EntityNotFoundException, NullPointerException {
		return super.find(expectedType, identity);
	}

	@Override
	public <T extends Entity> void store(T entity) throws NullPointerException, ConcurrencyException {

		super.store(entity);
	}

	@Override
	public <T extends Entity> void remove(T entity) throws NullPointerException {
		super.remove(entity);
	}

}
