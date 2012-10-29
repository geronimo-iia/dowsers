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
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.reference.References;

/**
 * InMemoryEntityStore implements a {@link EntityStore} in memory (only for
 * testing purpose no ?).
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class InMemoryEntityStore implements EntityStore {

	private final Map<Reference, Object> entities = Maps.newHashMap();

	/**
	 * Build a new instance of InMemoryEntityStore.java.
	 */
	public InMemoryEntityStore() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T find(final Class<T> expectedType, final Reference reference) throws EntityNotFoundException, NullPointerException, IllegalArgumentException {
		return (T) entities.get(reference);
	}

	@Override
	public <T> void store(final T entity) throws NullPointerException, ConcurrencyException, IllegalArgumentException {
		entities.put(References.identify(entity), entity);
	}

	@Override
	public <T> void remove(final T entity) throws NullPointerException, IllegalArgumentException {
		entities.remove(References.identify(entity));
	}

	@Override
	public void remove(final Reference reference) throws NullPointerException, IllegalArgumentException {
		entities.remove(reference);
	}

}
