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
import java.util.Map.Entry;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.google.common.collect.Maps;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProvider;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProviderSupport;
import com.intelligentsia.dowsers.entity.meta.MetaEntityProvider;
import com.intelligentsia.dowsers.entity.meta.provider.MetaEntityContextProviderWithCache;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * MetaEntityContextProviderFactory implements a {@link FactoryBean} of
 * {@link MetaEntityContextProvider}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class MetaEntityContextProviderFactory implements FactoryBean<MetaEntityContextProvider>, BeanFactoryAware, InitializingBean {

	private boolean enableCache = Boolean.TRUE;

	private MetaEntityProvider metaEntityProvider = null;

	private String metaEntityProviderName = null;

	private Map<Reference, MetaEntityContext> contextEntities = Maps.newLinkedHashMap();

	private BeanFactory beanFactory;

	private MetaEntityContextProvider metaEntityContextProvider = null;

	private MetaEntityContextProviderLazyInitialization contextProviderLazyInitialization = null;

	public MetaEntityContextProviderFactory() {
		super();
	}

	@Override
	public MetaEntityContextProvider getObject() throws Exception {
		if (metaEntityContextProvider == null) {
			final MetaEntityContextProviderSupport.Builder builder = MetaEntityContextProviderSupport.builder();
			// try to find metaEntityProvider 
			if (findMetaEntityProvider() == null) {
				contextProviderLazyInitialization = new MetaEntityContextProviderLazyInitialization(contextEntities);
				metaEntityContextProvider = contextProviderLazyInitialization;
				// afterPropertiesSet will do the job
			} else {
				// build metaEntityContextProvider in one call
				for (final Entry<Reference, MetaEntityContext> entry : contextEntities.entrySet()) {
					builder.add(entry.getKey(), entry.getValue());
				}
				metaEntityContextProvider = builder.build(metaEntityProvider);
			}
			metaEntityContextProvider = enableCache ? new MetaEntityContextProviderWithCache(metaEntityContextProvider) : metaEntityContextProvider;
		}
		return metaEntityContextProvider;
	}

	@Override
	public Class<?> getObjectType() {
		return MetaEntityContextProvider.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public boolean isEnableCache() {
		return enableCache;
	}

	public void setEnableCache(final boolean enableCache) {
		this.enableCache = enableCache;
	}

	public MetaEntityProvider getMetaEntityProvider() {
		return metaEntityProvider;
	}

	public void setMetaEntityProvider(final MetaEntityProvider metaEntityProvider) {
		this.metaEntityProvider = metaEntityProvider;
	}

	public Map<Reference, MetaEntityContext> getContextEntities() {
		return contextEntities;
	}

	public void setContextEntities(final Map<Reference, MetaEntityContext> contextEntities) {
		this.contextEntities = contextEntities;
	}

	@Override
	public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public String getMetaEntityProviderName() {
		return metaEntityProviderName;
	}

	public void setMetaEntityProviderName(String metaEntityProviderName) {
		this.metaEntityProviderName = metaEntityProviderName;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (contextProviderLazyInitialization != null) {
			contextProviderLazyInitialization.setMetaEntityProvider(findMetaEntityProvider());
		}
	}

	/**
	 * @throws BeansException
	 */
	protected MetaEntityProvider findMetaEntityProvider() throws BeansException {
		if (metaEntityProvider == null && beanFactory != null) {
			try {
				metaEntityProvider = beanFactory.getBean(MetaEntityProvider.class);
			} catch (NoSuchBeanDefinitionException beanDefinitionException) {
				if (metaEntityProviderName != null) {
					metaEntityProvider = beanFactory.getBean(metaEntityProviderName, MetaEntityProvider.class);
				}
			}
		}
		return metaEntityProvider;
	}
}
