package com.intelligentsia.dowsers.entity.manager;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.FactoryBean;

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
public class MetaEntityContextProviderFactory implements FactoryBean<MetaEntityContextProvider> {

	private boolean enableCache = Boolean.TRUE;

	private MetaEntityProvider metaEntityProvider;

	private Map<Reference, MetaEntityContext> contextEntities;

	public MetaEntityContextProviderFactory() {
		super();
	}

	@Override
	public MetaEntityContextProvider getObject() throws Exception {
		final MetaEntityContextProviderSupport.Builder builder = MetaEntityContextProviderSupport.builder();
		if (contextEntities != null) {
			for (final Entry<Reference, MetaEntityContext> entry : contextEntities.entrySet()) {
				builder.add(entry.getKey(), entry.getValue());
			}
		}
		final MetaEntityContextProvider metaEntityContextProvider = builder.build(metaEntityProvider);
		return enableCache ? new MetaEntityContextProviderWithCache(metaEntityContextProvider) : metaEntityContextProvider;
	}

	@Override
	public Class<?> getObjectType() {
		return MetaEntityContextProvider.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
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

}
