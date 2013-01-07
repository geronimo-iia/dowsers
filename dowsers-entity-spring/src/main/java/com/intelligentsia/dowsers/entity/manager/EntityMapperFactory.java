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

import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProvider;
import com.intelligentsia.dowsers.entity.serializer.EntityMapper;

/**
 * EntityMapperFactory implements {@link FactoryBean} for {@link EntityMapper}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class EntityMapperFactory implements FactoryBean<EntityMapper>, BeanFactoryAware {

	private MetaEntityContextProvider metaEntityContextProvider;

	private BeanFactory beanFactory;

	private EntityMapper entityMapper = null;

	public EntityMapperFactory() {
		super();
	}

	@Override
	public EntityMapper getObject() throws Exception {
		if (entityMapper == null) {
			if (metaEntityContextProvider == null && beanFactory != null) {
				metaEntityContextProvider = beanFactory.getBean(MetaEntityContextProvider.class);
			}
			entityMapper = new EntityMapper(metaEntityContextProvider);
		}
		return entityMapper;
	}

	@Override
	public Class<?> getObjectType() {
		return EntityMapper.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public MetaEntityContextProvider getMetaEntityContextProvider() {
		return metaEntityContextProvider;
	}

	public void setMetaEntityContextProvider(final MetaEntityContextProvider metaEntityContextProvider) {
		this.metaEntityContextProvider = metaEntityContextProvider;
	}

	@Override
	public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
}
