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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.Reference;

public class InProcessEntityStore implements EntityStore {

	private Map<URI, Entity> entities = Maps.newHashMap();

	public InProcessEntityStore() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity> T find(Class<T> expectedType, String identity) throws EntityNotFoundException, NullPointerException {
		try {
			return (T) entities.get(Reference.newReference(expectedType, "identity", identity));
		} catch (URISyntaxException e) {
			throw Throwables.propagate(e);
		}
	}

	@Override
	public <T extends Entity> void store(T entity) throws NullPointerException, ConcurrencyException {
		try {
			entities.put(Reference.newReference(entity), entity);
		} catch (URISyntaxException e) {
			throw Throwables.propagate(e);
		}
	}

	@Override
	public <T extends Entity> void remove(T entity) throws NullPointerException {
		try {
			entities.remove(Reference.newReference(entity));
		} catch (URISyntaxException e) {
			throw Throwables.propagate(e);
		}
	}

}
