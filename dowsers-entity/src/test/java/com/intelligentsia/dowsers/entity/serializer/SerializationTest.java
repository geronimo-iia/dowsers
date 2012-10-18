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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.intelligentsia.dowsers.core.reflection.ClassInformation;
import org.intelligentsia.dowsers.core.serializers.JacksonSerializer;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intelligentsia.dowsers.entity.EntityDynamic;
import com.intelligentsia.dowsers.entity.EntityFactories;
import com.intelligentsia.dowsers.entity.EntityFactories.EntityFactory;
import com.intelligentsia.dowsers.entity.EntityMapper;
import com.intelligentsia.dowsers.entity.EntityProxy;
import com.intelligentsia.dowsers.entity.meta.MetaAttribute;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaEntityDefinition;
import com.intelligentsia.dowsers.entity.meta.MetaModel;
import com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity;

public class SerializationTest {

	private ObjectMapper mapper;

	@Before
	public void initialize() {
		mapper = JacksonSerializer.getMapper();
		mapper.registerModule(new EntityDowsersJacksonModule());
	}

	@Test
	public void testEntityDynamicSerialization() throws JsonGenerationException, JsonMappingException, IOException {
		final StringWriter writer = new StringWriter();

		final EntityDynamic dynamic = new EntityDynamic("6787007f-f424-40b7-b240-64206b1177e2");
		dynamic.attribute("name", "Steve");
		dynamic.attribute("idea", "Sweet apple");
		assertEquals("Steve", dynamic.attribute("name"));
		assertEquals("Sweet apple", dynamic.attribute("idea"));

		mapper.writeValue(writer, dynamic);
		final String result = writer.toString();
		assertNotNull(result);
		assertEquals("{\"identity\":\"6787007f-f424-40b7-b240-64206b1177e2\",\"attributes\":{\"name\":\"Steve\",\"idea\":\"Sweet apple\"}}", result);

		final EntityDynamic entityDynamic = mapper.readValue(new StringReader(result), EntityDynamic.class);

		assertEquals(dynamic.attribute("name"), entityDynamic.attribute("name"));
		assertEquals(dynamic.attribute("idea"), entityDynamic.attribute("idea"));
	}

	@Test
	public void testProxySerialization() throws JsonGenerationException, JsonMappingException, IOException {
		final StringWriter writer = new StringWriter();

		final CustomizableSampleEntity sampleEntity = getCustomizableSampleEntity();

		mapper.writeValue(writer, sampleEntity);
		final String result = writer.toString();
		assertNotNull(result);
		assertEquals(
				"{\"interface\":\"com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity\",\"support-class\":\"com.intelligentsia.dowsers.entity.EntityDynamic\",\"entity\":{\"identity\":\"4c8b03dd-908a-4cad-8d48-3c7277d44ac9\",\"attributes\":{\"name\":\"Hello John\",\"description\":\"a blablablabalbablbalablabb\",\"order\":1}}}",
				result);

		final EntityProxy customizableSampleEntity = mapper.readValue(new StringReader(result), EntityProxy.class);
		assertEquals(sampleEntity.attribute("name"), customizableSampleEntity.attribute("name"));
		assertEquals(sampleEntity.attribute("description"), customizableSampleEntity.attribute("description"));
		assertEquals(sampleEntity.attribute("order"), customizableSampleEntity.attribute("order"));
	}

	@Test
	public void testEntityMapper() {
		final EntityMapper mapper = new EntityMapper();
		final CustomizableSampleEntity entity = getCustomizableSampleEntity();
		final StringWriter writer = new StringWriter();

		mapper.writeValue(writer, entity);
		final String result = writer.toString();
		System.err.println(result);

		final CustomizableSampleEntity entity2 = mapper.readValue(new StringReader(result), CustomizableSampleEntity.class);

		assertEquals(entity, entity2);

	}

	@Test
	public void testMetaAttribute() {
		final EntityMapper mapper = new EntityMapper();
		final StringWriter writer = new StringWriter();

		MetaEntity definition = new MetaEntityDefinition.Builder(). // definition
				name(MetaAttribute.class.getName()).version(MetaModel.VERSION)
				// identity
				.addMetaAttribute("identity", String.class)
				// name
				.addMetaAttribute("name", String.class)
				// value
				.addMetaAttribute("valueClass", ClassInformation.class)
				// default value
				.addMetaAttribute("defaultValue", Object.class).build();

		mapper.writeValue(writer, definition);
		final String result = writer.toString();
		System.err.println(result);
		// TODO finalize
	}

	protected CustomizableSampleEntity getCustomizableSampleEntity() {
		final EntityFactory<CustomizableSampleEntity> factory = EntityFactories.newEntityProxyDynamicFactory(CustomizableSampleEntity.class);

		final CustomizableSampleEntity sampleEntity = factory.newInstance("4c8b03dd-908a-4cad-8d48-3c7277d44ac9");
		assertNotNull(sampleEntity);
		sampleEntity.setName("Hello John");
		sampleEntity.setDescription("a blablablabalbablbalablabb");
		sampleEntity.attribute("order", 1);
		assertEquals("Hello John", sampleEntity.getName());
		assertEquals("a blablablabalbablbalablabb", sampleEntity.getDescription());
		assertEquals(1, sampleEntity.attribute("order"));
		return sampleEntity;
	}
}
