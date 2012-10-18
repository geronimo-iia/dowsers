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
package org.intelligentsia.dowsers.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.intelligentsia.dowsers.entity.factory.EntityFactoryDynamicSupport;
import org.intelligentsia.dowsers.entity.factory.EntityFactoryProxySupport;
import org.junit.Before;
import org.junit.Test;

/**
 * EntityTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityTest {

	private EntityManagerUnit entityManagerUnit;

	@Before
	public void init() {
		entityManagerUnit = new EntityManagerUnit(new EntityFactoryProxySupport(new EntityFactoryDynamicSupport(new MockMetaEntityContextRepository())));
	}

	@Test
	public void testSampleEntity() {

		final SampleEntity sampleEntity = entityManagerUnit.newInstance(SampleEntity.class);
		assertNotNull(sampleEntity);
		sampleEntity.setName("Hello John");
		sampleEntity.setDescription("a blablablabalbablbalablabb");

		assertEquals("Hello John", sampleEntity.getName());
		assertEquals("a blablablabalbablbalablabb", sampleEntity.getDescription());
	}

	@Test
	public void testCustomizableSampleEntity() {

		final CustomizableSampleEntity sampleEntity = entityManagerUnit.newInstance(CustomizableSampleEntity.class);
		assertNotNull(sampleEntity);
		sampleEntity.setName("Hello John");
		sampleEntity.setDescription("a blablablabalbablbalablabb");
		sampleEntity.attribute("order", 1L);

		assertEquals("Hello John", sampleEntity.getName());
		assertEquals("a blablablabalbablbalablabb", sampleEntity.getDescription());
		assertEquals(1L, sampleEntity.attribute("order"));

	}

	@Test
	public void testSampleEntityMetaAware() {

		final SampleEntityMetaAware sampleEntity = entityManagerUnit.newInstance(SampleEntityMetaAware.class);
		assertNotNull(sampleEntity);
		sampleEntity.setName("Hello John");
		sampleEntity.setDescription("a blablablabalbablbalablabb");
		sampleEntity.attribute("order", 1L);
		assertEquals("Hello John", sampleEntity.getName());
		assertEquals("a blablablabalbablbalablabb", sampleEntity.getDescription());
		assertEquals(1L, sampleEntity.attribute("order"));
		assertNotNull(sampleEntity.metaEntityContext());
		assertEquals(SampleEntityMetaAware.class.getName(), sampleEntity.metaEntityContext().name());
	}
}
