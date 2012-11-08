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
package com.intelligentsia.dowsers.entity.store;

import junit.framework.Assert;

import org.junit.Test;

import com.intelligentsia.dowsers.entity.model.Person;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.reference.References;
import com.intelligentsia.dowsers.entity.store.fs.FileEntityStore;
import com.intelligentsia.dowsers.entity.store.memory.InMemoryEntityStore;

/**
 * <code>ShardingEntityStoreTest</code>.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 * 
 */
public class ShardingEntityStoreTest extends StoreBaseTest {

	protected EntityStore personStore;
	protected EntityStore defaultEntityStore;

	@Override
	public EntityStore instanciateEntityStore() {
		personStore = new InMemoryEntityStore(entityMapper);
		defaultEntityStore = new FileEntityStore(FileEntityStoreTest.getRoot(), entityMapper);
		return ShardingEntityStore.builder().// person
				add(Reference.newReference(Person.class), personStore).build(defaultEntityStore);
	}

	@Test
	public void testSharding() {
		final Person mario = getMario();
		final Reference id = References.identify(mario);
		// create on sharing
		entityStore.store(mario);
		// find it in person
		personStore.find(Person.class, id);
		// not found on default
		try {
			defaultEntityStore.find(Person.class, id);
			Assert.fail();
		} catch (final EntityNotFoundException e) {
			// ok
		}
	}

}
