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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import com.intelligentsia.dowsers.entity.EntityFactories.EntityFactory;
import com.intelligentsia.dowsers.entity.model.Person;
import com.intelligentsia.dowsers.entity.model.Util;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * <code>ReferenceTest</code>.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 * 
 */
public class ReferenceTest {

	@Test
	public void testGenerateReference() {
//		final ServiceLoader<ReferenceFactory> loader = ServiceLoader.load(ReferenceFactory.class, Thread.currentThread().getContextClassLoader());
//		assertTrue(loader.iterator().hasNext());
//		
		String urn = Reference.generateNewReference(Person.class);
		assertEquals("com.intelligentsia.dowsers.entity.model.Person", Reference.getEntityClassName(urn));
	}
	
	@Test
	public void testBeforeSimplifyReference() throws URISyntaxException {
		final String urn = "urn:dowsers:com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity:name#4c8b03dd-908a-4cad-8d48-3c7277d44ac9";
		final URI uri = new URI(urn);
		assertEquals(urn, uri.toString());
		
		assertEquals("urn:dowsers:com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity:name#4c8b03dd-908a-4cad-8d48-3c7277d44ac9", uri.toString());
		assertEquals("com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity", Reference.getEntityClassName(uri));
		assertEquals("name", Reference.getAttributeName(uri));
		assertEquals("4c8b03dd-908a-4cad-8d48-3c7277d44ac9", Reference.getIdentity(uri));
	}

	@Test
	public void testEntityAttributeReference() throws URISyntaxException {
		final String uri = Reference.newAttributeReference(Util.getCustomizableSampleEntity(), "name");
		assertNotNull(uri);
		assertEquals("urn:dowsers:com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity:name#4c8b03dd-908a-4cad-8d48-3c7277d44ac9", uri.toString());
		assertEquals("com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity", Reference.getEntityClassName(uri));
		assertEquals("name", Reference.getAttributeName(uri));
		assertEquals("4c8b03dd-908a-4cad-8d48-3c7277d44ac9", Reference.getIdentity(uri));
	}

	@Test
	public void testEntityReference() throws URISyntaxException {
		final String uri = Reference.newEntityReference(Util.getCustomizableSampleEntity());
		assertNotNull(uri);
		assertEquals("urn:dowsers:com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity:identity#4c8b03dd-908a-4cad-8d48-3c7277d44ac9", uri.toString());
		assertEquals("com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity", Reference.getEntityClassName(uri));
		assertEquals("identity", Reference.getAttributeName(uri));
		assertEquals("4c8b03dd-908a-4cad-8d48-3c7277d44ac9", Reference.getIdentity(uri));
	}

	@Test
	public void testProxyReference() throws IllegalArgumentException, URISyntaxException {
		final EntityFactory<Person> factory = EntityFactories.newEntityProxyDynamicFactory(Person.class, Util.getMetaEntityContextProvider().find(Person.class));
		final Person person = factory.newInstance("4c8b03dd-908a-4cad-8d48-3c7277d44ac9");
		person.setFirstName("Mario");
		person.setLastName("Fusco");
		person.setYearOld(35);

		final String uri = Reference.newEntityReference(person);
		assertNotNull(uri);
		assertEquals("urn:dowsers:com.intelligentsia.dowsers.entity.model.Person:identity#4c8b03dd-908a-4cad-8d48-3c7277d44ac9", uri.toString());
		assertEquals("com.intelligentsia.dowsers.entity.model.Person", Reference.getEntityClassName(uri));
		assertEquals("identity", Reference.getAttributeName(uri));
		assertEquals("4c8b03dd-908a-4cad-8d48-3c7277d44ac9", Reference.getIdentity(uri));
	}

	@Test
	public void testCollectionReference() throws URISyntaxException {
		final String uri = Reference.newEntityCollectionReference(Person.class);
		assertNotNull(uri);
		assertEquals("urn:dowsers:com.intelligentsia.dowsers.entity.model.Person:", uri.toString());
		assertEquals("com.intelligentsia.dowsers.entity.model.Person", Reference.getEntityClassName(uri));
		assertEquals("", Reference.getAttributeName(uri));
		assertNull(Reference.getIdentity(uri));

	}
}