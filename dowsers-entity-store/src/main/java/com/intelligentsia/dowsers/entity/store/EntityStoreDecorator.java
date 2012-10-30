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
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * EntityStoreDecorator implements decorator pattern on {@link EntityStore}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityStoreDecorator implements EntityStore {

	protected final EntityStore entityStore;

	/**
	 * Build a new instance of <code>EntityStoreDecorator</code>.
	 * 
	 * @param entityStore
	 * @throws NullPointerException
	 *             if entityStore is null
	 */
	public EntityStoreDecorator(final EntityStore entityStore) throws NullPointerException {
		super();
		this.entityStore = Preconditions.checkNotNull(entityStore);
	}

	@Override
	public <T> T find(final Class<T> expectedType, final Reference reference) throws EntityNotFoundException, NullPointerException, IllegalArgumentException {
		return entityStore.find(expectedType, reference);
	}

	@Override
	public <T> void store(final T entity) throws NullPointerException, ConcurrencyException, IllegalArgumentException {
		entityStore.store(entity);
	}

	@Override
	public <T> void remove(final T entity) throws NullPointerException, IllegalArgumentException {
		entityStore.remove(entity);
	}

	@Override
	public void remove(final Reference reference) throws NullPointerException, IllegalArgumentException {
		entityStore.remove(reference);
	}

}
