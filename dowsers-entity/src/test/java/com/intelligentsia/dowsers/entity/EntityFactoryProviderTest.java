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

import static junit.framework.Assert.*;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProviderSupport;
import com.intelligentsia.dowsers.entity.meta.MetaModel;
import com.intelligentsia.dowsers.entity.model.MetaDataUtil;
import com.intelligentsia.dowsers.entity.model.Person;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * EntityFactoryProviderTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityFactoryProviderTest {

	@Test
	public void testBuilder() {
		try {
			EntityFactoryProvider.builder().build(null);
			fail();
		} catch (NullPointerException exception) {
			// ok
		}
		assertNotNull(EntityFactoryProvider.builder().build(MetaDataUtil.getMetaEntityContextProvider()));
	}

	@Test
	public void testProxyEntity() {
		EntityFactoryProvider entityFactoryProvider = EntityFactoryProvider.builder().build(MetaDataUtil.getMetaEntityContextProvider());
		assertNotNull(entityFactoryProvider.newInstance(Person.class));
		try {
			entityFactoryProvider.newInstance(EntityFactoryProviderTest.class);
			fail();
		} catch (IllegalArgumentException argumentException) {
			// ok
		}
	}

	@Test
	public void testEntityDynamic() {
		EntityFactoryProvider entityFactoryProvider = EntityFactoryProvider.builder().build(MetaDataUtil.getMetaEntityContextProvider());
		assertNotNull(entityFactoryProvider.newInstance(EntityDynamic.class));
	}

	@Test
	public void testEntityRegistered() {

		final MetaEntityContext aContext = MetaEntityContext.builder().definition(A.META).build();
		EntityFactoryProvider entityFactoryProvider = EntityFactoryProvider.builder().//
				register(//
						A.class,//
						EntityFactories.newEntityProxyDynamicFactory(A.class, aContext)//
				).build(MetaEntityContextProviderSupport.builder().add(A.class, aContext).build());

		assertNotNull(entityFactoryProvider);
		assertNotNull(entityFactoryProvider.newInstance(A.class));
	}

	@Test
	public void testDefaultEntityFactoryFailureNotImplementEntity() {
		final MetaEntityContext aContext = MetaEntityContext.builder().definition(B.META).build();
		EntityFactoryProvider entityFactoryProvider = EntityFactoryProvider.builder().//
				register(//
						B.class,//
						EntityFactories.newEntityProxyDynamicFactory(B.class, aContext)//
				).build(MetaEntityContextProviderSupport.builder().add(B.class, aContext).build());

		assertNotNull(entityFactoryProvider);
		try {
			assertNotNull(entityFactoryProvider.newInstance(B.class));
			fail();
		} catch (IllegalArgumentException argumentException) {
			// ok
		}

	}

	@Test
	public void testDefaultEntityFactoryFailureHaveNoIdentityField() {
		final MetaEntityContext aContext = MetaEntityContext.builder().definition(BP.META).build();
		EntityFactoryProvider entityFactoryProvider = EntityFactoryProvider.builder().//
				register(//
						BP.class,//
						EntityFactories.newEntityProxyDynamicFactory(BP.class, aContext)//
				).build(MetaEntityContextProviderSupport.builder().add(BP.class, aContext).build());

		assertNotNull(entityFactoryProvider);
		try {
			assertNotNull(entityFactoryProvider.newInstance(BP.class));
			fail();
		} catch (NullPointerException argumentException) {
			// ok
		}

	}

	@Test
	public void testDefaultEntityFactory() {
		final MetaEntityContext aContext = MetaEntityContext.builder().definition(BPP.META).build();
		EntityFactoryProvider entityFactoryProvider = EntityFactoryProvider.builder().//
				register(//
						BPP.class,//
						EntityFactories.newEntityProxyDynamicFactory(BPP.class, aContext)//
				).build(MetaEntityContextProviderSupport.builder().add(BPP.class, aContext).build());

		assertNotNull(entityFactoryProvider);
		assertNotNull(entityFactoryProvider.newInstance(BPP.class));
		assertNotNull(entityFactoryProvider.newInstance(BPP.class).newInstance());

	}

	public static interface A {
		public static MetaEntity META = MetaEntity.builder().name(A.class.getName()).version(MetaModel.VERSION). //
				metaAttributes(MetaModel.getIdentityAttribute()).//
				metaAttribute("name", String.class).build();

		String name();
	}

	public static class B {
		public static MetaEntity META = MetaEntity.builder().name(B.class.getName()).version(MetaModel.VERSION). //
				metaAttributes(MetaModel.getIdentityAttribute()).//
				metaAttribute("name", String.class).build();

		private String name;

		public B() {
			super();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	public static class BP implements Entity {
		public static MetaEntity META = MetaEntity.builder().name(BP.class.getName()).version(MetaModel.VERSION). //
				metaAttributes(MetaModel.getIdentityAttribute()).//
				metaAttribute("name", String.class).build();

		private String name;

		public BP() {
			super();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public Reference identity() {
			return null;
		}

		@Override
		public <Value> Value attribute(String name) throws NullPointerException, IllegalArgumentException {
			return null;
		}

		@Override
		public <Value> Entity attribute(String name, Value value) throws NullPointerException, IllegalArgumentException {
			return null;
		}

		@Override
		public boolean contains(String name) throws NullPointerException, IllegalArgumentException {
			return false;
		}

		@Override
		public ImmutableSet<String> attributeNames() {
			return null;
		}

		@Override
		public MetaEntityContext metaEntityContext() {
			return null;
		}

	}

	public static class BPP implements Entity {
		public static MetaEntity META = MetaEntity.builder().name(BPP.class.getName()).version(MetaModel.VERSION). //
				metaAttributes(MetaModel.getIdentityAttribute()).//
				metaAttribute("name", String.class).build();

		private Reference identity;
		private String name;

		public BPP() {
			super();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public Reference identity() {
			return identity;
		}

		@Override
		public <Value> Value attribute(String name) throws NullPointerException, IllegalArgumentException {
			return null;
		}

		@Override
		public <Value> Entity attribute(String name, Value value) throws NullPointerException, IllegalArgumentException {
			return null;
		}

		@Override
		public boolean contains(String name) throws NullPointerException, IllegalArgumentException {
			return false;
		}

		@Override
		public ImmutableSet<String> attributeNames() {
			return null;
		}

		@Override
		public MetaEntityContext metaEntityContext() {
			return null;
		}

	}
}
