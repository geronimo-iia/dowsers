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
package com.intelligentsia.dowsers.entity.view.processor;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.intelligentsia.dowsers.entity.EntityFactoryProvider;
import com.intelligentsia.dowsers.entity.manager.EntityManager;
import com.intelligentsia.dowsers.entity.manager.EntityManagerSupport;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProviderSupport;
import com.intelligentsia.dowsers.entity.model.Contact;
import com.intelligentsia.dowsers.entity.serializer.EntityMapper;
import com.intelligentsia.dowsers.entity.store.EntityStore;
import com.intelligentsia.dowsers.entity.store.memory.InMemoryEntityStore;

/**
 * ProcessorTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class ProcessorTest {

	@Test
	public void testBuilder() {
		assertEquals("(source (com.intelligentsia.dowsers.entity.model.Contact c))", ProcessorBuilder.builder(Contact.class, "c").build().toString());

		// bad way
		assertEquals("(projection (source (com.intelligentsia.dowsers.entity.model.Contact c)) identity email )", //
				ProcessorBuilder.builder(Contact.class, "c").projection("identity", "email").build().toString());
		// good way
		assertEquals("(source (com.intelligentsia.dowsers.entity.model.Contact c) identity email )", //
				ProcessorBuilder.builder(Contact.class, "c", "identity", "email").build().toString());

		// silly join
		assertEquals("(join (source (com.intelligentsia.dowsers.entity.model.Contact c) identity email ) c.identity (source (com.intelligentsia.dowsers.entity.model.Contact o)))", //
				ProcessorBuilder.builder(Contact.class, "c", "identity", "email")//
						.join(getEntityManager(), "c.identity", Contact.class, ProcessorBuilder.builder(Contact.class, "o").build()).build().toString());
	}

	public EntityManager getEntityManager() {
		final MetaEntityContextProviderSupport metaEntityContextProvider = MetaEntityContextProviderSupport.builder().build();
		final EntityMapper entityMapper = new EntityMapper();
		entityMapper.initialize(metaEntityContextProvider);
		final EntityStore entityStore = new InMemoryEntityStore(entityMapper);
		return new EntityManagerSupport(EntityFactoryProvider.builder().enableDefaultFactory().build(metaEntityContextProvider), entityStore);
	}
}
