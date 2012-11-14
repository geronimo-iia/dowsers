package com.intelligentsia.dowsers.entity.manager;

import org.springframework.beans.factory.FactoryBean;

import com.intelligentsia.dowsers.entity.store.EntityStore;
import com.intelligentsia.dowsers.entity.store.MetaEntityStore;
import com.intelligentsia.dowsers.entity.store.MetaEntityStoreSupport;

/**
 * 
 * MetaEntityStoreFactory implements {@link FactoryBean} for
 * {@link MetaEntityStore}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class MetaEntityStoreFactory implements FactoryBean<MetaEntityStore> {

	private EntityStore entityStore;

	public MetaEntityStoreFactory() {
	}

	public MetaEntityStoreFactory(final EntityStore entityStore) {
		super();
		this.entityStore = entityStore;
	}

	@Override
	public MetaEntityStore getObject() throws Exception {
		return new MetaEntityStoreSupport(entityStore);
	}

	@Override
	public Class<?> getObjectType() {
		return MetaEntityStore.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public EntityStore getEntityStore() {
		return entityStore;
	}

	public void setEntityStore(final EntityStore entityStore) {
		this.entityStore = entityStore;
	}

}
