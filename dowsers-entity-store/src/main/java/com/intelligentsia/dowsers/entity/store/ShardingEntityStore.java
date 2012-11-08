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

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.reference.References;

/**
 * ShardingEntityStore implements a sharding {@link EntityStore}.
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class ShardingEntityStore implements EntityStore {
	/**
	 * {@link Map} of URI and {@link EntityStore}.
	 */
	private final Map<Reference, EntityStore> stores;
	/**
	 * Default {@link EntityStore}.
	 */
	protected final EntityStore defaultEntityStore;

	/**
	 * Build a new instance of ShardingEntityStore.java.
	 * 
	 * @param defaultEntityStore
	 *            default {@link EntityStore}
	 * @param stores
	 *            a Map of {@link Reference}, {@link EntityStore}
	 * @throws NullPointerException
	 *             if one of parameters is null
	 */
	public ShardingEntityStore(final EntityStore defaultEntityStore, final Map<Reference, EntityStore> stores) throws NullPointerException {
		super();
		this.defaultEntityStore = Preconditions.checkNotNull(defaultEntityStore);
		this.stores = Preconditions.checkNotNull(stores);
	}

	@Override
	public <T> T find(final Class<T> expectedType, final Reference reference) throws EntityNotFoundException, NullPointerException, IllegalArgumentException {
		return find(reference).find(expectedType, reference);
	}

	@Override
	public <T> void store(final T entity) throws NullPointerException, ConcurrencyException, IllegalArgumentException {
		find(References.identify(entity)).store(entity);
	}

	@Override
	public <T> void remove(final T entity) throws NullPointerException, IllegalArgumentException {
		find(References.identify(entity)).remove(entity);
	}

	@Override
	public void remove(final Reference reference) throws NullPointerException, IllegalArgumentException {
		find(reference).remove(reference);
	}

	/**
	 * Find {@link EntityStore} instance of use with this reference
	 * 
	 * @param reference
	 * @return {@link EntityStore} instance.
	 */
	public EntityStore find(final Reference reference) {
		EntityStore result = stores.get(reference);
		if (result == null) {
			result = stores.get(reference.getEntityClassReference());
			if (result == null) {
				result = defaultEntityStore;
			}
		}
		return result;
	}

	/**
	 * @return a {@link Builder} instance of {@link ShardingEntityStore}.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder of {@link ShardingEntityStore} .
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	public static class Builder {
		private final Map<Reference, EntityStore> stores = Maps.newHashMap();

		/**
		 * Build a new instance of ShardingEntityStore.java.
		 */
		public Builder() {
			super();
		}

		/**
		 * Add a specific {@link EntityStore} for specified {@link Class}
		 * 
		 * @param clazz
		 *            specified class name
		 * @param entityStore
		 *            {@link EntityStore} to add for specified class
		 * @return this instance
		 * @throws NullPointerException
		 *             if one of parameters is null
		 */
		public Builder add(final Reference reference, final EntityStore entityStore) throws NullPointerException {
			stores.put(reference, Preconditions.checkNotNull(entityStore));
			return this;
		}

		/**
		 * Build a new instance of {@link ShardingEntityStore}.
		 * 
		 * @param defaultEntityStore
		 *            default {@link EntityStore}
		 * @return a new instance of {@link ShardingEntityStore}.
		 * @throws NullPointerException
		 *             if defaultEntityStoreis nul
		 */
		public ShardingEntityStore build(final EntityStore defaultEntityStore) throws NullPointerException {
			return new ShardingEntityStore(Preconditions.checkNotNull(defaultEntityStore), stores);
		}
	}
}
