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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.intelligentsia.dowsers.entity.EntityFactories.EntityFactory;
import com.intelligentsia.dowsers.entity.model.Person;
import com.intelligentsia.dowsers.entity.model.PersonDto;
import com.intelligentsia.dowsers.entity.model.Util;
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
		factory = EntityFactories.newEntityProxyDynamicFactory(Person.class);
		entityMapper = new EntityMapper(Util.getMetaEntityContextProvider().add(Person.class, Person.META));
	}

	@Test
	public void testInstanciation() {
		final Person person = factory.newInstance();
		person.setFirstName("Mario");
		person.setLastName("Fusco");
		person.setYearOld(35);
		assertEquals("Mario", person.getFirstName());
		assertEquals("Fusco", person.getLastName());
		assertEquals((Integer) 35, person.getYearOld());
	}

	@Test
	public void testReference() throws URISyntaxException {
		final Person person = factory.newInstance();
		person.setFirstName("Mario");
		person.setLastName("Fusco");
		person.setYearOld(35);
		assertEquals("Mario", person.getFirstName());
		assertEquals("Fusco", person.getLastName());
		assertEquals((Integer) 35, person.getYearOld());

		URI urn = Reference.newReference(person);
		assertNotNull(urn);
		assertEquals(Person.class.getName(), Reference.getEntityPart(urn));

	}

	@Test
	public void testSerialization() {
		/**
		 * <code>
		 * {
		 * 	"@interface":"com.intelligentsia.dowsers.entity.model.Person",
		 * 	"@support":"com.intelligentsia.dowsers.entity.EntityDynamic",
		 * 	"@entity":{
		 * 		"@reference":"urn:dowsers:com.intelligentsia.dowsers.entity.EntityDynamic:identity#4ca1ea7f-2dfe-4b8a-9008-97f3e30a36be",
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

		List<Person> meAndMyFriends = Lists.newArrayList(me, luca, biagio, celestino);

		// it is possible to filter the ones having more than 30 years applying
		// the following filter:

		List<Person> oldFriends = filter(having(on(Person.class).getYearOld(), Matchers.greaterThan(30)), meAndMyFriends);
		assertNotNull(oldFriends);
		assertTrue(oldFriends.contains(me));
		assertTrue(oldFriends.contains(biagio));
		assertTrue(!oldFriends.contains(luca));
		assertTrue(!oldFriends.contains(celestino));

		// sum
		int totalAge = sum(meAndMyFriends, on(Person.class).getYearOld());
		assertEquals((Integer) 35 + 29 + 39 + 29, totalAge);
		int totalAge2 = sumFrom(meAndMyFriends).getYearOld();
		assertEquals(totalAge, totalAge2);

		// projection
		List<PersonDto> personsDto = project(meAndMyFriends, PersonDto.class, on(Person.class).getFirstName(), on(Person.class).getYearOld());
		assertNotNull(oldFriends);
		assertEquals((Integer) 4, (Integer) personsDto.size());
	}
}
