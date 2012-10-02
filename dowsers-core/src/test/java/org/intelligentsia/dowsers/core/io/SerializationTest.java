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
package org.intelligentsia.dowsers.core.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.intelligentsia.dowsers.core.DummyObject;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * SerializationTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class SerializationTest {

	private Logger logger = LoggerFactory.getLogger(SerializationTest.class);
	
	private final DummyObject dummyObject = new DummyObject("coucou", Lists.newArrayList("A", "B", "C", "D", "E"));

	@Before
	public void setup() {
		KryoSerializer.register(DummyObject.class);
	}

	@Test
	public void standardJavaSerialization() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(500);
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(dummyObject);
		logger.trace("Standard Java size: " + baos.size());
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
		DummyObject deserObj = (DummyObject) ois.readObject();
		assertEquals(dummyObject, deserObj);
	}

	@Test
	public void standardJavaSerializationWithKryoSerializableWrapper() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(500);
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(new KryoSerializableWrapper(dummyObject));
		logger.trace("Standard Java with Kryo wrapper size: " + baos.size());
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
		DummyObject deserObj = (DummyObject) ois.readObject();
		assertEquals(dummyObject, deserObj);
	}

	@Test
	public void standardJavaSerializationWithJacksonSerializableWrapper() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(500);
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(new JacksonSerializableWrapper(dummyObject));
		logger.trace("Standard Java with Jackson wrapper size: " + baos.size());
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
		DummyObject deserObj = (DummyObject) ois.readObject();
		assertEquals(dummyObject, deserObj);
	}

	@Test
	public void kryoSerialization() throws Exception {
		byte[] bytes = KryoSerializer.write(dummyObject);
		logger.trace("Kryo size: " + bytes.length);
		DummyObject deserObj = (DummyObject) KryoSerializer.read(bytes);
		assertEquals(dummyObject, deserObj);
	}

	@Test
	public void JacksonSerialization() throws Exception {
		byte[] bytes = JacksonSerializer.write(dummyObject);
		logger.trace("Jackson size: " + bytes.length);
		DummyObject deserObj = (DummyObject) JacksonSerializer.read(bytes, DummyObject.class);
		assertEquals(dummyObject, deserObj);
	}
}
