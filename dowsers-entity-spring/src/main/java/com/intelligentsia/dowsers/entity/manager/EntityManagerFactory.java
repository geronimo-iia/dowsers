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

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.intelligentsia.dowsers.entity.EntityFactoryProvider;
import com.intelligentsia.dowsers.entity.serializer.EntityMapper;
import com.intelligentsia.dowsers.entity.store.EntityStore;

/**
 * EntityManagerFactory implement a {@link FactoryBean} for
 * {@link EntityManager}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class EntityManagerFactory implements FactoryBean<EntityManager>, BeanFactoryAware {

	private EntityManager.Listener entityManagerListener = null;

	private EntityStore entityStore;

	private EntityFactoryProvider entityFactoryProvider;

	private BeanFactory beanFactory;

	private EntityMapper entityMapper;

	private EntityManager entityManager = null;

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
		return true;
	}

	@Override
	public EntityManager getObject() throws Exception {
		if (entityManager == null) {
			if ((entityFactoryProvider == null) && (beanFactory != null)) {
				entityFactoryProvider = beanFactory.getBean(EntityFactoryProvider.class);
			}
			if ((entityStore == null) && (beanFactory != null)) {
				entityStore = beanFactory.getBean(EntityStore.class);
			}
			if ((entityManagerListener == null) && (beanFactory != null)) {
				try {
					entityManagerListener = beanFactory.getBean(EntityManager.Listener.class);
				} catch (final NoSuchBeanDefinitionException exception) {
					// do nothing
				}
			}
			if ((entityMapper == null) && (beanFactory != null)) {
				try {
					entityMapper = beanFactory.getBean(EntityMapper.class);
				} catch (final NoSuchBeanDefinitionException exception) {
					// do nothing
				}
			}
			entityManager = new EntityManagerSupport(entityFactoryProvider, entityStore, entityMapper, entityManagerListener);
		}
		return entityManager;
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

	public EntityMapper getEntityMapper() {
		return entityMapper;
	}

	public void setEntityMapper(final EntityMapper entityMapper) {
		this.entityMapper = entityMapper;
	}

	@Override
	public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
}
