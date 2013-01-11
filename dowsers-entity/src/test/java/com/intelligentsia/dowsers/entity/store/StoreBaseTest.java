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
package com.intelligentsia.dowsers.entity.store;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.intelligentsia.dowsers.entity.EntityFactories;
import com.intelligentsia.dowsers.entity.EntityFactories.EntityFactory;
import com.intelligentsia.dowsers.entity.model.MetaDataUtil;
import com.intelligentsia.dowsers.entity.model.Person;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.reference.References;
import com.intelligentsia.dowsers.entity.serializer.EntityMapper;

/**
 * <code>StoreBaseTest</code>.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 * 
 */
public abstract class StoreBaseTest {

	protected EntityFactory<Person> factory;
	protected EntityMapper entityMapper;
	protected EntityStore entityStore;

	@Before
	public void initialize() {
		factory = EntityFactories.newEntityProxyDynamicFactory(Person.class, MetaDataUtil.getMetaEntityContextProvider().find(Reference.newReferenceOnEntityClass(Person.class)));
		entityMapper = new EntityMapper(MetaDataUtil.getMetaEntityContextProvider());
		entityStore = instanciateEntityStore();
	}

	public abstract EntityStore instanciateEntityStore();

	public Person getMario() {
		final Person person = factory.newInstance();
		person.setFirstName("Mario");
		person.setLastName("Fusco");
		person.setYearOld(35);
		return person;
	}

	@Test
	public void testCRUD() {
		final Person mario = getMario();
		assertEquals("Mario", mario.getFirstName());
		assertEquals("Fusco", mario.getLastName());
		assertEquals((Integer) 35, mario.getYearOld());
		final Reference id = References.identify(mario);
		// create
		entityStore.store(mario);
		assertEquals("Mario", mario.getFirstName());
		assertEquals("Fusco", mario.getLastName());
		assertEquals((Integer) 35, mario.getYearOld());
		// Read
		Person person = entityStore.find(Person.class, id);
		assertNotNull(person);
		assertEquals(id, References.identify(person));
		assertEquals(mario.getFirstName(), person.getFirstName());
		assertEquals(mario.getLastName(), person.getLastName());
		assertEquals(mario.getYearOld(), person.getYearOld());
		// Update
		mario.setFirstName("Bob");
		assertNotSame(mario.getFirstName(), person.getFirstName());
		entityStore.store(mario);
		person = entityStore.find(Person.class, id);
		assertEquals(mario.getFirstName(), person.getFirstName());
		assertEquals(mario.getLastName(), person.getLastName());
		assertEquals(mario.getYearOld(), person.getYearOld());
		// delete
		entityStore.remove(mario);
		try {
			entityStore.find(Person.class, id);
			fail();
		} catch (final EntityNotFoundException e) {
			// ok
		}
	}

	@Test
	public void testIdentityHijack() {
		final Person mario = getMario();
		assertEquals("Mario", mario.getFirstName());
		assertEquals("Fusco", mario.getLastName());
		assertEquals((Integer) 35, mario.getYearOld());
		final Reference id = References.identify(mario);
		// create
		entityStore.store(mario);
		assertEquals("Mario", mario.getFirstName());
		assertEquals("Fusco", mario.getLastName());
		assertEquals((Integer) 35, mario.getYearOld());
		// Read
		Person person = entityStore.find(Person.class, id);
		assertNotNull(person);
		assertEquals(id, References.identify(person));
		assertEquals(mario.getFirstName(), person.getFirstName());
		assertEquals(mario.getLastName(), person.getLastName());
		assertEquals(mario.getYearOld(), person.getYearOld());
		// Update
		mario.setFirstName("Bob");
		assertNotSame(mario.getFirstName(), person.getFirstName());
		entityStore.store(mario);
		person = entityStore.find(Person.class, id);
		assertEquals(mario.getFirstName(), person.getFirstName());
		assertEquals(mario.getLastName(), person.getLastName());
		assertEquals(mario.getYearOld(), person.getYearOld());
		// delete
		entityStore.remove(mario);
		try {
			entityStore.find(Person.class, id);
			fail();
		} catch (final EntityNotFoundException e) {
			// ok
		}
	}

	@Test
	public void testCollection() {
		final Set<Reference> references = Sets.newHashSet();
		for (int i = 0; i < 2; i++) {
			final Person mario = getMario();
			references.add(References.identify(mario));
			entityStore.store(mario);
		}
		assertEquals(2, references.size());
		int count = 0;
		for (final Reference reference : entityStore.find(Person.class)) {
			assertTrue(references.contains(reference));
			count++;
		}
		assertEquals(2, count);
	}

	@Test
	public void testFindAndFilter() {
		entityStore.store(getMario("A"));
		entityStore.store(getMario("A"));
		entityStore.store(getMario("B"));
		entityStore.store(getMario("C"));
		// filter on lastName == A
		int count = 0;
		for (final Reference reference : entityStore.find(new Reference(Person.class, "lastName", "A"))) {
			assertTrue(entityStore.find(Person.class, reference).getLastName().equals("A"));
			count++;
		}
		assertEquals(2, count);
		// filter on lastName == B
		count = 0;
		for (final Reference reference : entityStore.find(new Reference(Person.class, "lastName", "B"))) {
			assertTrue(entityStore.find(Person.class, reference).getLastName().equals("B"));
			count++;
		}
		assertEquals(1, count);
		// filter on firstName == Mario
		count = 0;
		for (final Reference reference : entityStore.find(new Reference(Person.class, "firstName", "Mario"))) {
			assertTrue(entityStore.find(Person.class, reference).getFirstName().equals("Mario"));
			count++;
		}
		assertEquals(4, count);
	}

	public Person getMario(String lastName) {
		final Person person = factory.newInstance();
		person.setFirstName("Mario");
		person.setLastName(lastName);
		person.setYearOld(35);
		return person;
	}
}
