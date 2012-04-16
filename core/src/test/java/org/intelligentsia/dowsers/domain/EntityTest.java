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
package org.intelligentsia.dowsers.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * EntityTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityTest {

	@Test
	public void testInstanciation() {
		try {
			new DummyEntity(null);
			fail("Expected NullPointerException");
		} catch (NullPointerException e) {
		}
		new DummyEntity("test");
	}

	@Test
	public void shoudlStore() {
		final String id = IdentifierFactoryProvider.generateNewIdentifier();
		final DummyEntity entity = new DummyEntity(id);
		assertEquals(id, entity.getIdentity());
	}

	@Test
	public void testEquals() {
		DummyEntity a = new DummyEntity(IdentifierFactoryProvider.generateNewIdentifier());
		DummyEntity ap = new DummyEntity(a.getIdentity());
		DummyEntity b = new DummyEntity(IdentifierFactoryProvider.generateNewIdentifier());

		assertEquals(a.getIdentity(), ap.getIdentity());
		assertTrue(a.equals(ap));
		assertTrue(ap.equals(a));

		assertNotSame(a.getIdentity(), b.getIdentity());
		assertTrue(!a.equals(b));
		assertTrue(!b.equals(a));
	}

	/**
	 * 
	 * DummyEntity.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 * 
	 */
	private class DummyEntity extends Entity {

		/**
		 * Build a new instance of DummyEntity.
		 */
		public DummyEntity(String id) {
			super(id);
		}

	}
}
