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
package org.intelligentsia.dowsers.core.serializers;

import junit.framework.Assert;

import org.intelligentsia.dowsers.core.DummyObject;
import org.junit.Before;
import org.junit.Test;

/**
 * AbstractSerializerTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public abstract class AbstractSerializerTest {

	private Serializer<DummyObject> serializer;

	@Before
	public void setup() {
		serializer = createSerializer(DummyObject.class);
	}

	protected abstract <T> Serializer<T> createSerializer(Class<T> className);

	@Test
	public void testDummyObject() throws Exception {
		final DummyObject dummyObject = new DummyObject("John");
		dummyObject.getListName().add("test1");
		dummyObject.getListName().add("test2");
		final byte[] bytes = serializer.serialize(dummyObject);
		Assert.assertNotNull(bytes);
		Assert.assertTrue(bytes.length > 0);
		final DummyObject dummyObject2 = serializer.deserialize(bytes);
		Assert.assertNotNull(dummyObject2);
		Assert.assertEquals(dummyObject, dummyObject2);
		Assert.assertEquals(dummyObject.getName(), dummyObject2.getName());
		Assert.assertNotNull(dummyObject2.getListName());
		Assert.assertTrue(dummyObject.getListName().size() == dummyObject2.getListName().size());
	}

}
