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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.Reader;
import java.util.Collection;

import org.intelligentsia.keystone.api.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.google.common.io.Closeables;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaEntityProvider;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.serializer.EntityMapper;

/**
 * 
 * {@link MetaEntityProviderFileSystem} implements {@link MetaEntityProvider}
 * using file system.
 * <p>
 * This implementation load all {@link MetaEntity} definition from a root
 * directory according this source tree:
 * </p>
 * <code>
 * 		${root}
 * 			${entity class name}
 * 				${meta entity file 1}
 * 				${meta entity file 2}
 * 				. . . 
 * </code>
 * <p>
 * meta entity file could have json, xml or have no extension.
 * </p>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class MetaEntityProviderFileSystem implements MetaEntityProvider {

	private static final transient MetaEntityFilenameFilter FILTER = new MetaEntityFilenameFilter();
	/**
	 * {@link EntityMapper} instance.
	 */
	private final EntityMapper entityMapper;

	/**
	 * Root file.
	 */
	private final File root;

	/**
	 * Build a new instance of <code>MetaEntityProviderFileSystem</code>.
	 * 
	 * @param root
	 * 
	 *            root directory of this {@link MetaEntityProvider}.
	 * @param entityMapper
	 *            {@link EntityMapper} to use
	 * @throws NullPointerException
	 *             if one of parameters is null
	 * @throws IllegalStateException
	 *             if root is not a directory of if it cannot be created
	 */
	public MetaEntityProviderFileSystem(final File root, final EntityMapper entityMapper) throws NullPointerException, IllegalStateException {
		super();
		Preconditions.checkNotNull(root);
		this.entityMapper = Preconditions.checkNotNull(entityMapper);
		// check root
		if (!root.exists()) {
			if (!root.mkdirs()) {
				throw new IllegalStateException(StringUtils.format("unable to create directory '%s'", root));
			}
		}
		if (!root.isDirectory()) {
			throw new IllegalStateException(StringUtils.format("'%s' is not a directory", root));
		}
		this.root = root;
	}

	/**
	 * this implementation will try to find data under <code>
	 * ${root}/${entity class name}
	 * </code
	 * 
	 * @see com.intelligentsia.dowsers.entity.meta.MetaEntityProvider#find(com.intelligentsia.dowsers.entity.reference.Reference)
	 */
	@Override
	public Collection<MetaEntity> find(final Reference reference) throws NullPointerException {
		final Collection<MetaEntity> result = Sets.newLinkedHashSet();
		final File file = new File(root, reference.getEntityClassName());
		if (!file.exists() || !file.isDirectory()) {
			return result;
		}
		for (final File f : file.listFiles(FILTER)) {
			// for each file, load it
			Reader reader = null;
			try {
				reader = new FileReader(f);
				final MetaEntity metaEntity = entityMapper.readValue(reader, MetaEntity.class);
				result.add(metaEntity);
			} catch (final FileNotFoundException e) {
			} finally {
				if (reader != null) {
					Closeables.closeQuietly(reader);
				}
			}
		}
		return result;
	}

	/**
	 * MetaEntityFilenameFilter implements {@link FilenameFilter}.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
	 */
	private static class MetaEntityFilenameFilter implements FilenameFilter {

		@Override
		public boolean accept(final File dir, final String name) {
			final int index = name.lastIndexOf('.');
			if (index >= 0) {
				final String ext = name.substring(index);
				return "xml".equalsIgnoreCase(ext) || "json".equalsIgnoreCase(ext);
			}
			return true;
		}
	}
}
