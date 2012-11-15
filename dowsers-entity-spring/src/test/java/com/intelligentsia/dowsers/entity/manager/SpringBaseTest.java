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
package com.intelligentsia.dowsers.entity.manager;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.Resource;

import com.intelligentsia.dowsers.entity.model.Person;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.reference.References;
import com.intelligentsia.dowsers.entity.store.EntityNotFoundException;

public abstract class SpringBaseTest {

	protected DefaultListableBeanFactory registry = null;
	protected XmlBeanDefinitionReader reader = null;

	@Before
	public void initialize() {
		registry = new DefaultListableBeanFactory();
		reader = new XmlBeanDefinitionReader(registry);

	}

	@Test
	public void loadConfiguration() {
		reader.loadBeanDefinitions(getResource());
		final EntityManager entityManager = registry.getBean("entityManager", EntityManager.class);
		assertNotNull(entityManager);
		testCRUD(entityManager);
	}

	public abstract Resource getResource();

	public final void testCRUD(final EntityManager entityManager) {
		final Person mario = getMario(entityManager);
		assertEquals("Mario", mario.getFirstName());
		assertEquals("Fusco", mario.getLastName());
		assertEquals((Integer) 35, mario.getYearOld());
		final Reference id = References.identify(mario);
		// create
		entityManager.store(mario);
		assertEquals("Mario", mario.getFirstName());
		assertEquals("Fusco", mario.getLastName());
		assertEquals((Integer) 35, mario.getYearOld());
		// Read
		Person person = entityManager.find(Person.class, id);
		assertNotNull(person);
		assertEquals(id, References.identify(person));
		assertEquals(mario.getFirstName(), person.getFirstName());
		assertEquals(mario.getLastName(), person.getLastName());
		assertEquals(mario.getYearOld(), person.getYearOld());
		// Update
		mario.setFirstName("Bob");
		assertNotSame(mario.getFirstName(), person.getFirstName());
		entityManager.store(mario);
		person = entityManager.find(Person.class, id);
		assertEquals(mario.getFirstName(), person.getFirstName());
		assertEquals(mario.getLastName(), person.getLastName());
		assertEquals(mario.getYearOld(), person.getYearOld());
		// delete
		entityManager.remove(mario);
		try {
			entityManager.find(Person.class, id);
			fail();
		} catch (final EntityNotFoundException e) {
			// ok
		}
	}

	public final Person getMario(final EntityManager entityManager) {
		final Person person = entityManager.newInstance(Person.class);
		person.setFirstName("Mario");
		person.setLastName("Fusco");
		person.setYearOld(35);
		return person;
	}

}
