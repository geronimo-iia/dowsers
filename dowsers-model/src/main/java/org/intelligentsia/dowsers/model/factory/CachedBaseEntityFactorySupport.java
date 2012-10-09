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
package org.intelligentsia.dowsers.model.factory;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.intelligentsia.dowsers.core.DowsersException;
import org.intelligentsia.dowsers.model.BaseEntity;
import org.intelligentsia.dowsers.model.meta.MetaEntityContext;
import org.intelligentsia.dowsers.model.meta.MetaEntityContextRepository;
import org.intelligentsia.dowsers.model.meta.MetaProperty;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;

/**
 * 
 * CachedBaseEntityFactorySupport extends BaseEntityFactorySupport with
 * properties definition cache.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class CachedBaseEntityFactorySupport extends BaseEntityFactorySupport {

	/**
	 * {@link Cache} instance.
	 */
	private LoadingCache<MetaEntityContext, Map<String, Object>> definitions;

	public CachedBaseEntityFactorySupport(MetaEntityContextRepository metaEntityContextRepository) throws NullPointerException {
		super(metaEntityContextRepository);
		definitions = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build(new CacheLoader<MetaEntityContext, Map<String, Object>>() {
			@Override
			public Map<String, Object> load(MetaEntityContext metaEntityContext) throws Exception {
				Map<String, Object> properties = Maps.newHashMap();
				final Iterator<String> iterator = metaEntityContext.getMetaPropertyNames();
				while (iterator.hasNext()) {
					final MetaProperty metaProperty = metaEntityContext.getMetaProperty(iterator.next());
					properties.put(metaProperty.getName(), metaProperty.getDefaultValue());
				}
				return properties;
			}
		});
	}

	@Override
	protected BaseEntity instanciateBaseEntity(String identity, MetaEntityContext metaEntityContext) {
		return new BaseEntity(identity, metaEntityContext, getProperties(metaEntityContext));
	}

	private Map<String, Object> getProperties(final MetaEntityContext metaEntityContext) {
		try {
			return Maps.newHashMap(definitions.get(metaEntityContext));
		} catch (ExecutionException e) {
			throw new DowsersException(e);
		}
	}

}
