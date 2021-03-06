/**
 *        Licensed to the Apache Software Foundation (ASF) under one
 *        or more contributor license agreements.  See the NOTICE file
 *        distributed with this work for additional information
 *        regarding copyright ownership.  The ASF licenses this file
 *        to you under the Apache License, Version 2.0 (the
 *        "License"); you may not use this file except in compliance
 *        with the License.  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing,
 *        software distributed under the License is distributed on an
 *        "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *        KIND, either express or implied.  See the License for the
 *        specific language governing permissions and limitations
 *        under the License.
 *
 */
package com.intelligentsia.dowsers.entity.meta.provider;

import java.io.File;
import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaEntityProvider;
import com.intelligentsia.dowsers.entity.meta.MetaEntityProviderSupport;
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
	 * Build a {@link MetaEntityProvider} which aggregate for each directory
	 * under root, a {@link MetaEntityProviderFileSystem} which select last
	 * version.
	 * 
	 * This permit to define meta entity from several extension with version
	 * management.
	 * 
	 * @param reference
	 *            {@link Reference}
	 * @param root
	 *            Root {@link File}
	 * @param entityMapper
	 * @return {@link MetaEntityProvider} instance
	 */
	public static MetaEntityProvider newMetaEntityProvider(final Reference reference, final File root, final EntityMapper entityMapper) {
		return newMetaEntityProvider(reference, root, entityMapper, false);
	}

	/**
	 * Build a {@link MetaEntityProvider} which aggregate for each directory
	 * under root, a {@link MetaEntityProviderFileSystem} which select last
	 * version.
	 * 
	 * This permit to define meta entity from several extension with version
	 * management.
	 * 
	 * @param reference
	 *            {@link Reference}
	 * @param root
	 *            Root {@link File}
	 * @param entityMapper
	 * @param addAnalyzer
	 *            if true add {@link MetaEntityProviderAnalyzer} in first
	 *            {@link MetaEntityProvider}
	 * @return {@link MetaEntityProvider} instance
	 */
	public static MetaEntityProvider newMetaEntityProvider(final Reference reference, final File root, final EntityMapper entityMapper, final boolean addAnalyzer) {
		// for each directory under root, build a file meta entity provider and
		// select last version under each
		final Collection<MetaEntityProvider> metaEntityProviders = Lists.newLinkedList();
		if (addAnalyzer) {
			metaEntityProviders.add(newMetaEntityProviderAnalyzer());
		}
		for (final File file : root.listFiles()) {
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

	public static MetaEntityProvider newMetaEntityProvider(final File root, final EntityMapper entityMapper) {
		return new MetaEntityProviderFileSystem(root, entityMapper);
	}

	public static MetaEntityProvider newMetaEntityProvider(final MetaEntityProvider... metaEntityProviders) {
		return new MetaEntityProviderComposite(metaEntityProviders);
	}

	public static MetaEntityProvider newMetaEntityProvider(final Collection<MetaEntityProvider> metaEntityProviders) {
		return new MetaEntityProviderComposite(metaEntityProviders);
	}

	public static MetaEntityProvider selectLastVersion(final MetaEntityProvider metaEntityProvider) {
		return new MetaEntityProviderFilterLastVersion(metaEntityProvider);
	}

	public static MetaEntityProvider filter(final MetaEntityProvider metaEntityProvider, final Predicate<MetaEntity> predicate) {
		return new MetaEntityProviderFilter(metaEntityProvider, predicate);
	}

}
