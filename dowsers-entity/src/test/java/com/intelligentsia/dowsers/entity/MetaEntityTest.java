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

import org.intelligentsia.dowsers.core.reflection.ClassInformation;
import org.intelligentsia.keystone.api.artifacts.Version;
import org.junit.Test;

import com.intelligentsia.dowsers.entity.meta.MetaAttributeCollection;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;

/**
 * MetaEntityTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MetaEntityTest {

	@Test
	public void testMetaEntityBuilder() {
		final MetaEntity definition = MetaEntity.builder().name("test").version(new Version(1)) // attributes
				.addMetaAttribute("test-attribute", String.class).build();

		assertNotNull(definition);
		assertNotNull(definition.identity());
		assertEquals("test", definition.name());
		assertEquals(new Version(1), definition.version());
		assertNotNull(definition.attribute("metaAttributes"));
		final MetaAttributeCollection metaAttributes = definition.attribute("metaAttributes");
		assertNotNull(metaAttributes);
		assertNotNull(metaAttributes.contains("test-attribute"));
		assertNotNull(metaAttributes.asList().get(0).identity());
		assertEquals("test-attribute", metaAttributes.asList().get(0).name());
		assertEquals(new ClassInformation(String.class), metaAttributes.asList().get(0).valueClass());
		assertNull(metaAttributes.asList().get(0).defaultValue());

		// assertTrue(definition.attributeNames().contains("identity"));
		assertTrue(definition.attributeNames().contains("name"));
		assertTrue(definition.attributeNames().contains("version"));
		assertTrue(definition.attributeNames().contains("metaAttributes"));

		assertTrue(definition.metaAttributeNames().contains("test-attribute"));
	}

}
