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
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.util.Iterator;
import java.util.Map;

import org.intelligentsia.dowsers.core.reflection.ClassInformation;
import org.intelligentsia.dowsers.core.serializers.jackson.DowsersJacksonModule;
import org.intelligentsia.keystone.api.artifacts.Version;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.EntityDynamic;
import com.intelligentsia.dowsers.entity.EntityProxy;
import com.intelligentsia.dowsers.entity.Reference;
import com.intelligentsia.dowsers.entity.meta.MetaAttribute;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContext;
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

		addSerializer(new EntitySerializer<Entity>(Entity.class));
		addDeserializer(EntityProxy.class, new EntityProxyDeSerializer());
		addDeserializer(EntityDynamic.class, new EntityDynamicDeSerializer(metaEntityContextProvider));
		declareVersion();
		// declareMetaAttribute(metaEntityContextProvider);
		// declareMetaEntity(metaEntityContextProvider);
	}

	// public void declareMetaEntity(final MetaEntityContextProvider
	// metaEntityContextProvider) {
	// addDeserializer(MetaEntity.class, new
	// EntityDeSerializer<MetaEntity>(MetaEntity.class, new
	// EntityFactory<MetaEntity>() {
	// @Override
	// public MetaEntity newInstance(final String identity, final Map<String,
	// Object> attributes) {
	// return new MetaEntity(identity, attributes);
	// }
	// }, metaEntityContextProvider));
	// }
	//
	// public void declareMetaAttribute(final MetaEntityContextProvider
	// metaEntityContextProvider) {
	// addDeserializer(MetaAttribute.class, new
	// EntityDeSerializer<MetaAttribute>(MetaAttribute.class, new
	// EntityFactory<MetaAttribute>() {
	// @Override
	// public MetaAttribute newInstance(final String identity, final Map<String,
	// Object> attributes) {
	// return new MetaAttribute(identity, attributes);
	// }
	// }, metaEntityContextProvider));
	// }

	public void declareVersion() {
		addSerializer(new VersionSerializer());
		addDeserializer(Version.class, new VersionDeSerializer());
	}

	/**
	 * EntitySerializer.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
	 */
	public static class EntitySerializer<T extends Entity> extends StdSerializer<T> {

		public EntitySerializer(final Class<T> className) {
			super(className);
		}

		@Override
		public void serialize(final T value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
			jgen.writeStartObject();
			if (value != null) {
				// proxy case
				if (Proxy.isProxyClass(value.getClass())) {
					final InvocationHandler handler = Proxy.getInvocationHandler(value);
					if (!EntityProxy.class.isAssignableFrom(handler.getClass())) {
						throw new JsonGenerationException("Cannot Serialize an EntityProxyHandler that did not came from EntityProxy");
					}
					final EntityProxy entityProxy = (EntityProxy) handler;
					jgen.writeObjectField("@interface", entityProxy.getInterfaceName().getName());
					jgen.writeObjectField("@support", entityProxy.getEntity().getClass().getName());
					jgen.writeFieldName("@entity");
					jgen.writeStartObject();
					serializeEntity(value, jgen);
					jgen.writeEndObject();
				} else {
					// normal case
					serializeEntity(value, jgen);
				}
			}
			jgen.writeEndObject();
		}

		public void serializeEntity(final T value, final JsonGenerator jgen) throws IOException, JsonProcessingException, JsonGenerationException {
			jgen.writeObjectField("@reference", Reference.newEntityReference(value));
			jgen.writeFieldName("@attributes");
			jgen.writeStartObject();
			final Iterator<String> iterator = value.attributeNames().iterator();
			while (iterator.hasNext()) {
				final String name = iterator.next();
				jgen.writeObjectField(name, value.attribute(name));
			}
			jgen.writeEndObject();
		}
	}

	/**
	 * EntityDynamicDeSerializer.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
	 */
	public static class EntityDynamicDeSerializer extends StdDeserializer<EntityDynamic> {

		private static final long serialVersionUID = 7124116996885105048L;

		/**
		 * {@link MetaEntityContextProvider} instance.
		 */
		private final MetaEntityContextProvider metaEntityContextProvider;

		/**
		 * Build a new instance of EntityDynamicDeSerializer.
		 * 
		 * @param metaEntityContextProvider
		 * @throws NullPointerException
		 *             if metaEntityContextProvider is null
		 */
		public EntityDynamicDeSerializer(final MetaEntityContextProvider metaEntityContextProvider) throws NullPointerException {
			super(EntityDynamic.class);
			this.metaEntityContextProvider = Preconditions.checkNotNull(metaEntityContextProvider);
		}

		@Override
		public EntityDynamic deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
			final Map<String, Object> attributes = Maps.newLinkedHashMap();
			URI reference = null;
			MetaEntityContext context = null;
			String identity = null;
			while (jp.nextToken() != JsonToken.END_OBJECT) {
				final String fieldname = jp.getCurrentName();
				if ("@reference".equals(fieldname)) {
					jp.nextToken();
					reference = jp.readValueAs(URI.class);
					identity = Reference.getIdentity(reference);
					context = metaEntityContextProvider.find(reference);
				}
				if ("@attributes".equals(fieldname)) {
					jp.nextToken();
					String name = null;
					while (jp.nextToken() != JsonToken.END_OBJECT) {
						if (jp.getCurrentToken().equals(JsonToken.FIELD_NAME)) {
							name = jp.getText();
							final MetaAttribute attribute = context.metaAttribute(name);
							jp.nextToken();
							final Object value = jp.readValueAs(attribute != null ? attribute.valueClass().getType() : Object.class);
							attributes.put(name, value);
						}
					}
				}
			}
			return reference != null ? new EntityDynamic(identity, attributes, context) : null;
		}

	}

	/**
	 * 
	 * EntityProxyDeSerializer.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	public static class EntityProxyDeSerializer extends StdDeserializer<EntityProxy> {

		/**
		 * serialVersionUID:long
		 */
		private static final long serialVersionUID = -1025718595928596570L;

		public EntityProxyDeSerializer() {
			super(EntityProxy.class);
		}

		@Override
		public EntityProxy deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ClassInformation interfaceName = null;
			ClassInformation support = null;
			Entity entity = null;
			while (jp.nextToken() != JsonToken.END_OBJECT) {
				final String fieldname = jp.getCurrentName();
				if ("@interface".equals(fieldname)) {
					jp.nextToken();
					interfaceName = ClassInformation.parse(jp.getText());
				}
				if ("@support".equals(fieldname)) {
					jp.nextToken();
					support = ClassInformation.parse(jp.getText());
				}
				if ("@entity".equals(fieldname)) {
					if (support == null) {
						throw new JsonParseException("@support is required in order to decode Entity", jp.getCurrentLocation());
					}
					jp.nextToken();
					entity = (Entity) jp.readValueAs(support.getType());
				}
			}
			return entity != null ? new EntityProxy(interfaceName.getType(), entity) : null;
		}
	}

	/**
	 * VersionSerializer.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	public static class VersionSerializer extends StdSerializer<Version> {

		public VersionSerializer() {
			super(Version.class);
		}

		@Override
		public void serialize(final Version value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
			if (value != null) {
				jgen.writeString(Version.format(value));
			} else {
				jgen.writeNull();
			}
		}
	}

	/**
	 * 
	 * VersionDeSerializer.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	public static class VersionDeSerializer extends StdDeserializer<Version> {

		/**
		 * serialVersionUID:long
		 */
		private static final long serialVersionUID = 1916647538058301330L;

		public VersionDeSerializer() {
			super(Version.class);
		}

		@Override
		public Version deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
			if (jp.hasCurrentToken()) {
				final String value = jp.getText();
				if (value != null) {
					return Version.parse(value);
				}
			}
			return null;
		}
	}

}
