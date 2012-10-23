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
package com.intelligentsia.dowsers.entity.serializer;

import java.util.Map;

import org.intelligentsia.dowsers.core.serializers.jackson.DowsersJacksonModule;
import org.intelligentsia.keystone.api.artifacts.Version;

import com.intelligentsia.dowsers.entity.EntityDynamic;
import com.intelligentsia.dowsers.entity.EntityProxy;
import com.intelligentsia.dowsers.entity.meta.MetaAttribute;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProvider;

/**
 * EntityDowsersJacksonModule.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityDowsersJacksonModule extends DowsersJacksonModule {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -5511198331164987259L;

	/**
	 * Build a new instance of EntityDowsersJacksonModule.java.
	 * 
	 * @param metaEntityContextProvider
	 * @throws NullPointerException
	 */
	public EntityDowsersJacksonModule(final MetaEntityContextProvider metaEntityContextProvider) throws NullPointerException {
		super();
		declareEntityProxy();
		declareEntityDynamic(metaEntityContextProvider);
		declareVersion();
		declareMetaAttribute(metaEntityContextProvider);
		declareMetaEntity(metaEntityContextProvider);
	}

	public void declareMetaEntity(final MetaEntityContextProvider metaEntityContextProvider) {
		addDeserializer(MetaEntity.class, new EntityDeSerializer<MetaEntity>(MetaEntity.class, new EntityDynamicFactory<MetaEntity>() {
			@Override
			public MetaEntity newInstance(final String identity, final Map<String, Object> attributes) {
				return new MetaEntity(identity, attributes);
			}
		}, metaEntityContextProvider));
	}

	public void declareMetaAttribute(final MetaEntityContextProvider metaEntityContextProvider) {
		addDeserializer(MetaAttribute.class, new EntityDeSerializer<MetaAttribute>(MetaAttribute.class, new EntityDynamicFactory<MetaAttribute>() {
			@Override
			public MetaAttribute newInstance(final String identity, final Map<String, Object> attributes) {
				return new MetaAttribute(identity, attributes);
			}
		}, metaEntityContextProvider));
	}

	public void declareVersion() {
		addSerializer(new VersionSerializer());
		addDeserializer(Version.class, new VersionDeSerializer());
	}

	public void declareEntityDynamic(final MetaEntityContextProvider metaEntityContextProvider) {
		addSerializer(new EntitySerializer<EntityDynamic>(EntityDynamic.class));
		addDeserializer(EntityDynamic.class, new EntityDeSerializer<EntityDynamic>(EntityDynamic.class, new EntityDynamicFactory<EntityDynamic>() {

			@Override
			public EntityDynamic newInstance(final String identity, final Map<String, Object> attributes) {
				return new EntityDynamic(identity, attributes);
			}
		}, metaEntityContextProvider));
	}

	public void declareEntityProxy() {
		addSerializer(EntityProxyHandler.class, new EntityProxySerializer());
		addDeserializer(EntityProxy.class, new EntityProxyDeSerializer());
	}

}
