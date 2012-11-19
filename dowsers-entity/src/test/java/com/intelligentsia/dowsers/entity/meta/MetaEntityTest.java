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

import org.intelligentsia.dowsers.core.reflection.ClassInformation;
import org.intelligentsia.keystone.kernel.api.artifacts.Version;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * MetaEntityTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MetaEntityTest {

	@Test
	public void testMetaEntityBuilder() {
		final MetaEntity definition = MetaEntity.builder().name("test").version(new Version(1)) // attributes
				.metaAttribute("test-attribute", String.class).build();

		assertNotNull(definition);
		assertNotNull(definition.identity());
		assertEquals(new Reference(MetaEntity.class), definition.identity().getEntityClassReference());
		assertEquals("test", definition.name());
		assertEquals(new Version(1), definition.version());
		assertNotNull(definition.metaAttributes());
		final MetaAttributeCollection metaAttributes = definition.metaAttributes();
		assertNotNull(metaAttributes);
		assertNotNull(metaAttributes.getMetaAttributes().contains("test-attribute"));
		final ImmutableList<MetaAttribute> attributes = metaAttributes.getMetaAttributes().asList();
		assertNotNull(attributes.get(0).identity());
		assertEquals("test-attribute", attributes.get(0).name());
		assertEquals(new ClassInformation(String.class), attributes.get(0).valueClass());

	}

}
