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

import static ch.lambdaj.Lambda.filter;
import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.project;
import static ch.lambdaj.Lambda.sum;
import static ch.lambdaj.Lambda.sumFrom;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.intelligentsia.dowsers.entity.EntityFactories.EntityFactory;
import com.intelligentsia.dowsers.entity.model.MetaDataUtil;
import com.intelligentsia.dowsers.entity.model.Person;
import com.intelligentsia.dowsers.entity.model.PersonDto;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.reference.References;
import com.intelligentsia.dowsers.entity.serializer.EntityMapper;

/**
 * 
 * PersonTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class PersonTest {

	private EntityFactory<Person> factory;
	private EntityMapper entityMapper;

	@Before
	public void initialize() {
		factory = EntityFactories.newEntityProxyDynamicFactory(Person.class, MetaDataUtil.getMetaEntityContextProvider().find(Reference.newReferenceOnEntityClass(Person.class)));
		entityMapper = new EntityMapper(MetaDataUtil.getMetaEntityContextProvider());
	}

	@Test
	public void testInstanciation() {
		final Person person = factory.newInstance();
		person.setFirstName("Mario");
		person.setLastName("Fusco");
		person.setYearOld(35);
		person.bot(Boolean.TRUE);
		assertEquals("Mario", person.getFirstName());
		assertEquals("Fusco", person.getLastName());
		assertEquals((Integer) 35, person.getYearOld());
		assertEquals(Boolean.TRUE, person.isBot());
	}

	@Test
	public void testReference() {
		final Person person = factory.newInstance();
		person.setFirstName("Mario");
		person.setLastName("Fusco");
		person.setYearOld(35);

		assertEquals("Mario", person.getFirstName());
		assertEquals("Fusco", person.getLastName());
		assertEquals((Integer) 35, person.getYearOld());

		final Reference urn = References.identify(person);
		assertNotNull(urn);
		assertEquals(Reference.newReferenceOnEntityClass(Person.class), urn.getEntityClassReference());
	}

	@Test
	public void testSerialization() {
		/**
		 * <code>
		 * {
		 * 	"@interface":"com.intelligentsia.dowsers.entity.model.Person",
		 * 	"@support":"com.intelligentsia.dowsers.entity.EntityDynamic",
		 * 	"@entity":{
		 * 		"@identity":"urn:dowsers:com.intelligentsia.dowsers.entity.model.Person:identity#4ca1ea7f-2dfe-4b8a-9008-97f3e30a36be",
		 * 		"@attributes":{
		 * 			"firstName":"Mario",
		 * 			"lastName":"Fusco",
		 * 			"yearOld":35}}}
		 * </code>
		 */
		final Person person = factory.newInstance();
		person.setFirstName("Mario");
		person.setLastName("Fusco");
		person.setYearOld(35);

		final StringWriter writer = new StringWriter();
		entityMapper.writeValue(writer, person);
		final String result = writer.toString();
		assertNotNull(result);

		final Person person2 = entityMapper.readValue(new StringReader(result), Person.class);

		assertEquals(person.getFirstName(), person2.getFirstName());
		assertEquals(person.getLastName(), person2.getLastName());
		assertEquals(person.getYearOld(), person2.getYearOld());

	}

	@Test
	public void testSelection() {
		final Person me = factory.newInstance();
		me.setFirstName("Mario");
		me.setLastName("Fusco");
		me.setYearOld(35);

		final Person luca = factory.newInstance();
		luca.setFirstName("Luca");
		luca.setLastName("Marrocco");
		luca.setYearOld(29);

		final Person biagio = factory.newInstance();
		biagio.setFirstName("Biagio");
		biagio.setLastName("Beatrice");
		biagio.setYearOld(39);

		final Person celestino = factory.newInstance();
		celestino.setFirstName("Celestino");
		celestino.setLastName("Bellone");
		celestino.setYearOld(29);

		final List<Person> meAndMyFriends = Lists.newArrayList(me, luca, biagio, celestino);

		// it is possible to filter the ones having more than 30 years applying
		// the following filter:

		final List<Person> oldFriends = filter(having(on(Person.class).getYearOld(), Matchers.greaterThan(30)), meAndMyFriends);
		assertNotNull(oldFriends);
		assertTrue(oldFriends.contains(me));
		assertTrue(oldFriends.contains(biagio));
		assertTrue(!oldFriends.contains(luca));
		assertTrue(!oldFriends.contains(celestino));

		// sum
		final int totalAge = sum(meAndMyFriends, on(Person.class).getYearOld());
		assertEquals(35 + 29 + 39 + 29, totalAge);
		final int totalAge2 = sumFrom(meAndMyFriends).getYearOld();
		assertEquals(totalAge, totalAge2);

		// projection
		final List<PersonDto> personsDto = project(meAndMyFriends, PersonDto.class, on(Person.class).getFirstName(), on(Person.class).getYearOld());
		assertNotNull(oldFriends);
		assertEquals((Integer) 4, (Integer) personsDto.size());
	}

	@Test
	public void testProxyReference() throws IllegalArgumentException {
		final Person person = factory.newInstance(Reference.newReference(Person.class, "4c8b03dd-908a-4cad-8d48-3c7277d44ac9"));
		final Reference uri = References.identify(person);
		assertNotNull(uri);
		assertEquals("urn:dowsers:com.intelligentsia.dowsers.entity.model.Person:identity#4c8b03dd-908a-4cad-8d48-3c7277d44ac9", uri.toString());
		assertEquals("com.intelligentsia.dowsers.entity.model.Person", uri.getEntityClassName());
		assertEquals("identity", uri.getAttributeName());
		assertEquals("4c8b03dd-908a-4cad-8d48-3c7277d44ac9", uri.getIdentity());
		assertTrue(uri.isIdentifier());
	}

}
