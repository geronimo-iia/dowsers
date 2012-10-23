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
import java.net.URISyntaxException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.Reference;

/**
 * EntitySerializer.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class EntitySerializer<T extends Entity> extends StdSerializer<T> {

	public EntitySerializer(final Class<T> className) {
		super(className);
	}

	@Override
	public void serialize(final T value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
		jgen.writeStartObject();
		if (value != null) {
			try {
				jgen.writeObjectField("@reference", Reference.newReference(value));
			} catch (final URISyntaxException e) {
				throw new JsonGenerationException("Unable to build entity reference");
			}
			jgen.writeFieldName("@attributes");
			jgen.writeStartObject();
			final Iterator<String> iterator = value.attributeNames().iterator();
			while (iterator.hasNext()) {
				final String name = iterator.next();
				jgen.writeObjectField(name, value.attribute(name));
			}
			jgen.writeEndObject();
		}
		jgen.writeEndObject();
	}
}