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

import com.google.common.base.Preconditions;
import com.intelligentsia.dowsers.entity.EntityFactoryProvider;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.reference.References;
import com.intelligentsia.dowsers.entity.serializer.EntityMapper;
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
	private final EntityFactoryProvider entityFactoryProvider;
	/**
	 * {@link EntityStore} instance.
	 */
	private final EntityStore entityStore;
	/**
	 * {@link Listener} instance.
	 */
	private Listener listener;
	/**
	 * {@link EntityMapper} instance;
	 */
	private final EntityMapper entityMapper;

	/**
	 * Build a new instance of EntityManagerSupport.java.
	 * 
	 * @param entityFactoryProvider
	 * @param entityStore
	 * @param entityMapper
	 * @throws NullPointerException
	 *             if entityFactoryProvider or entityStore is null
	 */
	public EntityManagerSupport(final EntityFactoryProvider entityFactoryProvider, final EntityStore entityStore, final EntityMapper entityMapper) throws NullPointerException {
		this(entityFactoryProvider, entityStore, entityMapper, null);
	}

	/**
	 * 
	 * Build a new instance of EntityManagerSupport.java.
	 * 
	 * @param entityFactoryProvider
	 * @param entityStore
	 * @param entityMapper
	 * @param listener
	 * @throws NullPointerException
	 *             if entityFactoryProvider or entityStore is null
	 */
	public EntityManagerSupport(final EntityFactoryProvider entityFactoryProvider, final EntityStore entityStore, final EntityMapper entityMapper, final Listener listener) throws NullPointerException {
		super();
		this.entityFactoryProvider = Preconditions.checkNotNull(entityFactoryProvider);
		this.entityStore = Preconditions.checkNotNull(entityStore);
		this.listener = listener;
		this.entityMapper = entityMapper;
	}

	@Override
	public <T> T newInstance(final Class<T> expectedType) throws NullPointerException {
		final T entity = entityFactoryProvider.newInstance(expectedType).newInstance();
		if (listener != null) {
			listener.entityInstantiated(entity);
		}
		return entity;
	}

	@Override
	public <T> T newInstance(final Class<T> expectedType, final Reference reference) throws NullPointerException, IllegalArgumentException {
		Preconditions.checkArgument(Reference.newReferenceOnEntityClass(expectedType).equals(Preconditions.checkNotNull(reference).getEntityClassReference()));
		final T entity = entityFactoryProvider.newInstance(expectedType).newInstance(reference);
		if (listener != null) {
			listener.entityInstantiated(entity);
		}
		return entity;
	}

	/**
	 * Find {@link Reference} entity with the specified type.
	 * 
	 * @param expectedType
	 * @return an {@link Iterable} {@link Reference}.
	 * @throws NullPointerException
	 *             if expectedType is null
	 */
	@Override
	public Iterable<Reference> find(final Class<?> expectedType) throws NullPointerException {
		return entityStore.find(expectedType);
	}

	@Override
	public <T> T find(final Class<T> expectedType, final Reference reference) throws EntityNotFoundException, NullPointerException {
		return notifyFind(entityStore.find(expectedType, reference));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T find(final Reference reference) throws EntityNotFoundException, NullPointerException, IllegalArgumentException {
		return notifyFind((T) entityStore.find(ClassInformation.parse(reference.getEntityClassName()).getType(), reference));
	}

	@Override
	public <T> void store(final T entity) throws NullPointerException, ConcurrencyException {
		entityStore.store(entity);
		if (listener != null) {
			listener.entityStored(entity);
		}
	}

	@Override
	public <T> void remove(final T entity) throws NullPointerException {
		entityStore.remove(entity);
		notifyRemove(References.identify(entity));
	}

	@Override
	public void remove(final Reference reference) throws NullPointerException, IllegalArgumentException {
		entityStore.remove(reference);
		notifyRemove(reference);
	}

	@Override
	public void addListener(final Listener listener) {
		this.listener = listener;
	}

	@Override
	public void removeListener(final Listener listener) {
		this.listener = null;
	}

	@Override
	public EntityMapper getEntityMapper() {
		return entityMapper;
	}

	private <T> T notifyFind(final T entity) {
		if (listener != null) {
			listener.entityFinded(entity);
		}
		return entity;
	}

	private void notifyRemove(final Reference reference) {
		if (listener != null) {
			listener.entityRemoved(reference);
		}
	}

}
