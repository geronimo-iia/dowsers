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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.Reference;
import com.intelligentsia.dowsers.entity.meta.MetaAttribute;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProvider;

/**
 * EntityDynamicDeSerializer.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class EntityDeSerializer<T extends Entity> extends StdDeserializer<T> {

	private static final long serialVersionUID = 7124116996885105048L;

	private final EntityDynamicFactory<T> factory;
	/**
	 * {@link MetaEntityContextProvider} instance.
	 */
	private final MetaEntityContextProvider metaEntityContextProvider;

	/**
	 * Build a new instance of EntityDowsersJacksonModule.java.
	 * 
	 * @param className
	 * @param factory
	 * @param metaEntityContextProvider
	 * @throws NullPointerException
	 *             if on of parameters is null
	 */
	public EntityDeSerializer(final Class<T> className, final EntityDynamicFactory<T> factory, final MetaEntityContextProvider metaEntityContextProvider) throws NullPointerException {
		super(className);
		this.factory = Preconditions.checkNotNull(factory);
		this.metaEntityContextProvider = Preconditions.checkNotNull(metaEntityContextProvider);
	}

	@Override
	public T deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
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
		return reference != null ? factory.newInstance(identity, attributes) : null;
	}

}