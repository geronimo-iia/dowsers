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
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import org.intelligentsia.dowsers.core.IdentifierFactoryProvider;
import org.intelligentsia.dowsers.core.ReadOnlyIterator;
import org.intelligentsia.dowsers.core.reflection.ClassInformation;
import org.intelligentsia.keystone.api.artifacts.Version;
import org.junit.Test;

import com.google.common.collect.ImmutableCollection;
import com.intelligentsia.dowsers.entity.meta.MetaAttribute;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity;

/**
 * BuilderTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class BuilderTest {

	@Test
	public void testMetaAttributBuilderMandatory() {
		try {
			MetaAttribute.builder().build();
			fail();
		} catch (NullPointerException exception) {
			// ok
		}
		try {
			MetaAttribute.builder().name("test-attribute").build();
			fail();
		} catch (NullPointerException exception) {
			// ok
		}
		MetaAttribute.builder().name("test-attribute").valueClass(String.class).build();
	}

	@Test
	public void testMetaAttributBuilderWithoutDefaultValue() {
		MetaAttribute attribute = MetaAttribute.builder().name("test-attribute").valueClass(String.class).build();
		assertNotNull(attribute);
		assertNotNull(attribute.identity());
		assertTrue(!"".equals(attribute.identity()));
		assertNotNull(attribute.name());
		assertEquals("test-attribute", attribute.name());
		assertNotNull(attribute.valueClass());
		assertEquals(new ClassInformation(String.class), attribute.valueClass());
		assertNull(attribute.defaultValue());

		//assertTrue(attribute.attributeNames().contains("identity"));
		assertTrue(attribute.attributeNames().contains("name"));
		assertTrue(attribute.attributeNames().contains("valueClass"));
		assertTrue(attribute.attributeNames().contains("defaultValue"));
	}

	@Test
	public void testMetaAttributBuilderWithDefaultValue() {

		MetaAttribute attribute = MetaAttribute.builder().name("test-attribute").valueClass(String.class).defaultValue("a default value").build();
		assertNotNull(attribute);
		assertNotNull(attribute.identity());
		assertTrue(!"".equals(attribute.identity()));
		assertNotNull(attribute.name());
		assertEquals("test-attribute", attribute.name());
		assertNotNull(attribute.valueClass());
		assertEquals(new ClassInformation(String.class), attribute.valueClass());
		assertNotNull(attribute.defaultValue());
		assertEquals("a default value", attribute.defaultValue());
	}

	@Test
	public void testMetaAttributBuilderWithIdentitySet() {
		String id = IdentifierFactoryProvider.generateNewIdentifier();
		MetaAttribute attribute = MetaAttribute.builder().identity(id).name("test-attribute").valueClass(String.class).build();
		assertNotNull(attribute.identity());
		assertEquals(id, attribute.identity());
	}

	@Test
	public void testMetaEntityBuilderMandatory() {
		try {
			MetaEntity.builder().build();
			fail();
		} catch (NullPointerException exception) {
			// ok
		}
		try {
			MetaEntity.builder().name("test-attribute").build();
			fail();
		} catch (NullPointerException exception) {
			// ok
		}
		MetaEntity.builder().name("test-attribute").version(new Version(1)).build();
	}

	@Test
	public void testMetaEntityBuilder() {
		MetaEntity definition = MetaEntity.builder().name("test").version(new Version(1)) // attributes
				.addMetaAttribute("test-attribute", String.class).build();

		assertNotNull(definition);
		assertNotNull(definition.identity());
		assertEquals("test", definition.name());
		assertEquals(new Version(1), definition.version());
		assertNotNull(definition.attribute("metaAttributes"));
		ImmutableCollection<MetaAttribute> metaAttributes = definition.attribute("metaAttributes");
		assertNotNull(metaAttributes);
		assertNotNull(metaAttributes.contains("test-attribute"));
		assertNotNull(metaAttributes.asList().get(0).identity());
		assertEquals("test-attribute", metaAttributes.asList().get(0).name());
		assertEquals(new ClassInformation(String.class), metaAttributes.asList().get(0).valueClass());
		assertNull(metaAttributes.asList().get(0).defaultValue());

		//assertTrue(definition.attributeNames().contains("identity"));
		assertTrue(definition.attributeNames().contains("name"));
		assertTrue(definition.attributeNames().contains("version"));
		assertTrue(definition.attributeNames().contains("metaAttributes"));

		assertTrue(definition.metaAttributeNames().contains("test-attribute"));
	}

	@Test
	public void testMetaEntityContextBuilder() {
		final MetaEntityContext context = MetaEntityContext.builder() // definition
				.definition(new MetaEntity.Builder().name(CustomizableSampleEntity.class.getName()).version(new Version(1))// attributes
						.addMetaAttribute("name", String.class, null) //
						.addMetaAttribute("description", String.class).build()) // extension
				.addExtendedDefinition(new MetaEntity.Builder().name("test-extended").version(new Version(2)). // attributes
						addMetaAttribute("order", Long.class, 1L).build()).build();

		assertNotNull(context);
		assertEquals(CustomizableSampleEntity.class.getName(), context.name());
		assertEquals(new Version(1), context.version());

		// definition name
		assertTrue(context.definitionAttributeNames().contains("name"));
		assertTrue(context.definitionAttributeNames().contains("description"));
		assertTrue(!context.definitionAttributeNames().contains("order"));
		// extended
		assertTrue(!context.allExtendedAttributeNames().contains("name"));
		assertTrue(!context.allExtendedAttributeNames().contains("description"));
		assertTrue(context.allExtendedAttributeNames().contains("order"));

		// version
		assertTrue(context.containsVersion(new Version(1)));
		assertTrue(context.containsVersion(new Version(2)));

		// version iterator
		final ReadOnlyIterator<Version> versions = context.versions();
		assertTrue(versions.hasNext());
		assertEquals(new Version(1), versions.next());
		assertTrue(versions.hasNext());
		assertEquals(new Version(2), versions.next());
		assertTrue(!versions.hasNext());

		// meta attributes
		assertTrue(context.containsMetaAttribute("name"));
		assertTrue(context.containsMetaAttribute("description"));
		assertTrue(context.containsMetaAttribute("order"));

	}
}
