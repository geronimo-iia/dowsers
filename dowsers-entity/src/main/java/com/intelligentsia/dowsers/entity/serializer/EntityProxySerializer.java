package com.intelligentsia.dowsers.entity.serializer;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.intelligentsia.dowsers.entity.EntityProxy;

/**
 * EntityProxySerializer.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
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
			jgen.writeObjectField("@interface", entityProxy.getInterfaceName().getName());
			jgen.writeObjectField("@support", entityProxy.getEntity().getClass().getName());
			jgen.writeObjectField("@entity", entityProxy.getEntity());
		}
		jgen.writeEndObject();
	}
}