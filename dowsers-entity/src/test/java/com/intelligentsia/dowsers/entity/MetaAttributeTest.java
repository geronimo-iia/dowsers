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
import org.intelligentsia.dowsers.core.reflection.ClassInformation;
import org.intelligentsia.keystone.api.artifacts.Version;
import org.junit.Test;

import com.intelligentsia.dowsers.entity.meta.MetaAttribute;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;

/**
 * MetaAttributeTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MetaAttributeTest {

	@Test
	public void testMetaAttributBuilderMandatory() {
		try {
			MetaAttribute.builder().build();
			fail();
		} catch (final NullPointerException exception) {
			// ok
		}
		try {
			MetaAttribute.builder().name("test-attribute").build();
			fail();
		} catch (final NullPointerException exception) {
			// ok
		}
		MetaAttribute.builder().name("test-attribute").valueClass(String.class).build();
	}

	@Test
	public void testMetaAttributBuilderWithoutDefaultValue() {
		final MetaAttribute attribute = MetaAttribute.builder().name("test-attribute").valueClass(String.class).build();
		assertNotNull(attribute);
		assertNotNull(attribute.identity());
		assertTrue(!"".equals(attribute.identity()));
		assertNotNull(attribute.name());
		assertEquals("test-attribute", attribute.name());
		assertNotNull(attribute.valueClass());
		assertEquals(new ClassInformation(String.class), attribute.valueClass());
		assertNull(attribute.defaultValue());

		// assertTrue(attribute.attributeNames().contains("identity"));
		assertTrue(attribute.attributeNames().contains("name"));
		assertTrue(attribute.attributeNames().contains("valueClass"));
		assertTrue(attribute.attributeNames().contains("defaultValue"));
	}

	@Test
	public void testMetaAttributBuilderWithDefaultValue() {

		final MetaAttribute attribute = MetaAttribute.builder().name("test-attribute").valueClass(String.class).defaultValue("a default value").build();
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
		final String id = IdentifierFactoryProvider.generateNewIdentifier();
		final MetaAttribute attribute = MetaAttribute.builder().identity(id).name("test-attribute").valueClass(String.class).build();
		assertNotNull(attribute.identity());
		assertEquals(id, attribute.identity());
	}

	@Test
	public void testMetaEntityBuilderMandatory() {
		try {
			MetaEntity.builder().build();
			fail();
		} catch (final NullPointerException exception) {
			// ok
		}
		try {
			MetaEntity.builder().name("test-attribute").build();
			fail();
		} catch (final NullPointerException exception) {
			// ok
		}
		MetaEntity.builder().name("test-attribute").version(new Version(1)).build();
	}
}
