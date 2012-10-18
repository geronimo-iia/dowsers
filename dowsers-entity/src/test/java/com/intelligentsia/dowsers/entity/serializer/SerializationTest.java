package com.intelligentsia.dowsers.entity.serializer;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.intelligentsia.dowsers.core.serializers.JacksonSerializer;
import org.junit.Before;
import org.junit.Test;

import com.intelligentsia.dowsers.entity.EntityDynamic;
import com.intelligentsia.dowsers.entity.EntityFactories;
import com.intelligentsia.dowsers.entity.EntityFactories.EntityFactory;
import com.intelligentsia.dowsers.entity.EntityProxy;
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

		EntityDynamic dynamic = new EntityDynamic("6787007f-f424-40b7-b240-64206b1177e2");
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

		final EntityFactory factory = EntityFactories.newEntityProxyDynamicFactory(CustomizableSampleEntity.class);

		final CustomizableSampleEntity sampleEntity = factory.newInstance();
		assertNotNull(sampleEntity);
		sampleEntity.setName("Hello John");
		sampleEntity.setDescription("a blablablabalbablbalablabb");
		sampleEntity.attribute("order", 1L);
		assertEquals("Hello John", sampleEntity.getName());
		assertEquals("a blablablabalbablbalablabb", sampleEntity.getDescription());
		assertEquals(1L, sampleEntity.attribute("order"));

		mapper.writeValue(writer, (EntityProxy)sampleEntity);
		final String result = writer.toString();
		assertNotNull(result);
		assertEquals("{\"identity\":\"6787007f-f424-40b7-b240-64206b1177e2\",\"attributes\":{\"name\":\"Steve\",\"idea\":\"Sweet apple\"}}", result);

		final CustomizableSampleEntity customizableSampleEntity = mapper.readValue(new StringReader(result), CustomizableSampleEntity.class);
		assertEquals(sampleEntity.attribute("name"), customizableSampleEntity.attribute("name"));
		assertEquals(sampleEntity.attribute("description"), customizableSampleEntity.attribute("idea"));
		assertEquals(sampleEntity.attribute("order"), customizableSampleEntity.attribute("idea"));
	}
}
