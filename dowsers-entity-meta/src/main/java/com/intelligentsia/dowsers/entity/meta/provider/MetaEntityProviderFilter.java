package com.intelligentsia.dowsers.entity.meta.provider;

import java.util.Collection;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * MetaEntityProviderFilter apply a {@link Predicate} onto delegated result.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class MetaEntityProviderFilter extends MetaEntityProviderDecorator {

	private final Predicate<MetaEntity> predicate;

	/**
	 * Build a new instance of MetaEntityProviderFilter.java.
	 * 
	 * @param metaEntityProvider
	 * @param predicate
	 * @throws NullPointerException
	 *             if one of parameters is null
	 */
	public MetaEntityProviderFilter(final MetaEntityProvider metaEntityProvider, final Predicate<MetaEntity> predicate) throws NullPointerException {
		super(metaEntityProvider);
		this.predicate = Preconditions.checkNotNull(predicate);
	}

	@Override
	public Collection<MetaEntity> find(final Reference reference) throws NullPointerException {
		return Collections2.filter(metaEntityProvider.find(reference), predicate);
	}

}
