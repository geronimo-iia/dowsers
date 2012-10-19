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

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import com.intelligentsia.dowsers.entity.EntityFactories.EntityFactory;
import com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity;

public class ReferenceFactoryTest {

	@Test
	public void testEntityAttributeReference() throws URISyntaxException {
		URI uri = Reference.newReference(getCustomizableSampleEntity(), "name");
		assertNotNull(uri);
		assertEquals("urn:dowsers:com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity:name#4c8b03dd-908a-4cad-8d48-3c7277d44ac9", uri.toString());
		assertEquals("com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity", Reference.getEntityPart(uri));
		assertEquals("name", Reference.getAttributPart(uri));
		assertEquals("4c8b03dd-908a-4cad-8d48-3c7277d44ac9", Reference.getIdentity(uri));
	}

	@Test
	public void testEntityReference() throws URISyntaxException {
		URI uri = Reference.newReference(getCustomizableSampleEntity());
		assertNotNull(uri);
		assertEquals("urn:dowsers:com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity:identity#4c8b03dd-908a-4cad-8d48-3c7277d44ac9", uri.toString());
		assertEquals("com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity", Reference.getEntityPart(uri));
		assertEquals("identity", Reference.getAttributPart(uri));
		assertEquals("4c8b03dd-908a-4cad-8d48-3c7277d44ac9", Reference.getIdentity(uri));
	}
	
	protected CustomizableSampleEntity getCustomizableSampleEntity() {
		final EntityFactory<CustomizableSampleEntity> factory = EntityFactories.newEntityProxyDynamicFactory(CustomizableSampleEntity.class);
		final CustomizableSampleEntity sampleEntity = factory.newInstance("4c8b03dd-908a-4cad-8d48-3c7277d44ac9");
		sampleEntity.setName("Hello John");
		sampleEntity.setDescription("a blablablabalbablbalablabb");
		sampleEntity.attribute("order", 1);
		return sampleEntity;
	}
}
