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

import com.intelligentsia.dowsers.entity.store.ConcurrencyException;
import com.intelligentsia.dowsers.entity.store.EntityNotFoundException;

public class EntityManagerSupport implements EntityManager {

	@Override
	public <T> T newInstance(Class<T> expectedType) throws NullPointerException {
		return null;
	}

	@Override
	public <T> T find(Class<T> expectedType, String identity) throws EntityNotFoundException, NullPointerException {
		return null;
	}

	@Override
	public <T> void store(T entity) throws NullPointerException, ConcurrencyException {
	}

	@Override
	public <T> void remove(T entity) throws NullPointerException {
	}

}
