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
package org.intelligentsia.dowsers.entity.meta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.intelligentsia.keystone.api.artifacts.Version;
import org.junit.Test;

/**
 * MetaEntityDefinitionTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MetaEntityDefinitionTest {

	@Test
	public void testMetaEntityDefinitionFromCopy() {
		try {
			new MetaEntityDefinition(null);
			fail("NullPointerException attended");
		} catch (final NullPointerException exception) {
			// ok
		}
		final MetaEntityDefinition base = new MetaEntityDefinition("name", new Version(1), new ArrayList<MetaAttribute>());
		final MetaEntityDefinition definition = new MetaEntityDefinition(base);
		assertNotNull(definition);
		assertEquals(definition.getName(), base.getName());
		assertEquals(definition.getVersion(), base.getVersion());

	}

	@Test
	public void testMetaEntityDefinitionFromProperties() {
		try {
			new MetaEntityDefinition(null, new Version(1), new ArrayList<MetaAttribute>());
			fail("NullPointerException attended");
		} catch (final NullPointerException exception) {
			// ok
		}
		try {
			new MetaEntityDefinition("", new Version(1), new ArrayList<MetaAttribute>());
			fail("IllegalArgumentException attended");
		} catch (final IllegalArgumentException exception) {
			// ok
		}
		try {
			new MetaEntityDefinition("name", null, new ArrayList<MetaAttribute>());
			fail("NullPointerException attended");
		} catch (final NullPointerException exception) {
			// ok
		}
		try {
			new MetaEntityDefinition("name", new Version(1), null);
			fail("NullPointerException attended");
		} catch (final NullPointerException exception) {
			// ok
		}
		final MetaEntityDefinition definition = new MetaEntityDefinition("name", new Version(1), new ArrayList<MetaAttribute>());
		assertNotNull(definition);

		assertEquals("name", definition.getName());
		assertEquals(new Version(1), definition.getVersion());
	}

	@Test
	public void testMetaEntityDefinitionBuilder() {
		try {
			new MetaEntityDefinitionBuilder().build();
			fail("NullPointerException attended");
		} catch (final NullPointerException exception) {
			// ok
		}
		try {
			new MetaEntityDefinitionBuilder().setName("test").build();
			fail("NullPointerException attended");
		} catch (final NullPointerException exception) {
			// ok
		}

		new MetaEntityDefinitionBuilder().setName("test").setVersion(new Version(1)).build();

		final MetaEntityDefinition definition = new MetaEntityDefinitionBuilder().setName("test").setVersion(new Version(1)).add(new MetaAttributeDefinition("desc", String.class, "")).build();
		assertEquals("test", definition.getName());
		assertEquals(new Version(1), definition.getVersion());
		assertTrue(definition.getMetaAttributes().hasNext());
		final MetaAttribute metaAttribute = definition.getMetaAttribute("desc");
		assertNotNull(metaAttribute);

		assertEquals("desc", metaAttribute.getName());
		assertEquals(String.class, metaAttribute.getValueClass());
		assertEquals("", metaAttribute.getDefaultValue());
	}

}
