package com.intelligentsia.dowsers.entity.meta.provider;

import java.util.Collection;

import com.google.common.base.Preconditions;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * MetaEntityProviderDecorator implements decorator pattern on
 * {@link MetaEntityProvider}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
abstract class MetaEntityProviderDecorator implements MetaEntityProvider {

	protected final MetaEntityProvider metaEntityProvider;

	public MetaEntityProviderDecorator(final MetaEntityProvider metaEntityProvider) throws NullPointerException {
		super();
		this.metaEntityProvider = Preconditions.checkNotNull(metaEntityProvider);
	}

	@Override
	public Collection<MetaEntity> find(final Reference reference) throws NullPointerException {
		return metaEntityProvider.find(reference);
	}

}
