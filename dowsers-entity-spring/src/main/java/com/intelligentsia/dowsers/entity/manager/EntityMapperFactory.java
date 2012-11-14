package com.intelligentsia.dowsers.entity.manager;

import org.springframework.beans.factory.FactoryBean;

import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProvider;
import com.intelligentsia.dowsers.entity.serializer.EntityMapper;

/**
 * EntityMapperFactory implements {@link FactoryBean} for {@link EntityMapper}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class EntityMapperFactory implements FactoryBean<EntityMapper> {

	private MetaEntityContextProvider metaEntityContextProvider;

	public EntityMapperFactory() {
		super();
	}

	@Override
	public EntityMapper getObject() throws Exception {
		return new EntityMapper(metaEntityContextProvider);
	}

	@Override
	public Class<?> getObjectType() {
		return EntityMapper.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public MetaEntityContextProvider getMetaEntityContextProvider() {
		return metaEntityContextProvider;
	}

	public void setMetaEntityContextProvider(final MetaEntityContextProvider metaEntityContextProvider) {
		this.metaEntityContextProvider = metaEntityContextProvider;
	}

}
