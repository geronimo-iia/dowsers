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
package com.intelligentsia.dowsers.entity.store;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import org.intelligentsia.dowsers.core.DowsersException;
import org.intelligentsia.keystone.api.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.io.Closeables;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.reference.References;
import com.intelligentsia.dowsers.entity.serializer.EntityMapper;

/**
 * FileEntityStore.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class FileEntityStore implements EntityStore {
	/**
	 * {@link File} root.
	 */
	private final File root;

	/**
	 * {@link EntityMapper} instance.
	 */
	private final EntityMapper entityMapper;

	/**
	 * Build a new instance of FileEntityStore.
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
	public FileEntityStore(final File root, final EntityMapper entityMapper) throws NullPointerException, IllegalStateException {
		super();
		this.root = Preconditions.checkNotNull(root);
		this.entityMapper = Preconditions.checkNotNull(entityMapper);
		if (!root.exists()) {
			if (!root.mkdirs()) {
				throw new IllegalStateException(StringUtils.format("unable to create directory '%s'", root));
			}
		}
		if (!root.isDirectory()) {
			throw new IllegalStateException(StringUtils.format("'%s' is not a directory", root));
		}
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
		final StringBuilder builder = new StringBuilder();
		for (final String p : urn.getIdentity().split("\b{2}")) {
			builder.append(File.separator).append(p);
		}
		final File file = new File(new File(root, urn.getEntityClassName()), builder.deleteCharAt(0).toString());
		if (create) {
			file.getParentFile().mkdirs();
		}
		return file;
	}
}
