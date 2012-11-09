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

import java.util.Collection;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * MetaEntityStoreSupport.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class MetaEntityStoreSupport implements MetaEntityStore {

	/**
	 * Underlying {@link EntityStore}.
	 */
	private EntityStore entityStore;

	@Override
	public Collection<MetaEntity> find(Reference reference) throws NullPointerException {
		final Collection<MetaEntity> result = Sets.newLinkedHashSet();
		try {
			result.add(entityStore.find(MetaEntity.class, Preconditions.checkNotNull(reference)));
		} catch (EntityNotFoundException entityNotFoundException) {
			// oups
		}
		return result;
	}

	@Override
	public void store(MetaEntity entity) throws NullPointerException, ConcurrencyException, IllegalArgumentException {
		entityStore.store(entity);
	}

	@Override
	public void remove(MetaEntity entity) throws NullPointerException, IllegalArgumentException {
		entityStore.remove(entity);
	}

	@Override
	public void remove(Reference reference) throws NullPointerException, IllegalArgumentException {
		Preconditions.checkArgument(MetaEntity.class.getSimpleName().equals(Preconditions.checkNotNull(reference).getEntityClassName()));
		entityStore.remove(reference);
	}

}
