/**
 * 
 */
package com.intelligentsia.dowsers.entity.meta.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * MetaEntityProviderFilterLastVersion select last version of delegated
 * {@link MetaEntityProvider}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class MetaEntityProviderFilterLastVersion extends MetaEntityProviderDecorator {

	/**
	 * Build a new instance of MetaEntityProviderFilterLastVersion.java.
	 * 
	 * @param metaEntityProvider
	 * @throws NullPointerException
	 */
	public MetaEntityProviderFilterLastVersion(final MetaEntityProvider metaEntityProvider) throws NullPointerException {
		super(metaEntityProvider);
	}

	@Override
	public Collection<MetaEntity> find(final Reference reference) throws NullPointerException {
		// call super
		final LinkedList<MetaEntity> metaEntities = Lists.newLinkedList(super.find(reference));
		// take first
		if (metaEntities.isEmpty()) {
			return ImmutableSet.of();
		} else {
			// sort
			Collections.sort(metaEntities);
			// take first
			return ImmutableSet.of(metaEntities.getFirst());
		}
	}
}
