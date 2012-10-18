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
import java.util.Iterator;
import java.util.Map;

import org.intelligentsia.dowsers.core.reflection.ClassInformation;
import org.intelligentsia.dowsers.core.serializers.jackson.DowsersJacksonModule;

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
import com.google.common.collect.Maps;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.EntityDynamic;
import com.intelligentsia.dowsers.entity.EntityProxy;

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

	public EntityDowsersJacksonModule() {
		super();

		addSerializer(EntityProxyHandler.class, new EntityProxySerializer());
		addDeserializer(EntityProxy.class, new EntityProxyJsonDeSerializer());

		addSerializer(new EntityDynamicJsonSerializer());
		addDeserializer(EntityDynamic.class, new EntityDynamicJsonDeSerializer());

	}

	public class EntityProxySerializer extends StdSerializer<EntityProxyHandler> {

		public EntityProxySerializer() {
			super(EntityProxyHandler.class);
		}

		@Override
		public void serialize(final EntityProxyHandler value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
			jgen.writeStartObject();
			if (value != null) {
				final InvocationHandler handler = Proxy.getInvocationHandler(value);
				if (!EntityProxy.class.isAssignableFrom(handler.getClass())) {
					throw new JsonGenerationException("Cannot Serialize an EntityProxyHandler that did not came from EntityProxy");
				}
				final EntityProxy entityProxy = (EntityProxy) handler;
				jgen.writeObjectField("interface", entityProxy.getInterfaceName().getName());
				jgen.writeObjectField("support-class", new ClassInformation(entityProxy.getEntity().getClass()).getDescription());
				jgen.writeObjectField("entity", entityProxy.getEntity());
			}
			jgen.writeEndObject();
		}
	}

	public class EntityProxyJsonDeSerializer extends StdDeserializer<EntityProxy> {

		/**
		 * serialVersionUID:long
		 */
		private static final long serialVersionUID = -1025718595928596570L;

		public EntityProxyJsonDeSerializer() {
			super(EntityProxy.class);
		}

		@Override
		public EntityProxy deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
			if (jp.hasCurrentToken()) {
				ClassInformation interfaceName = null;
				ClassInformation support = null;
				Entity entity = null;
				if (jp.getCurrentToken().equals(JsonToken.START_OBJECT)) {
					// load interface
					jp.nextToken();
					if (!jp.getCurrentToken().equals(JsonToken.FIELD_NAME)) {
						throw new JsonParseException("Attended 'interface' field", jp.getCurrentLocation());
					}
					jp.nextToken();
					interfaceName = ClassInformation.parse(jp.getText());
					// load support-class
					jp.nextToken();
					if (!jp.getCurrentToken().equals(JsonToken.FIELD_NAME)) {
						throw new JsonParseException("Attended 'support-class' field", jp.getCurrentLocation());
					}
					jp.nextToken();
					support = ClassInformation.parse(jp.getText());
					jp.nextToken();
					if (!jp.getCurrentToken().equals(JsonToken.FIELD_NAME)) {
						throw new JsonParseException("Attended 'entity' field", jp.getCurrentLocation());
					}
					jp.nextToken();
					entity = (Entity) jp.readValueAs(support.getType());
					jp.nextToken();
				}

				return new EntityProxy(interfaceName.getType(), entity);
			}

			return null;
		}
	}

	/**
	 * EntityDynamicJsonSerializer.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
	 */
	public class EntityDynamicJsonSerializer extends StdSerializer<EntityDynamic> {

		public EntityDynamicJsonSerializer() {
			super(EntityDynamic.class);
		}

		@Override
		public void serialize(final EntityDynamic value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
			jgen.writeStartObject();
			jgen.writeStringField("identity", value.identity());
			jgen.writeFieldName("attributes");
			jgen.writeStartObject();
			final Iterator<String> iterator = value.attributeNames().iterator();
			while (iterator.hasNext()) {
				final String name = iterator.next();
				jgen.writeObjectField(name, value.attribute(name));
			}
			jgen.writeEndObject();
			jgen.writeEndObject();
		}

	}

	/**
	 * EntityDynamicJsonDeSerializer.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
	 */
	public class EntityDynamicJsonDeSerializer extends StdDeserializer<EntityDynamic> {

		/**
		 * serialVersionUID:long
		 */
		private static final long serialVersionUID = 7124116996885105048L;

		public EntityDynamicJsonDeSerializer() throws NullPointerException {
			super(EntityDynamic.class);
		}

		@Override
		public EntityDynamic deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
			String identity = null;
			final Map<String, Object> attributes = Maps.newLinkedHashMap();

			if (jp.hasCurrentToken()) {
				if (jp.getCurrentToken().equals(JsonToken.START_OBJECT)) {
					// identity
					jp.nextValue();
					identity = jp.getText();
					jp.nextToken();
					if (jp.getCurrentToken().equals(JsonToken.FIELD_NAME)) {
						// attributes
						jp.nextToken();
					}
					if (jp.getCurrentToken().equals(JsonToken.START_OBJECT)) {
						jp.nextToken();
						do {
							final String name = jp.getCurrentName();
							jp.nextToken();
							final Object value = jp.readValueAs(Object.class);
							jp.nextToken();
							attributes.put(name, value);

						} while (!jp.getCurrentToken().equals(JsonToken.END_OBJECT));
					}
					jp.nextToken();
				}
			}
			return new EntityDynamic(identity, attributes);
		}

	}

}
