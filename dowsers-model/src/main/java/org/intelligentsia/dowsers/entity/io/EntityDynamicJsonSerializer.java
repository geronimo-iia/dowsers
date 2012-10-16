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
import org.intelligentsia.dowsers.entity.EntityDynamicSupport;
import org.intelligentsia.dowsers.entity.meta.MetaAttribute;

/**
 * EntityDynamicJsonSerializer.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class EntityDynamicJsonSerializer extends SerializerBase<EntityDynamicSupport> {

	/**
	 * Build a new instance of EntityDynamicJsonSerializer.java.
	 */
	public EntityDynamicJsonSerializer() {
		super(EntityDynamicSupport.class);
	}

	@Override
	public void serialize(final EntityDynamicSupport value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
		jgen.writeStartObject();
		jgen.writeStringField("identity", value.identity());
		jgen.writeStringField("meta-entity-context-name", value.metaEntityContext().name());
		jgen.writeFieldName("attributes");
		jgen.writeStartObject();
		final Iterator<MetaAttribute> iterator = value.metaEntityContext().iterator();
		while (iterator.hasNext()) {
			final MetaAttribute metaAttribute = iterator.next();
			jgen.writeObjectField(metaAttribute.name(), value.attribute(metaAttribute.name()));
		}
		jgen.writeEndObject();
		jgen.writeEndObject();
	}

}
