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
