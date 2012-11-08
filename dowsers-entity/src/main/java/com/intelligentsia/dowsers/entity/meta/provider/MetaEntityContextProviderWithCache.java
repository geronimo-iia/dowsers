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
/**
 * 
 */
package com.intelligentsia.dowsers.entity.meta.provider;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProvider;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * MetaEntityContextProviderSupport.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 * 
 */
public class MetaEntityContextProviderWithCache implements MetaEntityContextProvider {

	/**
	 * {@link LoadingCache} of File.
	 */
	private final LoadingCache<Reference, MetaEntityContext> context;

	/**
	 * Build a new instance of MetaEntityContextProviderWithCache with a default
	 * cache.
	 * 
	 * @param metaEntityContextProvider
	 *            {@link MetaEntityContextProvider} instance
	 */
	public MetaEntityContextProviderWithCache(final MetaEntityContextProvider metaEntityContextProvider) {
		this(metaEntityContextProvider, CacheBuilder.newBuilder().maximumSize(5000).expireAfterAccess(1, TimeUnit.HOURS));
	}

	/**
	 * Build a new instance of <code>MetaEntityContextProviderWithCache</code>.
	 * 
	 * @param metaEntityContextProvider
	 *            {@link MetaEntityContextProvider} instance
	 * @param cacheBuilder
	 *            {@link CacheBuilder} instance
	 */
	public MetaEntityContextProviderWithCache(final MetaEntityContextProvider metaEntityContextProvider, final CacheBuilder<Object, Object> cacheBuilder) {
		super();
		context = Preconditions.checkNotNull(cacheBuilder).build(new CacheLoader<Reference, MetaEntityContext>() {
			@Override
			public MetaEntityContext load(final Reference reference) throws Exception {
				return metaEntityContextProvider.find(reference);
			}
		});
	}

	@Override
	public MetaEntityContext find(final Reference reference) throws IllegalArgumentException, NullPointerException {
		try {
			return context.get(Preconditions.checkNotNull(Preconditions.checkNotNull(reference).isIdentifier() ? reference.getEntityClassReference() : reference));
		} catch (final ExecutionException e) {
			throw new IllegalArgumentException(Throwables.getRootCause(e));
		}
	}

}
