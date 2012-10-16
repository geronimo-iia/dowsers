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
package org.intelligentsia.dowsers.entity.io.serializer;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdDeserializer;
import org.intelligentsia.dowsers.entity.EntityDynamicSupport;
import org.intelligentsia.dowsers.entity.meta.MetaEntityContextRepository;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
 * EntityDynamicJsonDeSerializer.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class EntityDynamicJsonDeSerializer extends StdDeserializer<EntityDynamicSupport> {

	private final MetaEntityContextRepository metaEntityContextRepository;

	/**
	 * Build a new instance of EntityJsonDeSerializer.java.
	 * 
	 * @param metaEntityContextRepository
	 *            {@link MetaEntityContextRepository} instance
	 * @throws NullPointerException
	 *             if metaEntityContextRepository is null
	 */
	public EntityDynamicJsonDeSerializer(final MetaEntityContextRepository metaEntityContextRepository) throws NullPointerException {
		super(EntityDynamicSupport.class);
		this.metaEntityContextRepository = Preconditions.checkNotNull(metaEntityContextRepository);
	}

	@Override
	public EntityDynamicSupport deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String identity = null;
		String metaEntityContextName = null;
		final Map<String, Object> attributes = Maps.newLinkedHashMap();

		if (jp.hasCurrentToken()) {
			if (jp.getCurrentToken().equals(JsonToken.START_OBJECT)) {
				// identity
				jp.nextValue();
				identity = jp.getText();
				// meta-entity-context-name
				jp.nextValue();
				metaEntityContextName = jp.getText();
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
		return new EntityDynamicSupport(identity, metaEntityContextRepository.find(metaEntityContextName), attributes);
	}

}
