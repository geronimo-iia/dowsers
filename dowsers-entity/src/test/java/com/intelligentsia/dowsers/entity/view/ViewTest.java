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
package com.intelligentsia.dowsers.entity.view;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.intelligentsia.dowsers.entity.EntityFactoryProvider;
import com.intelligentsia.dowsers.entity.manager.EntityManager;
import com.intelligentsia.dowsers.entity.manager.EntityManagerSupport;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProvider;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProviderSupport;
import com.intelligentsia.dowsers.entity.meta.provider.MetaEntityProviders;
import com.intelligentsia.dowsers.entity.model.Contact;
import com.intelligentsia.dowsers.entity.model.Description;
import com.intelligentsia.dowsers.entity.model.Organization;
import com.intelligentsia.dowsers.entity.serializer.EntityMapper;
import com.intelligentsia.dowsers.entity.store.EntityStore;
import com.intelligentsia.dowsers.entity.store.memory.InMemoryEntityStore;
import com.intelligentsia.dowsers.entity.view.processor.Item;

/**
 * ViewTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class ViewTest {
	protected MetaEntityContextProvider metaEntityContextProvider;

	protected EntityManager entityManager;

	protected ViewManager viewManager;

	@Before
	public void initialize() {

		final EntityMapper entityMapper = new EntityMapper();
		metaEntityContextProvider = MetaEntityContextProviderSupport.builder().build(MetaEntityProviders.newMetaEntityProviderAnalyzer());
		entityMapper.initialize(metaEntityContextProvider);

		final EntityStore entityStore = new InMemoryEntityStore(entityMapper);

		final EntityFactoryProvider entityFactoryProvider = new EntityFactoryProvider(metaEntityContextProvider);

		entityManager = new EntityManagerSupport(entityFactoryProvider, entityStore, entityMapper);

	}

	@Test
	public void testInstanciateViewManagerEmpty() {
		viewManager = new ViewManager(new ArrayList<View>(), entityManager, false);
		assertNotNull(viewManager);
	}

	@Test
	public void testInstanciateViewManager() {
		final List<View> views = new ArrayList<View>();

		final View.Builder builder = View.builder().name("ContactView").viewStore(new InMemoryViewStore());
		builder.processor(Contact.class, "c", "identity", "email").build();
		final View view = builder.build();
		assertNotNull(view);
		views.add(view);

		assertEquals("ContactView", view.name());

		viewManager = new ViewManager(views, entityManager, false);
		assertNotNull(viewManager);
	}

	@Test
	public void testViewManager() {
		final List<View> views = new ArrayList<View>();
		final View view = View.builder().name("ContactView").viewStore(new InMemoryViewStore()).processor(Contact.class, "c", "identity", "email").build().build();
		views.add(view);
		viewManager = new ViewManager(views, entityManager, false);

		final Contact contact = entityManager.newInstance(Contact.class);
		assertNotNull(contact);

		contact.setEmail("jguibert@intelligents-ia.com");
		contact.setPhoneNumber("123456");
		contact.setYearInteger(10);
		contact.setYearLong(666666L);
		contact.setBot(Boolean.TRUE);
		final Date date = new Date();
		contact.dob(date);

		entityManager.store(contact);

		final InMemoryViewStore viewStore = (InMemoryViewStore) view.viewStore();

		assertTrue(!viewStore.items().isEmpty());

		final Item item = viewStore.items().iterator().next();
		assertNotNull(item);
		assertNotNull(item.getAttributes());
		assertEquals(contact.identity(), item.get("c.identity"));
		assertEquals(contact.getEmail(), item.get("c.email"));

	}

	@Test
	public void testViewWithSplitter() {
		final List<View> views = new ArrayList<View>();
		final View view = View.builder().name("OrganizationView").//
				viewStore(new InMemoryViewStore()).//
				processor(Organization.class, "c", "identity", "name", "annotation").build().//
				splitter("c.annotation"). //
				build();
		views.add(view);
		viewManager = new ViewManager(views, entityManager, false);

		final Organization organization = entityManager.newInstance(Organization.class);
		organization.name("Intelligents-ia");
		organization.annotation(new Description());
		organization.annotation().add(Locale.FRANCE, "une note").add(Locale.ENGLISH, "a note");

		entityManager.store(organization);

		final InMemoryViewStore viewStore = (InMemoryViewStore) view.viewStore();

		assertTrue(!viewStore.items().isEmpty());
		assertTrue(viewStore.items().size() == 2);

		// for(Item item : viewStore.items()) {
		// System.err.println(item);
		// }

	}

}
