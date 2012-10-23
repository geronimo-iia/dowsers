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