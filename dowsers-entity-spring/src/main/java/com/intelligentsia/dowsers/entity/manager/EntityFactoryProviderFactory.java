package com.intelligentsia.dowsers.entity.manager;

import java.util.Map;

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
public class EntityFactoryProviderFactory implements FactoryBean<EntityFactoryProvider> {

	private boolean enableDefaultFactory = Boolean.TRUE;

	private MetaEntityContextProvider metaEntityContextProvider;

	private Map<Class<?>, EntityFactories.EntityFactory<?>> factories;

	/**
	 * Build a new instance of EntityFactoryProviderFactory.java.
	 */
	public EntityFactoryProviderFactory() {
		super();
	}

	@Override
	public EntityFactoryProvider getObject() throws Exception {
		return EntityFactoryProvider.builder().setFactories(factories).setEnableDefaultFactory(enableDefaultFactory).build(metaEntityContextProvider);
	}

	@Override
	public Class<?> getObjectType() {
		return EntityFactoryProvider.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
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

}
