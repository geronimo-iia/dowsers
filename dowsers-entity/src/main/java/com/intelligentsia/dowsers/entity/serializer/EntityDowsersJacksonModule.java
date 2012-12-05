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
import java.util.Set;

import org.intelligentsia.dowsers.core.reflection.ClassInformation;
import org.intelligentsia.dowsers.core.serializers.jackson.DowsersJacksonModule;
import org.intelligentsia.keystone.kernel.api.artifacts.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.google.common.collect.Sets;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.EntityDynamic;
import com.intelligentsia.dowsers.entity.EntityProxy;
import com.intelligentsia.dowsers.entity.meta.MetaAttribute;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProvider;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.reference.References;

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
	 * Logger instance.
	 */
	private static Logger logger = LoggerFactory.getLogger(EntityDowsersJacksonModule.class);

	/**
	 * Build a new instance of EntityDowsersJacksonModule.java.
	 * 
	 * @param metaEntityContextProvider
	 * @throws NullPointerException
	 */
	public EntityDowsersJacksonModule(final MetaEntityContextProvider metaEntityContextProvider) throws NullPointerException {
		super();

		/**
		 * Declare Serializer for all proxified class
		 */
		addSerializer(new EntitySerializer<EntityProxyHandler>(EntityProxyHandler.class, metaEntityContextProvider));
		addDeserializer(EntityProxy.class, new EntityProxyDeSerializer());

		/**
		 * Declare Serializer for all EntityDynamic class
		 */
		addSerializer(new EntitySerializer<EntityDynamic>(EntityDynamic.class, metaEntityContextProvider));
		addDeserializer(EntityDynamic.class, new EntityDynamicDeSerializer(metaEntityContextProvider));

		/**
		 * Declare Serializer for all Reference class
		 */
		addSerializer(new ReferenceSerializer());
		addDeserializer(Reference.class, new ReferenceDeSerializer());

		/**
		 * Declare Serializer for all Version class
		 */
		addSerializer(new VersionSerializer());
		addDeserializer(Version.class, new VersionDeSerializer());

	}

	/**
	 * EntitySerializer.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
	 */
	public static class EntitySerializer<T extends Entity> extends StdSerializer<T> {

		/**
		 * {@link MetaEntityContextProvider} instance.
		 */
		private final MetaEntityContextProvider metaEntityContextProvider;

		/**
		 * Build a new instance of EntitySerializer.
		 * 
		 * @param className
		 * @param metaEntityContextProvider
		 * @throws NullPointerException
		 *             if metaEntityContextProvider is null
		 */
		public EntitySerializer(final Class<T> className, final MetaEntityContextProvider metaEntityContextProvider) throws NullPointerException {
			super(className);
			this.metaEntityContextProvider = Preconditions.checkNotNull(metaEntityContextProvider);
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
			// WRITE IDENTITY
			jgen.writeObjectField("@identity", value.identity());
			// WRITE META IF NECESSARY
			// CHECK META NOT IN CONTEXT
			final MetaEntityContext context = metaEntityContextProvider.find(value.identity());
			if (context == null) {
				writeExtendedMeta(value, jgen, value.attributeNames());
			} else {
				final Set<String> extendedAttribute = Sets.difference(value.attributeNames(), context.attributeNames());
				if (!extendedAttribute.isEmpty()) {
					writeExtendedMeta(value, jgen, extendedAttribute);
				}
			}
			// WRITE ATTRIBUTES
			jgen.writeFieldName("@attributes");
			jgen.writeStartObject();
			final Iterator<String> iterator = value.attributeNames().iterator();
			while (iterator.hasNext()) {
				final String name = iterator.next();
				jgen.writeObjectField(name, value.attribute(name));
			}
			jgen.writeEndObject();

		}

		protected void writeExtendedMeta(final T value, final JsonGenerator jgen, final Set<String> attributeNames) throws JsonGenerationException, IOException {
			jgen.writeFieldName("@meta");
			jgen.writeStartObject();
			final Iterator<String> iterator = attributeNames.iterator();
			while (iterator.hasNext()) {
				final String name = iterator.next();
				final Object attribute = value.attribute(name);
				if (name != null) {
					jgen.writeObjectField(name, ClassInformation.toClassInformation(attribute.getClass()));
				}
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
			Reference identity = null;
			MetaEntityContext context = null;
			final Map<String, ClassInformation> extraMeta = Maps.newHashMap();

			while (jp.nextToken() != JsonToken.END_OBJECT) {
				final String fieldname = jp.getCurrentName();
				if ("@identity".equals(fieldname)) {
					jp.nextToken();
					identity = jp.readValueAs(Reference.class);
					context = metaEntityContextProvider.find(identity);
				}
				if ("@meta".equals(fieldname)) {
					jp.nextToken();
					String name = null;
					while (jp.nextToken() != JsonToken.END_OBJECT) {
						if (jp.getCurrentToken().equals(JsonToken.FIELD_NAME)) {
							name = jp.getText();
							jp.nextToken();
							final ClassInformation classInformation = jp.readValueAs(ClassInformation.class);
							extraMeta.put(name, classInformation);
						}
					}
				}
				if ("@attributes".equals(fieldname)) {
					jp.nextToken();
					String name = null;
					while (jp.nextToken() != JsonToken.END_OBJECT) {
						if (jp.getCurrentToken().equals(JsonToken.FIELD_NAME)) {
							name = jp.getText();
							jp.nextToken();
							// find class information
							final MetaAttribute attribute = context.metaAttribute(name);
							final ClassInformation classInformation = attribute != null ? attribute.valueClass() : extraMeta.get(name);
							// load attribute
							Object value = null;
							if (classInformation != null) {
								value = jp.readValueAs(classInformation.getType());
							} else {
								logger.warn("No class information on attribute '{}'", name);
								value = jp.readValueAs(Object.class);
							}
							attributes.put(name, value);
						}
					}
				}
			}
			return identity != null ? new EntityDynamic(identity, attributes, context) : new EntityDynamic(References.newReference(EntityDynamic.class), attributes, context);
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

	/**
	 * ReferenceSerializer.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	public static class ReferenceSerializer extends StdSerializer<Reference> {

		public ReferenceSerializer() {
			super(Reference.class);
		}

		@Override
		public void serialize(final Reference value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
			if (value != null) {
				jgen.writeString(value.toString());
			} else {
				jgen.writeNull();
			}
		}
	}

	/**
	 * 
	 * ReferenceDeSerializer.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	public static class ReferenceDeSerializer extends StdDeserializer<Reference> {

		/**
		 * serialVersionUID:long
		 */
		private static final long serialVersionUID = 1916647538058301330L;

		public ReferenceDeSerializer() {
			super(Reference.class);
		}

		@Override
		public Reference deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
			if (jp.hasCurrentToken()) {
				final String value = jp.getText();
				if (value != null) {
					return Reference.parseString(value);
				}
			}
			return null;
		}
	}
}
