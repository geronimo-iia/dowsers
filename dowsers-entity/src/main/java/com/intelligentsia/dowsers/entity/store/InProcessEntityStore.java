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
import java.util.Map;

import com.google.common.collect.Maps;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.Reference;

/**
 * InProcessEntityStore implements a {@link EntityStore} in memory (only for
 * test so ??).
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class InProcessEntityStore implements EntityStore {

	private Map<URI, Entity> entities = Maps.newHashMap();

	/**
	 * Build a new instance of InProcessEntityStore.java.
	 */
	public InProcessEntityStore() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity> T find(Class<T> expectedType, String identity) throws EntityNotFoundException, NullPointerException {
		return (T) entities.get(Reference.newAttributeReference(expectedType, "identity", identity));
	}

	@Override
	public <T extends Entity> void store(T entity) throws NullPointerException, ConcurrencyException {
		entities.put(Reference.newEntityReference(entity), entity);
	}

	@Override
	public <T extends Entity> void remove(T entity) throws NullPointerException {
		entities.remove(Reference.newEntityReference(entity));
	}

}
