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
package com.intelligentsia.dowsers.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.intelligentsia.dowsers.core.reflection.ClassInformation;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.intelligentsia.dowsers.entity.EntityFactories.EntityFactory;
import com.intelligentsia.dowsers.entity.meta.MetaAttribute;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaModel;
import com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity;
import com.intelligentsia.dowsers.entity.model.Util;
import com.intelligentsia.dowsers.entity.serializer.EntityMapper;

/**
 * SerializationTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class SerializationTest {

	private EntityMapper entityMapper;

	@Before
	public void initialize() {
		entityMapper = new EntityMapper(Util.getMetaEntityContextProvider());
	}

	@Test
	public void testEntityDynamicSerialization() throws JsonGenerationException, JsonMappingException, IOException {
		final StringWriter writer = new StringWriter();

		final EntityDynamic dynamic = new EntityDynamic("6787007f-f424-40b7-b240-64206b1177e2");
		dynamic.attribute("name", "Steve");
		dynamic.attribute("idea", "Sweet apple");
		assertEquals("Steve", dynamic.attribute("name"));
		assertEquals("Sweet apple", dynamic.attribute("idea"));

		entityMapper.writeValue(writer, dynamic);
		final String result = writer.toString();
		assertNotNull(result);
		assertEquals("{\"@reference\":\"urn:dowsers:com.intelligentsia.dowsers.entity.EntityDynamic:identity#6787007f-f424-40b7-b240-64206b1177e2\",\"@attributes\":{\"name\":\"Steve\",\"idea\":\"Sweet apple\"}}", result);

		final EntityDynamic entityDynamic = entityMapper.readValue(new StringReader(result), EntityDynamic.class);

		assertEquals(dynamic.identity(), entityDynamic.identity());
		assertEquals(dynamic.attribute("name"), entityDynamic.attribute("name"));
		assertEquals(dynamic.attribute("idea"), entityDynamic.attribute("idea"));
	}

	@Test
	public void testProxySerialization() throws JsonGenerationException, JsonMappingException, IOException {
		final StringWriter writer = new StringWriter();

		final CustomizableSampleEntity sampleEntity = getCustomizableSampleEntity();

		entityMapper.writeValue(writer, sampleEntity);
		final String result = writer.toString();
		assertNotNull(result);
		assertEquals("{\"@interface\":\"com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity\"," + //
				"\"@support\":\"com.intelligentsia.dowsers.entity.EntityDynamic\"," + //
				"\"@entity\":{\"@reference\":\"urn:dowsers:com.intelligentsia.dowsers.entity.EntityDynamic:identity#4c8b03dd-908a-4cad-8d48-3c7277d44ac9\"," + //
				"\"@attributes\":{\"name\":\"Hello John\",\"description\":\"a blablablabalbablbalablabb\",\"order\":1}}}", result);

		final EntityProxy customizableSampleEntity = entityMapper.readValue(new StringReader(result), EntityProxy.class);
		assertEquals(sampleEntity.identity(), customizableSampleEntity.identity());
		assertEquals(sampleEntity.attribute("name"), customizableSampleEntity.attribute("name"));
		assertEquals(sampleEntity.attribute("description"), customizableSampleEntity.attribute("description"));
		assertEquals(sampleEntity.attribute("order"), customizableSampleEntity.attribute("order"));
	}

	@Test
	public void testEntityMapper() {
		final CustomizableSampleEntity entity = getCustomizableSampleEntity();

		final StringWriter writer = new StringWriter();
		entityMapper.writeValue(writer, entity);
		final String result = writer.toString();

		final CustomizableSampleEntity entity2 = entityMapper.readValue(new StringReader(result), CustomizableSampleEntity.class);

		assertEquals(entity, entity2);
	}

	@Test
	public void testMetaAttribute() {
		final StringWriter writer = new StringWriter();
		final MetaAttribute attribute = MetaAttribute.builder().name(MetaAttribute.class.getName()).valueClass(String.class).build();
		entityMapper.writeValue(writer, attribute);
		final String result = writer.toString();
		final MetaAttribute attribute2 = entityMapper.readValue(new StringReader(result), MetaAttribute.class);
		assertNotNull(attribute2);
		assertEquals(attribute.identity(), attribute2.identity());
		assertEquals(attribute.name(), attribute2.name());
		assertEquals(attribute.valueClass(), attribute2.valueClass());
	}

	@Test
	public void testMetaEntityWithoutAttribut() {
		final StringWriter writer = new StringWriter();
		final MetaEntity entity = MetaEntity.builder().name(MetaAttribute.class.getName()).version(MetaModel.VERSION).build();
		entityMapper.writeValue(writer, entity);
		final String result = writer.toString();
		final MetaEntity entity2 = entityMapper.readValue(new StringReader(result), MetaEntity.class);
		assertNotNull(entity);
		assertEquals(entity.identity(), entity2.identity());
		assertEquals(entity.name(), entity2.name());
		assertEquals(entity.version(), entity2.version());
	}

	@Test
	public void testMetaEntity() {
		final StringWriter writer = new StringWriter();
		final MetaEntity definition = MetaEntity.builder(). // definition
				name(MetaAttribute.class.getName()).version(MetaModel.VERSION)
				// identity
				.addMetaAttribute("identity", String.class)
				// name
				.addMetaAttribute("name", String.class)
				// value
				.addMetaAttribute("valueClass", ClassInformation.class)
				// default value
				.addMetaAttribute("defaultValue", Object.class).build();
		entityMapper.writeValue(writer, definition);
		final String result = writer.toString();
		// System.err.println(result);
		entityMapper.readValue(new StringReader(result), MetaEntity.class);

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
