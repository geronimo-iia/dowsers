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

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.intelligentsia.dowsers.core.serializers.jackson.DowsersJacksonModule;
import org.intelligentsia.keystone.api.artifacts.Version;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.EntityDynamic;
import com.intelligentsia.dowsers.entity.EntityProxy;
import com.intelligentsia.dowsers.entity.Reference;
import com.intelligentsia.dowsers.entity.meta.MetaAttribute;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextRepository;

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

	private final MetaEntityContextRepository metaEntityContextRepository;

	public EntityDowsersJacksonModule(MetaEntityContextRepository metaEntityContextRepository) throws NullPointerException {
		super();
		this.metaEntityContextRepository = Preconditions.checkNotNull(metaEntityContextRepository);

		// proxy support
		addSerializer(EntityProxyHandler.class, new EntityProxySerializer());
		addDeserializer(EntityProxy.class, new EntityProxyDeSerializer());

		// entity dynamic
		addSerializer(new EntitySerializer<EntityDynamic>(EntityDynamic.class));
		addDeserializer(EntityDynamic.class, new EntityDeSerializer<EntityDynamic>(EntityDynamic.class, new EntityDynamicFactory<EntityDynamic>() {

			@Override
			public EntityDynamic newInstance(String identity, Map<String, Object> attributes) {
				return new EntityDynamic(identity, attributes);
			}
		}));

		// Version object
		addSerializer(new VersionSerializer());
		addDeserializer(Version.class, new VersionDeSerializer());

		// meta attribute
		addDeserializer(MetaAttribute.class, new EntityDeSerializer<MetaAttribute>(MetaAttribute.class, new EntityDynamicFactory<MetaAttribute>() {
			@Override
			public MetaAttribute newInstance(String identity, Map<String, Object> attributes) {
				return new MetaAttribute(identity, attributes);
			}
		}));

		// meta entity
		addDeserializer(MetaEntity.class, new EntityDeSerializer<MetaEntity>(MetaEntity.class, new EntityDynamicFactory<MetaEntity>() {
			@Override
			public MetaEntity newInstance(String identity, Map<String, Object> attributes) {
				return new MetaEntity(identity, attributes);
			}
		}));

	}

	/**
	 * EntityDynamicDeSerializer.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
	 */
	public class EntityDeSerializer<T extends Entity> extends StdDeserializer<T> {

		private static final long serialVersionUID = 7124116996885105048L;

		private final EntityDynamicFactory<T> factory;

		public EntityDeSerializer(Class<T> className, EntityDynamicFactory<T> factory) throws NullPointerException {
			super(className);
			this.factory = Preconditions.checkNotNull(factory);
		}

		@Override
		public T deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
			if (jp.hasCurrentToken()) {

				if (!jp.getCurrentToken().equals(JsonToken.START_OBJECT)) {
					throw new JsonParseException("Object attended", jp.getCurrentLocation());
				}
				jp.nextToken();
				if (!jp.getCurrentToken().equals(JsonToken.FIELD_NAME) || !"@reference".equals(jp.getText())) {
					throw new JsonParseException("@reference attended", jp.getCurrentLocation());
				}
				jp.nextToken();
				URI uri = jp.readValueAs(URI.class);
				jp.nextToken();
				String identity = Reference.getIdentity(uri);
				// get MetaEntityContext
				MetaEntityContext context = metaEntityContextRepository.find(uri);

				if (!jp.getCurrentToken().equals(JsonToken.FIELD_NAME) || !"@attributes".equals(jp.getText())) {
					throw new JsonParseException("@attributes attended", jp.getCurrentLocation());
				}
				jp.nextToken();
				jp.nextToken(); // start @attributes
				final Map<String, Object> attributes = Maps.newLinkedHashMap();
				do {
					final String name = jp.getCurrentName();
					jp.nextToken();
					MetaAttribute attribute = context.metaAttribute(name);
					final Object value = jp.readValueAs(attribute != null ? attribute.valueClass().getType() : Object.class);
					jp.nextToken();
					attributes.put(name, value);

				} while (!jp.getCurrentToken().equals(JsonToken.END_OBJECT));
				jp.nextToken(); // end @attributes
				jp.nextToken(); // end entity
				return factory.newInstance(identity, attributes);
			}
			return null;
		}
	}

}
