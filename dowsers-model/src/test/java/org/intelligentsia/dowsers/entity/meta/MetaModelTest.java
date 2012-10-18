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

import org.junit.Test;

/**
 * 
 * MetaModelTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class MetaModelTest {

	@Test
	public void testMetaMetaAttribute() {
		final MetaEntityContext context = MetaModel.getMetaAttributModel();
		assertNotNull(context);
		assertEquals(context.name(), MetaAttribute.class.getName());
		assertTrue(context.contains("identity"));
		assertTrue(context.contains("name"));
		assertTrue(context.contains("valueClass"));
		assertTrue(context.contains("defaultValue"));
	}

	@Test
	public void testMetaMetaEntity() {
		final MetaEntityContext context = MetaModel.getMetaEntityModel();
		assertNotNull(context);
		assertEquals(context.name(), MetaEntity.class.getName());
		assertTrue(context.contains("identity"));
		assertTrue(context.contains("name"));
		assertTrue(context.contains("version"));
		assertTrue(context.contains("metaAttributes"));
	}
}
