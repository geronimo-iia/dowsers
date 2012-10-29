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
package com.intelligentsia.dowsers.entity.meta;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import org.intelligentsia.dowsers.core.ReadOnlyIterator;
import org.intelligentsia.keystone.api.artifacts.Version;
import org.junit.Test;

import com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity;

public class MetaEntityContextTest {

	@Test
	public void testMetaEntityContextBuilder() {
		final MetaEntityContext context = MetaEntityContext.builder() // definition
				.definition(new MetaEntity.Builder().name(CustomizableSampleEntity.class.getName()).version(new Version(1))// attributes
						.metaAttribute("name", String.class) //
						.metaAttribute("description", String.class).build()) // extension
				.addExtendedDefinition(new MetaEntity.Builder().name("test-extended").version(new Version(2)). // attributes
						metaAttribute("order", Long.class).build()).build();

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
