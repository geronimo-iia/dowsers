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

import org.springframework.beans.factory.FactoryBean;

import com.intelligentsia.dowsers.entity.store.EntityStore;
import com.intelligentsia.dowsers.entity.store.MetaEntityStore;
import com.intelligentsia.dowsers.entity.store.MetaEntityStoreSupport;

/**
 * 
 * MetaEntityStoreFactory implements {@link FactoryBean} for
 * {@link MetaEntityStore}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class MetaEntityStoreFactory implements FactoryBean<MetaEntityStore> {

	private EntityStore entityStore;

	public MetaEntityStoreFactory() {
	}

	public MetaEntityStoreFactory(final EntityStore entityStore) {
		super();
		this.entityStore = entityStore;
	}

	@Override
	public MetaEntityStore getObject() throws Exception {
		if (entityStore == null) {
			throw new IllegalStateException("No entity store defined");
		}
		return new MetaEntityStoreSupport(entityStore);
	}

	@Override
	public Class<?> getObjectType() {
		return MetaEntityStore.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public EntityStore getEntityStore() {
		return entityStore;
	}

	public void setEntityStore(final EntityStore entityStore) {
		this.entityStore = entityStore;
	}

}
