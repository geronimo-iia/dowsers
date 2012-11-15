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
package com.intelligentsia.dowsers.entity.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;

import com.intelligentsia.dowsers.entity.EntityFactories;
import com.intelligentsia.dowsers.entity.EntityFactories.EntityFactory;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProvider;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProviderSupport;
import com.intelligentsia.dowsers.entity.meta.provider.MetaEntityProviders;
import com.intelligentsia.dowsers.entity.model.Organization;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.reference.References;
import com.intelligentsia.dowsers.entity.serializer.EntityMapper;
import com.intelligentsia.dowsers.entity.store.EntityNotFoundException;
import com.intelligentsia.dowsers.entity.store.EntityStore;
import com.intelligentsia.dowsers.entity.store.memory.InMemoryEntityStore;

/**
 * <code>OrganizationDemoTest</code>.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class OrganizationDemoTest {

	protected MetaEntityContextProvider metaEntityContextProvider;

	protected EntityFactory<Organization> factory;

	protected EntityMapper entityMapper;

	protected EntityStore entityStore;

	@Before
	public void initialize() {

		entityMapper = new EntityMapper();

		metaEntityContextProvider = newMetaEntityContextProvider();

		entityMapper.initialize(metaEntityContextProvider);

		entityStore = new InMemoryEntityStore(entityMapper);

		factory = EntityFactories.newEntityProxyDynamicFactory(Organization.class, metaEntityContextProvider.find(Reference.newReference(Organization.class)));

	}

	protected MetaEntityContextProviderSupport newMetaEntityContextProvider() {
		return MetaEntityContextProviderSupport.builder()//
				.add(Organization.class, Organization.META)//
				.build(MetaEntityProviders.newMetaEntityProviderAnalyzer());
	}

	protected Organization newOrganization() {
		final Organization organization = factory.newInstance();
		organization.name("Intelligents-ia");
		return organization;
	}

	@Test
	public void testInstanciation() {
		final Organization organization = newOrganization();
		assertEquals("Intelligents-ia", organization.name());
	}

	@Test
	public void testReference() {
		final Organization organization = newOrganization();
		final Reference urn = References.identify(organization);
		assertNotNull(urn);
		assertEquals(Reference.newReference(Organization.class), urn.getEntityClassReference());
	}

	@Test
	public void testSerialization() {
		final Organization organization = newOrganization();

		final StringWriter writer = new StringWriter();
		entityMapper.writeValue(writer, organization);
		final String result = writer.toString();
		assertNotNull(result);

		final Organization organization2 = entityMapper.readValue(new StringReader(result), Organization.class);

		assertEquals(organization.name(), organization2.name());
		for (final String name : organization.attributeNames()) {
			assertEquals(organization.attribute(name), organization2.attribute(name));
		}

	}

	@Test
	public void testCRUD() {
		final Organization organization = newOrganization();
		assertEquals("Intelligents-ia", organization.name());

		final Reference id = References.identify(organization);
		// create
		entityStore.store(organization);
		assertEquals("Intelligents-ia", organization.name());
		// Read
		Organization organization2 = entityStore.find(Organization.class, id);
		assertNotNull(organization2);
		assertEquals(id, References.identify(organization2));
		assertEquals(organization.name(), organization2.name());
		for (final String name : organization.attributeNames()) {
			assertEquals(organization.attribute(name), organization2.attribute(name));
		}
		// Update
		organization.name("Bob");
		assertNotSame(organization.name(), organization2.name());
		entityStore.store(organization);
		organization2 = entityStore.find(Organization.class, id);
		assertEquals(organization.name(), organization2.name());
		for (final String name : organization.attributeNames()) {
			assertEquals(organization.attribute(name), organization2.attribute(name));
		}
		// delete
		entityStore.remove(organization);
		try {
			entityStore.find(Organization.class, id);
			fail();
		} catch (final EntityNotFoundException e) {
			// ok
		}
	}
}
