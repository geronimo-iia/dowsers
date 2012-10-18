package com.intelligentsia.dowsers.entity.serializer;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.deser.std.StdDeserializer;
import org.codehaus.jackson.map.ser.std.SerializerBase;
import org.intelligentsia.dowsers.core.serializers.jackson.DowsersJacksonModule;

import com.google.common.collect.Maps;
import com.intelligentsia.dowsers.entity.EntityDynamic;
import com.intelligentsia.dowsers.entity.EntityProxy;

public class EntityDowsersJacksonModule extends DowsersJacksonModule {

	EntityNameResolvers.EntityNameResolver entityNameResolver;

	public EntityDowsersJacksonModule() {
		super();
		entityNameResolver = EntityNameResolvers.newEntityNameResolver();

		addSerializer(new EntityDynamicJsonSerializer());
		addSerializer(EntityProxy.class, new EntityProxySerializer());
		addDeserializer(EntityDynamic.class, new EntityDynamicJsonDeSerializer());

	}

	public class EntityProxySerializer extends SerializerBase<EntityProxy> {

		public EntityProxySerializer() {
			super(EntityProxy.class);
		}

		@Override
		public void serialize(final EntityProxy value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
			jgen.writeStartObject();
			jgen.writeStringField("interface-name", value.getInterfaceName().getName());
			jgen.writeStringField("entity-implementation-class-name", value.getEntity().getClass().getName());
			jgen.writeObjectField("entity", value.getEntity());
			jgen.writeEndObject();
		}
	}

	/**
	 * EntityDynamicJsonSerializer.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
	 */
	public class EntityDynamicJsonSerializer extends SerializerBase<EntityDynamic> {

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
