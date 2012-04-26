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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.intelligentsia.dowsers.core.DummyObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * PerformanceTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class PerformanceTest {
	private static Logger logger = LoggerFactory.getLogger(PerformanceTest.class);

	private static final int COUNT = 1000;
	private static final DummyObject[] objects = new DummyObject[COUNT];

	@BeforeClass
	public static void setup() {
		logger.trace("Initializing ");
		for (int i = 0; i < COUNT; i++) {
			objects[i] = new DummyObject("coucou" + i, Lists.newArrayList("A", "B", "C", "D", "E"));
		}
		logger.trace("Done creating " + COUNT + " test objects");
		KryoSerializer.register(DummyObject.class);
	}

	@Test
	public void runMany() throws Exception {
		int count = 0;
		int round = 5;
		double std = 0d, kryo = 0d, jackson = 0d;

		while (++count <= round) {
			std += standardSerialization();
			kryo += KryoSerializableWrapper();
			jackson += jacksonSerializableWrapper();
		}

		logger.debug("Result {} ms ", round);
		logger.debug("standard time {} ms ", std / round);
		logger.debug("kryo time {} ms ", kryo / round);
		logger.debug("jackson time {} ms ", jackson / round);
	}

	public double standardSerialization() throws Exception {
		long before = System.nanoTime();
		for (int i = 0; i < COUNT; i++) {
			DummyObject dummyObject = objects[i];
			ByteArrayOutputStream baos = new ByteArrayOutputStream(500);
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(dummyObject);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			@SuppressWarnings("unused")
			DummyObject deserObj = (DummyObject) ois.readObject();
		}
		long after = System.nanoTime();
		logger.trace("standard time {} ", (after - before) / 1e6);
		return (after - before) / 1e6;
	}

	public double KryoSerializableWrapper() throws Exception {
		long before = System.nanoTime();
		for (int i = 0; i < COUNT; i++) {
			DummyObject dummyObject = objects[i];
			ByteArrayOutputStream baos = new ByteArrayOutputStream(500);
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(new KryoSerializableWrapper(dummyObject));
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			@SuppressWarnings("unused")
			DummyObject deserObj = (DummyObject) ois.readObject();
		}
		long after = System.nanoTime();
		logger.trace("kryo time {} ", (after - before) / 1e6);
		return ((after - before) / 1e6);
	}

	public double jacksonSerializableWrapper() throws Exception {
		long before = System.nanoTime();
		for (int i = 0; i < COUNT; i++) {
			DummyObject dummyObject = objects[i];
			ByteArrayOutputStream baos = new ByteArrayOutputStream(500);
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(new JacksonSerializableWrapper(dummyObject));
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			@SuppressWarnings("unused")
			DummyObject deserObj = (DummyObject) ois.readObject();
		}
		long after = System.nanoTime();
		logger.trace("jackson time {} ", (after - before) / 1e6);
		return (after - before) / 1e6;
	}
}
