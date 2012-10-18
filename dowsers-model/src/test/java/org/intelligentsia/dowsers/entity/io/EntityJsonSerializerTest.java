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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.intelligentsia.dowsers.core.serializers.JacksonSerializer;
import org.intelligentsia.dowsers.entity.Entity;
import org.intelligentsia.dowsers.entity.MockMetaEntityContextRepository;
import org.intelligentsia.dowsers.entity.SampleEntity;
import org.intelligentsia.dowsers.entity.dynamic.EntityDynamicSupport;
import org.intelligentsia.dowsers.entity.io.serializer.EntityJsonModule;
import org.intelligentsia.dowsers.entity.meta.MetaEntityContextRepository;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * EntityJsonSerializerTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityJsonSerializerTest {

	@Test
	public void testEntitySerialization() throws JsonGenerationException, JsonMappingException, IOException {
		final MetaEntityContextRepository repository = new MockMetaEntityContextRepository();

		final Entity sampleEntity = new EntityDynamicSupport("1", repository.find(SampleEntity.class));
		sampleEntity.attribute("name", "a name");
		sampleEntity.attribute("description", "a description");

		final ObjectMapper mapper = JacksonSerializer.getMapper();
		mapper.registerModule(new EntityJsonModule(repository));

		final StringWriter writer = new StringWriter();
		mapper.writeValue(writer, sampleEntity);
		final String result = writer.toString();
		assertNotNull(result);
		assertEquals("{\"identity\":\"1\",\"meta-entity-context-name\":\"org.intelligentsia.dowsers.entity.SampleEntity\",\"attributes\":{\"name\":\"a name\",\"description\":\"a description\"}}", result);

		final EntityDynamicSupport dynamicSupport = mapper.readValue(new StringReader(result), EntityDynamicSupport.class);
		assertNotNull(dynamicSupport);
		assertEquals("a name", dynamicSupport.attribute("name"));
		assertEquals("a description", dynamicSupport.attribute("description"));
		assertEquals(SampleEntity.class.getName(), dynamicSupport.metaEntityContext().name());
	}

}
