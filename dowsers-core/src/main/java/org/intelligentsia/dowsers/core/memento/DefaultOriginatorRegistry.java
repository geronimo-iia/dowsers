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
package org.intelligentsia.dowsers.core.memento;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.UncheckedExecutionException;

/**
 * DefaultOriginatorRegistry implements a default OriginatorRegistry with a
 * default originator, and find cache management.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DefaultOriginatorRegistry implements OriginatorRegistry {

	/**
	 * Set of Originator.
	 */
	private final Set<Originator> originators;
	/**
	 * Default Originator selection.
	 */
	private final Originator defaultOriginator;
	/**
	 * Cache of class type name, associated Originator.
	 */
	private final LoadingCache<Class<?>, Originator> cache;

	/**
	 * Build a new instance of DefaultOriginatorRegistry. Inner cache will be
	 * configured with 1000 entry by default and expire after one hours.
	 * 
	 * @param originator
	 *            default Originator instance
	 */
	public DefaultOriginatorRegistry(final Originator originator) {
		this(CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(1, TimeUnit.HOURS), originator);
	}

	/**
	 * Build a new instance of DefaultOriginatorRegistry.
	 * 
	 * @param cacheBuilder
	 *            cache Builder instance.
	 * @param defaultOriginator
	 *            default Originator instance
	 */
	public DefaultOriginatorRegistry(final CacheBuilder<Object, Object> cacheBuilder, final Originator originator) {
		super();
		originators = Sets.newHashSet();
		defaultOriginator = Preconditions.checkNotNull(originator);
		cache = cacheBuilder.build(new CacheLoader<Class<?>, Originator>() {
			@Override
			public Originator load(final Class<?> key) throws Exception {
				for (final Originator originator : originators) {
					if (originator.support(key)) {
						return originator;
					}
				}
				return defaultOriginator;
			}
		});
	}

	/**
	 * @see org.intelligentsia.dowsers.core.Registry#register(java.lang.Object)
	 */
	@Override
	public void register(final Originator object) throws NullPointerException {
		originators.add(Preconditions.checkNotNull(object));
	}

	@Override
	public <T> Originator find(final Class<T> entity) {
		try {
			return cache.getUnchecked(entity.getClass());
		} catch (final UncheckedExecutionException e) {
			return defaultOriginator;
		}
	}

}
