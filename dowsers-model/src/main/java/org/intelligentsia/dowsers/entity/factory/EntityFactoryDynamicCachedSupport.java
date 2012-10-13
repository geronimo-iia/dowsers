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
package org.intelligentsia.dowsers.entity.factory;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.intelligentsia.dowsers.core.EntityDowsersException;
import org.intelligentsia.dowsers.entity.Entity;
import org.intelligentsia.dowsers.entity.EntityDynamicSupport;
import org.intelligentsia.dowsers.entity.meta.MetaAttribute;
import org.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import org.intelligentsia.dowsers.entity.meta.MetaEntityContextRepository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;

/**
 * EntityFactoryDynamicCachedSupport extends {@link EntityFactoryDynamicSupport}
 * with attributes definition cache.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class EntityFactoryDynamicCachedSupport extends EntityFactoryDynamicSupport {
	/**
	 * {@link Cache} instance.
	 */
	private final LoadingCache<MetaEntityContext, Map<String, Object>> definitions;

	public EntityFactoryDynamicCachedSupport(final MetaEntityContextRepository metaEntityContextRepository) throws NullPointerException {
		super(metaEntityContextRepository);
		definitions = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build(new CacheLoader<MetaEntityContext, Map<String, Object>>() {
			@Override
			public Map<String, Object> load(final MetaEntityContext metaEntityContext) throws Exception {
				final Map<String, Object> properties = Maps.newHashMap();
				final Iterator<String> iterator = metaEntityContext.metaAttributeNames();
				while (iterator.hasNext()) {
					final MetaAttribute metaAttribute = metaEntityContext.metaAttributes(iterator.next());
					// TODO clone it
					properties.put(metaAttribute.name(), metaAttribute.defaultValue());
				}
				return properties;
			}
		});
	}

	@Override
	protected Entity instanciateEntity(final String identity, final MetaEntityContext metaEntityContext) {
		return new EntityDynamicSupport(identity, metaEntityContext, getAttributes(metaEntityContext));
	}

	private Map<String, Object> getAttributes(final MetaEntityContext metaEntityContext) {
		try {
			return Maps.newHashMap(definitions.get(metaEntityContext));
		} catch (final ExecutionException e) {
			throw new EntityDowsersException(e);
		}
	}

}
