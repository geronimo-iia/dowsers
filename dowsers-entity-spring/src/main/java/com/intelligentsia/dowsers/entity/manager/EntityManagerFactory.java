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

import com.intelligentsia.dowsers.entity.EntityFactoryProvider;
import com.intelligentsia.dowsers.entity.store.EntityStore;

/**
 * EntityManagerFactory implement a {@link FactoryBean} for
 * {@link EntityManager}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class EntityManagerFactory implements FactoryBean<EntityManager> {

	private EntityManager.Listener entityManagerListener = null;

	private EntityStore entityStore;

	private EntityFactoryProvider entityFactoryProvider;

	/**
	 * Build a new instance of EntityManagerFactory.
	 */
	public EntityManagerFactory() {
		super();
	}

	@Override
	public Class<EntityManager> getObjectType() {
		return EntityManager.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	@Override
	public EntityManager getObject() throws Exception {
		return new EntityManagerSupport(entityFactoryProvider, entityStore, entityManagerListener);
	}

	public EntityManager.Listener getEntityManagerListener() {
		return entityManagerListener;
	}

	public void setEntityManagerListener(final EntityManager.Listener entityManagerListener) {
		this.entityManagerListener = entityManagerListener;
	}

	public EntityStore getEntityStore() {
		return entityStore;
	}

	public void setEntityStore(final EntityStore entityStore) {
		this.entityStore = entityStore;
	}

	public EntityFactoryProvider getEntityFactoryProvider() {
		return entityFactoryProvider;
	}

	public void setEntityFactoryProvider(final EntityFactoryProvider entityFactoryProvider) {
		this.entityFactoryProvider = entityFactoryProvider;
	}

}
