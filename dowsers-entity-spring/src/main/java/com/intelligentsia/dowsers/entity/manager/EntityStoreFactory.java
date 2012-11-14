package com.intelligentsia.dowsers.entity.manager;

import java.util.Map;

import org.springframework.beans.factory.FactoryBean;

import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.store.CachedEntityStore;
import com.intelligentsia.dowsers.entity.store.EntityStore;
import com.intelligentsia.dowsers.entity.store.MetaEntityStore;
import com.intelligentsia.dowsers.entity.store.ShardingEntityStore;

/**
 * MetaEntityStoreFactory implements {@link FactoryBean} for
 * {@link MetaEntityStore}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class EntityStoreFactory implements FactoryBean<EntityStore> {

	private Map<Reference, EntityStore> stores = null;

	private EntityStore entityStore;

	private boolean enableCachedEntities = Boolean.TRUE;

	public EntityStoreFactory() {
	}

	@Override
	public EntityStore getObject() throws Exception {
		final EntityStore store = enableCachedEntities ? new CachedEntityStore(entityStore) : entityStore;
		if (stores != null) {
			return ShardingEntityStore.builder().addAll(stores).build(store);
		}
		return store;
	}

	@Override
	public Class<?> getObjectType() {
		return EntityStore.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public Map<Reference, EntityStore> getStores() {
		return stores;
	}

	public void setStores(final Map<Reference, EntityStore> stores) {
		this.stores = stores;
	}

	public EntityStore getEntityStore() {
		return entityStore;
	}

	public void setEntityStore(final EntityStore entityStore) {
		this.entityStore = entityStore;
	}

	public boolean isEnableCachedEntities() {
		return enableCachedEntities;
	}

	public void setEnableCachedEntities(final boolean enableCachedEntities) {
		this.enableCachedEntities = enableCachedEntities;
	}

}
