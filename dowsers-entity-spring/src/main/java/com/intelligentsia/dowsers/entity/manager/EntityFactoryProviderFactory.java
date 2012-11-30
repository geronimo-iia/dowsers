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

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;

import com.intelligentsia.dowsers.entity.EntityFactories;
import com.intelligentsia.dowsers.entity.EntityFactoryProvider;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProvider;

/**
 * EntityFactoryProviderFactory implements {@link FactoryBean} of
 * {@link EntityFactoryProvider}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class EntityFactoryProviderFactory implements FactoryBean<EntityFactoryProvider>, BeanFactoryAware {

	private boolean enableDefaultFactory = Boolean.TRUE;

	private MetaEntityContextProvider metaEntityContextProvider;

	private Map<Class<?>, EntityFactories.EntityFactory<?>> factories;

	private BeanFactory beanFactory;

	private EntityFactoryProvider entityFactoryProvider = null;

	/**
	 * Build a new instance of EntityFactoryProviderFactory.java.
	 */
	public EntityFactoryProviderFactory() {
		super();
	}

	@Override
	public EntityFactoryProvider getObject() throws Exception {
		if (entityFactoryProvider == null) {
			if (metaEntityContextProvider == null) {
				metaEntityContextProvider = beanFactory.getBean(MetaEntityContextProvider.class);
			}
			entityFactoryProvider = EntityFactoryProvider.builder().setFactories(factories).setEnableDefaultFactory(enableDefaultFactory).build(metaEntityContextProvider);
		}
		return entityFactoryProvider;
	}

	@Override
	public Class<?> getObjectType() {
		return EntityFactoryProvider.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public boolean isEnableDefaultFactory() {
		return enableDefaultFactory;
	}

	public void setEnableDefaultFactory(final boolean enableDefaultFactory) {
		this.enableDefaultFactory = enableDefaultFactory;
	}

	public MetaEntityContextProvider getMetaEntityContextProvider() {
		return metaEntityContextProvider;
	}

	public void setMetaEntityContextProvider(final MetaEntityContextProvider metaEntityContextProvider) {
		this.metaEntityContextProvider = metaEntityContextProvider;
	}

	public Map<Class<?>, EntityFactories.EntityFactory<?>> getFactories() {
		return factories;
	}

	public void setFactories(final Map<Class<?>, EntityFactories.EntityFactory<?>> factories) {
		this.factories = factories;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
}
