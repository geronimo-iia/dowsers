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

import org.intelligentsia.dowsers.core.reflection.ClassInformation;

import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.store.ConcurrencyException;
import com.intelligentsia.dowsers.entity.store.EntityNotFoundException;
import com.intelligentsia.dowsers.entity.store.EntityStore;

/**
 * EntityManagerSupport implements {@link EntityManager}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class EntityManagerSupport implements EntityManager {
	/**
	 * {@link EntityFactoryProvider} instance.
	 */
	private EntityFactoryProvider entityFactoryProvider;
	/**
	 * {@link EntityStore} instance.
	 */
	private EntityStore entityStore;


	@Override
	public <T> T newInstance(final Class<T> expectedType) throws NullPointerException {
		return entityFactoryProvider.newInstance(expectedType);
	}

	@Override
	public <T> T find(final Class<T> expectedType, final Reference reference) throws EntityNotFoundException, NullPointerException {
		return entityStore.find(expectedType, reference);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T find(final Reference reference) throws EntityNotFoundException, NullPointerException, IllegalArgumentException {
		return (T) entityStore.find(ClassInformation.parse(reference.getEntityClassName()).getType(), reference);
	}

	@Override
	public <T> void store(final T entity) throws NullPointerException, ConcurrencyException {
		entityStore.store(entity);
	}

	@Override
	public <T> void remove(final T entity) throws NullPointerException {
		entityStore.remove(entity);
	}

	@Override
	public void remove(final Reference reference) throws NullPointerException, IllegalArgumentException {
		entityStore.remove(reference);
	}

}
