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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.intelligentsia.dowsers.entity.model.Person;
import com.intelligentsia.dowsers.entity.model.ViewFactory;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.view.InMemoryViewStore;
import com.intelligentsia.dowsers.entity.view.View;
import com.intelligentsia.dowsers.entity.view.ViewManager;
import com.intelligentsia.dowsers.entity.view.ViewManagerControler;
import com.intelligentsia.dowsers.entity.view.ViewManagerControler.Behavior;
import com.intelligentsia.dowsers.entity.view.processor.Item;

/**
 * 
 * ViewManagerTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class ViewManagerTest {

	protected DefaultListableBeanFactory registry = null;
	protected XmlBeanDefinitionReader reader = null;

	@Before
	public void initialize() {
		registry = new DefaultListableBeanFactory();
		reader = new XmlBeanDefinitionReader(registry);

	}

	public Resource getResource() {
		return new ClassPathResource("dowsers-context-view.xml");
	}

	@Test
	public void checkConfig() {
		ViewFactory.viewPerson().entities().get(0).equals(Reference.newReferenceOnEntityClass(Person.class));
	}

	@Test
	public void loadViewConfiguration() {
		reader.loadBeanDefinitions(getResource());
		final EntityManager entityManager = registry.getBean("entityManager", EntityManager.class);
		assertNotNull(entityManager);

		ViewManager viewManager = registry.getBean(ViewManager.class);
		assertNotNull(viewManager);

		// check config
		Collection<View> views = viewManager.getViews(Reference.newReferenceOnEntityClass(Person.class));
		assertNotNull(viewManager);
		assertFalse(views.isEmpty());
		View view = views.iterator().next();
		assertNotNull(view);
		InMemoryViewStore store = (InMemoryViewStore) view.viewStore();
		assertNotNull(store);

		// store person
		Person person = getMario(entityManager);
		assertNotNull(person);
		entityManager.store(person);

		// check view
		assertFalse(store.items().isEmpty());
		Item item = store.items().iterator().next();
		assertNotNull(item);
		assertEquals(person.identity(), item.get("identity"));
		assertEquals(null, item.get("email"));
		assertEquals(person.getFirstName(), item.get("firstName"));
		assertEquals(person.getLastName(), item.get("lastName"));

		// clear all
		store.drop();
		assertTrue(store.items().isEmpty());

		//rebuild
		ViewManagerControler controler = new ViewManagerControler(entityManager, viewManager, Behavior.FOREGROUND);
		controler.process();

		// check view
		assertFalse(store.items().isEmpty());
		item = store.items().iterator().next();
		assertNotNull(item);
		assertEquals(person.identity(), item.get("identity"));
		assertEquals(null, item.get("email"));
		assertEquals(person.getFirstName(), item.get("firstName"));
		assertEquals(person.getLastName(), item.get("lastName"));

	}

	public final Person getMario(final EntityManager entityManager) {
		final Person person = entityManager.newInstance(Person.class);
		person.setFirstName("Mario");
		person.setLastName("Fusco");
		person.setYearOld(35);
		person.setEmail("mario@bross.com");
		return person;
	}

}
