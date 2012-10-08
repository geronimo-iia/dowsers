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
package org.intelligentsia.dowsers.model;

import org.intelligentsia.dowsers.model.factory.BaseEntityFactorySupport;
import org.intelligentsia.dowsers.model.factory.EntityFactoryProxySupport;

/**
 * EntityTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityTest {
	
	private final EntityManagerUnit entityManagerUnit;

	/**
	 * @param args
	 */
	public static void main(final String[] args) {

		final EntityTest entityTest = new EntityTest();
		entityTest.testSampleEntity();
		entityTest.testCustomizableSampleEntity();
		entityTest.testSampleEntityMetaAware();
	}

	public EntityTest() {
		super();
		entityManagerUnit = new EntityManagerUnit(new EntityFactoryProxySupport(new BaseEntityFactorySupport(new MockMetaEntityContextRepository())));
	}

	public void testSampleEntity() {
		System.out.println("#----------------------------------------------------------------------------");
		System.out.println("# test SampleEntity");
		System.out.println("#----------------------");

		final SampleEntity sampleEntity = entityManagerUnit.newInstance(SampleEntity.class);
		sampleEntity.setName("Hello John");
		sampleEntity.setDescription("a blablablabalbablbalablabb");

		System.out.println(sampleEntity.getName());
		System.out.println(sampleEntity.getDescription());
		System.out.println("#----------------------------------------------------------------------------");
	}

	public void testCustomizableSampleEntity() {
		System.out.println("#----------------------------------------------------------------------------");
		System.out.println("# test CustomizableSampleEntity");
		System.out.println("#----------------------");
		final CustomizableSampleEntity sampleEntity = entityManagerUnit.newInstance(CustomizableSampleEntity.class);
		sampleEntity.setName("Hello John");
		sampleEntity.setDescription("a blablablabalbablbalablabb");
		sampleEntity.setProperty("order", 1L);

		System.out.println(sampleEntity.getName());
		System.out.println(sampleEntity.getDescription());
		System.out.println(sampleEntity.getProperty("order"));

		System.out.println("#----------------------------------------------------------------------------");
	}

	public void testSampleEntityMetaAware() {
		//
		System.out.println("#----------------------------------------------------------------------------");
		System.out.println("# test SampleEntityMetaAware");
		System.out.println("#----------------------");
		final SampleEntityMetaAware sampleEntity = entityManagerUnit.newInstance(SampleEntityMetaAware.class);
		sampleEntity.setName("Hello John");
		sampleEntity.setDescription("a blablablabalbablbalablabb");
		sampleEntity.setProperty("order", 1L);

		System.out.println(sampleEntity.getName());
		System.out.println(sampleEntity.getDescription());
		System.out.println(sampleEntity.getProperty("order"));

		System.out.println("Access on MetaEntityContext: " + sampleEntity.getMetaEntityContext().getName());

		System.out.println("#----------------------------------------------------------------------------");
	}
}
