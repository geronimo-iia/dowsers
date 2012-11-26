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
package com.intelligentsia.dowsers.entity.reference;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ServiceLoader;

import org.intelligentsia.dowsers.core.serializers.JacksonSerializer;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.intelligentsia.dowsers.entity.model.Person;
import com.intelligentsia.dowsers.entity.serializer.EntityDowsersJacksonModule.ReferenceDeSerializer;
import com.intelligentsia.dowsers.entity.serializer.EntityDowsersJacksonModule.ReferenceSerializer;

/**
 * <code>ReferenceTest</code>.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 * 
 */
public class ReferenceTest {

	@Test
	public void testReferenceFactoryProviderReference() {
		final ServiceLoader<ReferenceFactory> loader = ServiceLoader.load(ReferenceFactory.class, Thread.currentThread().getContextClassLoader());
		assertTrue(loader.iterator().hasNext());
		final Reference urn = new Reference(Person.class);
		assertEquals("com.intelligentsia.dowsers.entity.model.Person", urn.getEntityClassName());
	}

	@Test
	public void testReferenceClass() {
		final Reference reference = new Reference(Person.class);
		assertNotNull(reference);
		assertEquals("com.intelligentsia.dowsers.entity.model.Person", reference.getEntityClassName());
		assertEquals("", reference.getAttributeName());
		assertNull(reference.getIdentity());
		assertEquals("urn:dowsers:com.intelligentsia.dowsers.entity.model.Person:", reference.toString());
		try {
			new Reference(null);
			fail();
		} catch (final NullPointerException exception) {
			// ok
		}
	}

	@Test
	public void testReferenceInstance() {
		try {
			new Reference(null, null);
			fail();
		} catch (final NullPointerException exception) {
			// ok
		}
		try {
			new Reference(null, "Donald");
			fail();
		} catch (final NullPointerException exception) {
			// ok
		}
		try {
			new Reference(null, "");
			fail();
		} catch (final NullPointerException exception) {
			// ok
		}
		try {
			new Reference(Person.class, null);
			fail();
		} catch (final NullPointerException exception) {
			// ok
		}
		try {
			new Reference(Person.class, "");
			fail();
		} catch (final IllegalArgumentException exception) {
			// ok
		}
		final Reference reference = new Reference(Person.class, "Donald");
		assertNotNull(reference);
		assertEquals("com.intelligentsia.dowsers.entity.model.Person", reference.getEntityClassName());
		assertEquals("identity", reference.getAttributeName());
		assertEquals("Donald", reference.getIdentity());
		assertEquals("urn:dowsers:com.intelligentsia.dowsers.entity.model.Person:identity#Donald", reference.toString());
	}

	@Test
	public void testReferenceInstanceOnAttribute() {
		try {
			new Reference(null, null, null);
			fail();
		} catch (final NullPointerException exception) {
			// ok
		}

		final Reference reference = new Reference(Person.class, null, null);
		assertNotNull(reference);
		assertEquals("com.intelligentsia.dowsers.entity.model.Person", reference.getEntityClassName());
		assertEquals("", reference.getAttributeName());
		assertNull(reference.getIdentity());

		Reference nameReference = new Reference(Person.class, "name", null);
		assertNotNull(nameReference);
		assertEquals("com.intelligentsia.dowsers.entity.model.Person", nameReference.getEntityClassName());
		assertEquals("name", nameReference.getAttributeName());
		assertNull(nameReference.getIdentity());

		nameReference = new Reference(Person.class, "name", "id");
		assertNotNull(nameReference);
		assertEquals("com.intelligentsia.dowsers.entity.model.Person", nameReference.getEntityClassName());
		assertEquals("name", nameReference.getAttributeName());
		assertEquals("id", nameReference.getIdentity());

		assertEquals(new Reference(Person.class), nameReference.getEntityClassReference());
	}

	@Test
	public void testParseStringFull() {
		try {
			Reference.parseString(null);
			fail();
		} catch (final NullPointerException exception) {
			// ok
		}
		try {
			Reference.parseString("");
			fail();
		} catch (final IllegalArgumentException exception) {
			// ok
		}
		try {
			Reference.parseString("nawaknawaknawaknawaknawaknawak");
			fail();
		} catch (final IllegalArgumentException exception) {
			// ok
		}

		final String urn = "urn:dowsers:com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity:name#4c8b03dd-908a-4cad-8d48-3c7277d44ac9";
		final Reference reference = Reference.parseString(urn);
		assertNotNull(reference);
		assertEquals("com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity", reference.getEntityClassName());
		assertEquals("name", reference.getAttributeName());
		assertEquals("4c8b03dd-908a-4cad-8d48-3c7277d44ac9", reference.getIdentity());
	}

	@Test
	public void testParseStringForClass() {
		final String urn = "urn:dowsers:com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity:";
		final Reference reference = Reference.parseString(urn);
		assertNotNull(reference);
		assertEquals("com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity", reference.getEntityClassName());
	}
	
	
	
	@Test
	public void testParseURI() throws URISyntaxException {
		try {
			Reference.parseURI(null);
			fail();
		} catch (final NullPointerException exception) {
			// ok
		}
		try {
			Reference.parseURI(new URI(""));
			fail();
		} catch (final IllegalArgumentException exception) {
			// ok
		}
		try {
			Reference.parseURI(new URI("nawaknawaknawaknawaknawaknawak"));
			fail();
		} catch (final IllegalArgumentException exception) {
			// ok
		}
		URI uri = new URI("urn", "dowsers:com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity:name", "4c8b03dd-908a-4cad-8d48-3c7277d44ac9");
		Reference reference = Reference.parseURI(uri);
		assertNotNull(reference);
		assertEquals("com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity", reference.getEntityClassName());
		assertEquals("name", reference.getAttributeName());
		assertEquals("4c8b03dd-908a-4cad-8d48-3c7277d44ac9", reference.getIdentity());

		uri = new URI("urn:dowsers:com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity:name#4c8b03dd-908a-4cad-8d48-3c7277d44ac9");
		reference = Reference.parseURI(uri);
		assertNotNull(reference);
		assertEquals("com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity", reference.getEntityClassName());
		assertEquals("name", reference.getAttributeName());
		assertEquals("4c8b03dd-908a-4cad-8d48-3c7277d44ac9", reference.getIdentity());
	}

	@Test
	public void testEquals() {
		final Reference reference = new Reference(Person.class, "Donald");
		final Reference reference2 = new Reference(Person.class, "Donald");
		final Reference reference3 = new Reference(Person.class, "Donald Duck");

		assertEquals(reference, reference2);
		assertTrue(reference.equals(reference2));
		assertFalse(reference.equals(reference3));
	}

	@Test
	public void testCompare() {
		final Reference reference = new Reference(Person.class, "Donald");
		final Reference reference2 = new Reference(Person.class, "Donald Duck");

		assertEquals(reference.compareTo(reference2), "Donald".compareTo("Donald Duck"));
	}

	@Test
	public void testIsIdentifier() {
		Reference reference = new Reference(Person.class, "Donald");
		assertTrue(reference.isIdentifier());

		final String urn = "urn:dowsers:com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity:name#4c8b03dd-908a-4cad-8d48-3c7277d44ac9";
		reference = Reference.parseString(urn);
		assertTrue(!reference.isIdentifier());
	}

	@Test
	public void testSerialization() throws JsonParseException, JsonMappingException, IOException {

		final ObjectMapper mapper = JacksonSerializer.getMapper();
		final SimpleModule module = new SimpleModule();
		module.addSerializer(new ReferenceSerializer());
		module.addDeserializer(Reference.class, new ReferenceDeSerializer());
		mapper.registerModule(module);

		final StringWriter writer = new StringWriter();

		final Reference reference = Reference.parseString("urn:dowsers:com.intelligentsia.dowsers.entity.model.Person:identity#4c8b03dd-908a-4cad-8d48-3c7277d44ac9");
		mapper.writeValue(writer, reference);
		final String result = writer.toString();
		final Reference reference2 = mapper.readValue(new StringReader(result), Reference.class);
		assertNotNull(reference2);
		assertEquals(reference, reference2);
	}

}
