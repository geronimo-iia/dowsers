package com.intelligentsia.dowsers.entity.meta.provider;

import java.io.File;
import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.serializer.EntityMapper;

/**
 * MetaEntityProviders expose utilities to instanciate
 * {@link MetaEntityProvider}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public enum MetaEntityProviders {
	;

	/**
	 * Build a {@link MetaEntityProvider} which aggregate :
	 * <ul>
	 * <li>{@link MetaEntityProviderAnalyzer}</li>
	 * <li>for each directory under root, add a
	 * {@link MetaEntityProviderFileSystem} which select last version</li>
	 * </ul>
	 * 
	 * This permit to define meta entity from class analyze and add several
	 * extension with version management.
	 * 
	 * @param reference
	 *            {@link Reference}
	 * @param root
	 *            Root {@link File}
	 * @param entityMapper
	 * @return {@link MetaEntityProvider} instance
	 */
	public static MetaEntityProvider newMetaEntityProvider(final Reference reference, File root, EntityMapper entityMapper) {
		// for each directory under root, build a file meta entity provider and
		// select last version under each
		final Collection<MetaEntityProvider> metaEntityProviders = Lists.newArrayList();
		metaEntityProviders.add(newMetaEntityProviderAnalyzer());
		for (File file : root.listFiles()) {
			if (file.isDirectory()) {
				metaEntityProviders.add(selectLastVersion(newMetaEntityProvider(file, entityMapper)));
			}
		}
		return newMetaEntityProvider(metaEntityProviders);
	}

	public static MetaEntityProvider newMetaEntityProviderAnalyzer() {
		return new MetaEntityProviderAnalyzer();
	}

	public static MetaEntityProvider newMetaEntityProvider(final Reference reference, final MetaEntity... metaEntity) {
		return MetaEntityProviderSupport.builder().add(reference, metaEntity).build();
	}

	public static MetaEntityProvider newMetaEntityProvider(final Reference reference, final Collection<MetaEntity> metaEntities) {
		return MetaEntityProviderSupport.builder().add(reference, metaEntities).build();
	}

	public static MetaEntityProvider newMetaEntityProvider(File root, EntityMapper entityMapper) {
		return new MetaEntityProviderFileSystem(root, entityMapper);
	}

	public static MetaEntityProvider newMetaEntityProvider(MetaEntityProvider... metaEntityProviders) {
		return new MetaEntityProviderComposite(metaEntityProviders);
	}

	public static MetaEntityProvider newMetaEntityProvider(final Collection<MetaEntityProvider> metaEntityProviders) {
		return new MetaEntityProviderComposite(metaEntityProviders);
	}

	public static MetaEntityProvider selectLastVersion(MetaEntityProvider metaEntityProvider) {
		return new MetaEntityProviderFilterLastVersion(metaEntityProvider);
	}

	public static MetaEntityProvider filter(MetaEntityProvider metaEntityProvider, Predicate<MetaEntity> predicate) {
		return new MetaEntityProviderFilter(metaEntityProvider, predicate);
	}

}
