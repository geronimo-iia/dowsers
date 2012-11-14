package com.intelligentsia.dowsers.entity.manager;

import org.springframework.beans.factory.FactoryBean;

import com.intelligentsia.dowsers.entity.EntityFactoryProvider;

/**
 * EntityFactoryProviderFactory implements {@link FactoryBean} of
 * {@link EntityFactoryProvider}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class EntityFactoryProviderFactory implements FactoryBean<EntityFactoryProvider> {

	/**
	 * Build a new instance of EntityFactoryProviderFactory.java.
	 */
	public EntityFactoryProviderFactory() {
		super();
	}

	@Override
	public EntityFactoryProvider getObject() throws Exception {
		return null;
	}

	@Override
	public Class<?> getObjectType() {
		return EntityFactoryProvider.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
