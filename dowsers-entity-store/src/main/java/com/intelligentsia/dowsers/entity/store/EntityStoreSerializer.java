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

import java.io.Reader;

import com.google.common.base.Preconditions;
import com.google.common.io.Closeables;
import com.intelligentsia.dowsers.entity.serializer.EntityMapper;

public abstract class EntityStoreSerializer implements EntityStore {
	/**
	 * {@link EntityMapper} instance.
	 */
	protected final EntityMapper entityMapper;

	public EntityStoreSerializer(final EntityMapper entityMapper) throws NullPointerException {
		super();
		this.entityMapper = Preconditions.checkNotNull(entityMapper);
	}

	public <T> T readValue(final Class<T> expectedType, final Reader reader) {
		try {
			return entityMapper.readValue(reader, expectedType);
		} finally {
			if (reader != null) {
				Closeables.closeQuietly(reader);
			}
		}
	}
}