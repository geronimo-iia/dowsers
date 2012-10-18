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

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;
import org.intelligentsia.dowsers.entity.meta.MetaAttributeDefinition;

/**
 * MetaAttributeDefinitionSerializer.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class MetaAttributeDefinitionSerializer extends SerializerBase<MetaAttributeDefinition> {

	public MetaAttributeDefinitionSerializer() {
		super(MetaAttributeDefinition.class);
	}

	@Override
	public void serialize(final MetaAttributeDefinition value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
		jgen.writeStartObject();
		jgen.writeStringField("name", value.name());
		jgen.writeObjectField("valueClass", value.valueClass());
		jgen.writeObjectField("defaultValue", value.defaultValue());
		jgen.writeEndObject();
	}

}
