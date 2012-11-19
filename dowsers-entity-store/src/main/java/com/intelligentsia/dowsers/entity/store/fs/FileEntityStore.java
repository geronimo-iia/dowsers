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
package com.intelligentsia.dowsers.entity.store.fs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.concurrent.TimeUnit;

import org.intelligentsia.dowsers.core.DowsersException;
import org.intelligentsia.keystone.kernel.api.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.io.Closeables;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.reference.References;
import com.intelligentsia.dowsers.entity.serializer.EntityMapper;
import com.intelligentsia.dowsers.entity.store.ConcurrencyException;
import com.intelligentsia.dowsers.entity.store.EntityNotFoundException;
import com.intelligentsia.dowsers.entity.store.EntityStore;

/**
 * FileEntityStore implements EntityStore using File system.
 * 
 * <p>
 * Each entity is store under root directory like this:
 * </p>
 * <code>
 * ${root}/${entity class name}/{insert(entity.reference.identity,/, %2)/entity.reference.identity
 * </code>
 * <p>
 * Example for a instance of Person with id
 * '1b8f388f-9bfb-429a-a68b-dea17c7765ee':
 * </p>
 * <code>
 * ${root}/com.intelligentsia.dowsers.entity.model.Person/1b/8f/38/8f/9b/fb/42/9a/a6/8b/de/a1/7c/77/65/ee/1b8f388f-9bfb-429a-a68b-dea17c7765ee
 * </code>
 * 
 * This implementation use a cache to map entity.reference and file.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class FileEntityStore implements EntityStore {

	/**
	 * {@link EntityMapper} instance.
	 */
	private final EntityMapper entityMapper;

	/**
	 * {@link LoadingCache} of File.
	 */
	private final LoadingCache<Reference, File> files;

	/**
	 * Build a new instance of FileEntityStore with default cache:
	 * <ul>
	 * <li>Maximum size : 1000 elements</li>
	 * <li>expire after access : 1 hour</li>
	 * </ul>
	 * 
	 * @param root
	 *            root directory of this {@link EntityStore}.
	 * @param entityMapper
	 *            {@link EntityMapper} to use
	 * @throws NullPointerException
	 *             if one of parameters is null
	 * @throws IllegalStateException
	 *             if root is not a directory of if it cannot be created
	 */
	public FileEntityStore(final File root, final EntityMapper entityMapper) {
		this(root, entityMapper, CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(1, TimeUnit.HOURS));
	}

	/**
	 * Build a new instance of <code>FileEntityStore</code>.
	 * 
	 * @param root
	 *            root directory of this {@link EntityStore}.
	 * @param entityMapper
	 *            {@link EntityMapper} to use
	 * @param cacheBuilder
	 *            cache Builder
	 * @throws NullPointerException
	 *             if one of parameters is null
	 * @throws IllegalStateException
	 *             if root is not a directory of if it cannot be created
	 */
	public FileEntityStore(final File root, final EntityMapper entityMapper, final CacheBuilder<Object, Object> cacheBuilder) throws NullPointerException, IllegalStateException {
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
		// cache
		files = Preconditions.checkNotNull(cacheBuilder).build(new CacheLoader<Reference, File>() {
			@Override
			public File load(final Reference urn) throws Exception {
				final StringBuilder builder = new StringBuilder(urn.getIdentity().replace("-", ""));
				for (int i = 2; i < builder.length(); i += 2) {
					builder.insert(i, File.separator);
					i++;
				}
				builder.append(File.separator).append(urn.getIdentity());
				return new File(new File(root, urn.getEntityClassName()), builder.toString());
			}
		});
	}

	@Override
	public <T> T find(final Class<T> expectedType, final Reference reference) throws EntityNotFoundException, NullPointerException {
		final File file = getFile(reference, false);
		if (!file.exists()) {
			throw new EntityNotFoundException();
		}
		Reader reader = null;
		try {
			reader = new FileReader(file);
			return entityMapper.readValue(reader, expectedType);
		} catch (final FileNotFoundException e) {
			throw new EntityNotFoundException(e);
		} finally {
			if (reader != null) {
				Closeables.closeQuietly(reader);
			}
		}
	}

	@Override
	public <T> void store(final T entity) throws NullPointerException, ConcurrencyException, DowsersException {
		final File file = getFile(References.identify(entity), true);
		Writer writer = null;
		try {
			writer = new FileWriter(file);
			entityMapper.writeValue(writer, entity);
		} catch (final IOException e) {
			throw new DowsersException(e);
		} finally {
			if (writer != null) {
				Closeables.closeQuietly(writer);
			}
		}
	}

	@Override
	public <T> void remove(final T entity) throws NullPointerException {
		remove(References.identify(entity));
	}

	@Override
	public void remove(final Reference reference) throws NullPointerException, IllegalArgumentException {
		final File file = getFile(reference, false);
		file.delete();
	}

	public File getFile(final Reference urn, final boolean create) {
		try {
			final File file = files.getUnchecked(urn);
			if (create) {
				file.getParentFile().mkdirs();
			}
			return file;
		} catch (final UncheckedExecutionException e) {
			throw Throwables.propagate(e);
		}
	}

}
