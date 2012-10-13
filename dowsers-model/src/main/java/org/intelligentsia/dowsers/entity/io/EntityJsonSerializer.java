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
package org.intelligentsia.dowsers.entity.io;

import java.io.IOException;
import java.util.Iterator;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;
import org.intelligentsia.dowsers.entity.Entity;
import org.intelligentsia.dowsers.entity.meta.MetaAttribute;

public class EntityJsonSerializer extends SerializerBase<Entity> {

	public EntityJsonSerializer() {
		super(Entity.class);
	}

	@Override
	public void serialize(final Entity value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
		jgen.writeStartObject();
		jgen.writeStringField("identifier", value.identity());
		final Iterator<String> iterator = value.metaEntityContext().getMetaAttributeNames();
		while (iterator.hasNext()) {
			final MetaAttribute metaAttribute = value.metaEntityContext().getMetaAttribute(iterator.next());
			jgen.writeObjectField(metaAttribute.getName(), value.attribute(metaAttribute.getName()));
		}
		jgen.writeEndObject();
	}

}
