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
import java.util.Date;

import org.intelligentsia.dowsers.core.reflection.ClassInformation;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.intelligentsia.dowsers.entity.meta.MetaAttribute;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import com.intelligentsia.dowsers.entity.meta.MetaModel;
import com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity;
import com.intelligentsia.dowsers.entity.model.MetaDataUtil;
import com.intelligentsia.dowsers.entity.reference.Reference;
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
		entityMapper = new EntityMapper(MetaDataUtil.getMetaEntityContextProvider());
	}

	@Test
	public void testEntityDynamicSerialization() throws JsonGenerationException, JsonMappingException, IOException {
		final StringWriter writer = new StringWriter();

		final EntityDynamic dynamic = new EntityDynamic(Reference.newReference(EntityDynamic.class, "6787007f-f424-40b7-b240-64206b1177e2"), MetaEntityContext.builder().definition(MetaModel.getMetaOfEntitydynamic()).build());
		dynamic.attribute("name", "Steve");
		dynamic.attribute("idea", "Sweet apple");
		dynamic.attribute("dob", new Date(1354100382746L));
		assertEquals("Steve", dynamic.attribute("name"));
		assertEquals("Sweet apple", dynamic.attribute("idea"));

		entityMapper.writeValue(writer, dynamic);
		final String result = writer.toString();
		assertNotNull(result);
		assertEquals("{\"" + //
				"@identity\":\"urn:dowsers:com.intelligentsia.dowsers.entity.EntityDynamic:identity#6787007f-f424-40b7-b240-64206b1177e2\"," + //
				"\"@meta\":{\"name\":{\"classInformation\":\"java.lang.String\"},\"idea\":{\"classInformation\":\"java.lang.String\"},\"dob\":{\"classInformation\":\"java.util.Date\"}}," + //
				"\"@attributes\":{" + //
				"\"name\":\"Steve\"," + //
				"\"idea\":\"Sweet apple\"," + //
				"\"dob\":1354100382746}}", result);

		final EntityDynamic entityDynamic = entityMapper.readValue(new StringReader(result), EntityDynamic.class);

		assertEquals(dynamic.identity(), entityDynamic.identity());
		assertEquals(dynamic.attribute("name"), entityDynamic.attribute("name"));
		assertEquals(dynamic.attribute("idea"), entityDynamic.attribute("idea"));
		assertEquals(dynamic.attribute("dob"), entityDynamic.attribute("dob"));
	}

	@Test
	public void testProxySerialization() throws JsonGenerationException, JsonMappingException, IOException {
		final StringWriter writer = new StringWriter();

		final CustomizableSampleEntity entity = MetaDataUtil.getCustomizableSampleEntity();

		entityMapper.writeValue(writer, entity);
		final String result = writer.toString();
		assertNotNull(result);
		assertEquals("{\"" + //
				"@interface\":\"com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity\"," + //
				"\"@support\":\"com.intelligentsia.dowsers.entity.EntityDynamic\"," + //
				"\"@entity\":{" + //
				"\"@identity\":\"urn:dowsers:com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity:identity#4c8b03dd-908a-4cad-8d48-3c7277d44ac9\"," + //
				"\"@attributes\":{" + //
				"\"name\":\"Hello John\"," + //
				"\"description\":\"a blablablabalbablbalablabb\"," + //
				"\"order\":1}}}", result);

		final Entity entity2 = entityMapper.readValue(new StringReader(result), EntityProxy.class);
		assertNotNull(entity2);
		assertEquals(entity.identity(), entity2.identity());
		assertEquals(entity.attribute("name"), entity2.attribute("name"));
		assertEquals(entity.attribute("description"), entity2.attribute("description"));
		assertEquals(entity.attribute("order"), entity2.attribute("order"));

	}

	@Test
	public void testEntityMapper() {
		final CustomizableSampleEntity entity = MetaDataUtil.getCustomizableSampleEntity();

		final StringWriter writer = new StringWriter();
		entityMapper.writeValue(writer, entity);
		final String result = writer.toString();

		final CustomizableSampleEntity entity2 = entityMapper.readValue(new StringReader(result), CustomizableSampleEntity.class);
		assertEquals(entity, entity2);
		assertEquals(MetaDataUtil.IDENTIFIER, entity2.identity());
		assertEquals(MetaDataUtil.NAME, entity2.getName());
		assertEquals(MetaDataUtil.DESCRIPTION, entity2.getDescription());
		assertEquals(1L, entity2.attribute(MetaDataUtil.ORDER));
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
		final MetaEntity entity = MetaEntity.builder(). // definition
				name(MetaAttribute.class.getName()).version(MetaModel.VERSION)
				// identity
				.metaAttribute("identity", String.class)
				// name
				.metaAttribute("name", String.class)
				// value
				.metaAttribute("valueClass", ClassInformation.class)
				// default value
				.metaAttribute("defaultValue", Object.class).build();
		entityMapper.writeValue(writer, entity);
		final String result = writer.toString();
		// System.err.println(result);
		final MetaEntity entity2 = entityMapper.readValue(new StringReader(result), MetaEntity.class);

		assertNotNull(entity);
		assertEquals(entity.identity(), entity2.identity());
		assertEquals(entity.name(), entity2.name());
		assertEquals(entity.version(), entity2.version());

		// TODO test attributes

	}

}
