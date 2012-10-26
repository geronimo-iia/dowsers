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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.intelligentsia.dowsers.entity.EntityFactories.EntityFactory;
import com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity;
import com.intelligentsia.dowsers.entity.model.SampleEntity;
import com.intelligentsia.dowsers.entity.model.Util;

/**
 * EntityTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityTest {

	@Test
	public void testSampleEntity() {
		final EntityFactory<SampleEntity> factory = EntityFactories.newEntityProxyDynamicFactory(SampleEntity.class, Util.getMetaEntityContextProvider().find(SampleEntity.class));
		final SampleEntity sampleEntity = factory.newInstance();
		assertNotNull(sampleEntity);
		sampleEntity.setName("Hello John");
		sampleEntity.setDescription("a blablablabalbablbalablabb");
		assertEquals("Hello John", sampleEntity.getName());
		assertEquals("a blablablabalbablbalablabb", sampleEntity.getDescription());
	}

	@Test
	public void testCustomizableSampleEntity() {
		final EntityFactory<CustomizableSampleEntity> factory = EntityFactories.newEntityProxyDynamicFactory(CustomizableSampleEntity.class, Util.getMetaEntityContextProvider().find(CustomizableSampleEntity.class));
		final CustomizableSampleEntity sampleEntity = factory.newInstance();
		assertNotNull(sampleEntity);
		sampleEntity.setName("Hello John");
		
		sampleEntity.setDescription("a blablablabalbablbalablabb");
		sampleEntity.attribute("order", 1L);
		assertEquals("Hello John", sampleEntity.getName());
		assertEquals("a blablablabalbablbalablabb", sampleEntity.getDescription());
		assertEquals(1L, sampleEntity.attribute("order"));
	}

}
