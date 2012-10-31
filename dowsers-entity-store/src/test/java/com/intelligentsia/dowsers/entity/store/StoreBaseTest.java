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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

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
		factory = EntityFactories.newEntityProxyDynamicFactory(Person.class, MetaDataUtil.getMetaEntityContextProvider().find(Reference.newReference(Person.class)));
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

}
