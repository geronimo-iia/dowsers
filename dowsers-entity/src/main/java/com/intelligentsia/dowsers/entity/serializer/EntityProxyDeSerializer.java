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

import org.intelligentsia.dowsers.core.reflection.ClassInformation;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.EntityProxy;

/**
 * 
 * EntityProxyDeSerializer.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityProxyDeSerializer extends StdDeserializer<EntityProxy> {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -1025718595928596570L;

	public EntityProxyDeSerializer() {
		super(EntityProxy.class);
	}

	@Override
	public EntityProxy deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
		if (jp.hasCurrentToken()) {
			ClassInformation interfaceName = null;
			ClassInformation support = null;
			Entity entity = null;
			if (jp.getCurrentToken().equals(JsonToken.START_OBJECT)) {

				jp.nextToken();
				if (!jp.getCurrentToken().equals(JsonToken.FIELD_NAME) || !"@interface".equals(jp.getText())) {
					throw new JsonParseException("@interface attended", jp.getCurrentLocation());
				}
				jp.nextToken();
				interfaceName = ClassInformation.parse(jp.getText());
				jp.nextToken();
				if (!jp.getCurrentToken().equals(JsonToken.FIELD_NAME) || !"@support".equals(jp.getText())) {
					throw new JsonParseException("@support attended", jp.getCurrentLocation());
				}
				jp.nextToken();
				support = ClassInformation.parse(jp.getText());
				jp.nextToken();

				if (!jp.getCurrentToken().equals(JsonToken.FIELD_NAME) || !"@entity".equals(jp.getText())) {
					throw new JsonParseException("@entity attended", jp.getCurrentLocation());
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