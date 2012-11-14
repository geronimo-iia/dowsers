package com.intelligentsia.dowsers.entity.manager;

import org.springframework.beans.factory.FactoryBean;

import com.intelligentsia.dowsers.entity.store.EntityStore;

/**
 * EntityStoreFactory implements {@link FactoryBean} of {@link EntityStore}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class EntityStoreFactory implements FactoryBean<EntityStore> {

	/**
	 * Build a new instance of EntityStoreFactory.
	 */
	public EntityStoreFactory() {
		super();
	}

	@Override
	public EntityStore getObject() throws Exception {
		return null;
	}

	@Override
	public Class<?> getObjectType() {
		return EntityStore.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
