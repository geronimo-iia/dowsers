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

import com.google.common.base.Preconditions;
import com.intelligentsia.dowsers.entity.meta.MetaEntityProvider;
import com.intelligentsia.dowsers.entity.meta.provider.MetaEntityProviders;

/**
 * MetaEntityProviderFactory implements {@link FactoryBean} of
 * {@link MetaEntityProvider}.
 * 
 * @author JGT
 * 
 */
public class MetaEntityProviderFactory implements FactoryBean<MetaEntityProvider> {

	private boolean enableDynamicAnalyzer = Boolean.TRUE;

	private MetaEntityProvider metaEntityProvider;

	public MetaEntityProviderFactory() {
		super();
	}

	@Override
	public MetaEntityProvider getObject() throws Exception {
		if (enableDynamicAnalyzer) {
			return metaEntityProvider != null ? MetaEntityProviders.newMetaEntityProvider(MetaEntityProviders.newMetaEntityProviderAnalyzer(), metaEntityProvider) : MetaEntityProviders.newMetaEntityProviderAnalyzer();
		}
		return Preconditions.checkNotNull(metaEntityProvider);
	}

	@Override
	public Class<?> getObjectType() {
		return MetaEntityProvider.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public boolean isEnableDynamicAnalyzer() {
		return enableDynamicAnalyzer;
	}

	public void setEnableDynamicAnalyzer(final boolean enableDynamicAnalyzer) {
		this.enableDynamicAnalyzer = enableDynamicAnalyzer;
	}

	public MetaEntityProvider getMetaEntityProvider() {
		return metaEntityProvider;
	}

	public void setMetaEntityProvider(final MetaEntityProvider metaEntityProvider) {
		this.metaEntityProvider = metaEntityProvider;
	}

}
