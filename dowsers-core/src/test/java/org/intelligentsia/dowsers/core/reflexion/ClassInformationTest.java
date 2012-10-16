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
package org.intelligentsia.dowsers.core.reflexion;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.intelligentsia.dowsers.core.reflection.ClassInformation;
import org.junit.Test;

import com.google.common.io.Closeables;

/**
 * ClassInformationTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class ClassInformationTest {

	@Test
	public void testAnalyze() {
		final ClassInformation information = new ClassInformation(new String());
		assertNotNull(information);
		assertEquals(String.class, information.getType());
		assertTrue(information.getGenericClass().size() == 0);
	}

	@Test
	public void testAnalyze1() {
		final ClassInformation information = new ClassInformation(new StringList());
		assertNotNull(information);
		assertEquals(StringList.class, information.getType());
		assertTrue(information.getGenericClass().size() == 1);
		assertEquals(String.class, information.getGenericClass().get(0));
	}

	@Test
	public void testAnalyze2() {
		final ClassInformation information = new ClassInformation(new StringListFinal());
		assertNotNull(information);
		assertEquals(StringListFinal.class, information.getType());
		assertTrue(information.getGenericClass().size() == 2);
		assertEquals(Integer.class, information.getGenericClass().get(0));
		assertEquals(Long.class, information.getGenericClass().get(1));
	}

	@Test
	public void checkToString() {
		assertEquals("java.lang.String", new ClassInformation(new String()).toString());
		assertEquals("org.intelligentsia.dowsers.core.reflexion.StringList<java.lang.String>", new ClassInformation(new StringList()).toString());
		assertEquals("org.intelligentsia.dowsers.core.reflexion.StringListFinal<java.lang.Integer,java.lang.Long>", new ClassInformation(new StringListFinal()).toString());
	}

	@Test
	public void testParseSignature() {
		try {
			ClassInformation.parse(null);
			fail();
		} catch (final NullPointerException e) {
			// ok
		}
		try {
			ClassInformation.parse("");
			fail();
		} catch (final IllegalArgumentException e) {
			// ok
		}
	}

	@Test
	public void testParseAnalyze() {
		ClassInformation classInformation = ClassInformation.parse("java.lang.String");
		assertNotNull(classInformation);
		assertEquals("java.lang.String", classInformation.toString());
		assertTrue(classInformation.getGenericClass().isEmpty());

		classInformation = ClassInformation.parse("org.intelligentsia.dowsers.core.reflexion.StringList<java.lang.String>");
		assertNotNull(classInformation);
		assertTrue(!classInformation.getGenericClass().isEmpty());
		assertEquals("org.intelligentsia.dowsers.core.reflexion.StringList<java.lang.String>", classInformation.toString());
		assertEquals("org.intelligentsia.dowsers.core.reflexion.StringList", classInformation.getType().getName());
		assertEquals("java.lang.String", classInformation.getGenericClass().get(0).getName());

		classInformation = ClassInformation.parse("org.intelligentsia.dowsers.core.reflexion.StringListFinal<java.lang.Integer,java.lang.Long>");
		assertNotNull(classInformation);
		assertTrue(!classInformation.getGenericClass().isEmpty());
		assertEquals("org.intelligentsia.dowsers.core.reflexion.StringListFinal<java.lang.Integer,java.lang.Long>", classInformation.toString());
		assertEquals("org.intelligentsia.dowsers.core.reflexion.StringListFinal", classInformation.getType().getName());
		assertEquals("java.lang.Integer", classInformation.getGenericClass().get(0).getName());
		assertEquals("java.lang.Long", classInformation.getGenericClass().get(1).getName());

	}

	@Test
	public void testSerialization() throws IOException, ClassNotFoundException {
		final ClassInformation information = new ClassInformation(new StringList());

		final File file = File.createTempFile("classInformation", "");
		// serialize
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(information);
		} finally {
			Closeables.closeQuietly(oos);
		}
		// deserialize
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			final ClassInformation classInformation = (ClassInformation) ois.readObject();
			assertNotNull(classInformation);
			assertTrue(!classInformation.getGenericClass().isEmpty());
			assertEquals("org.intelligentsia.dowsers.core.reflexion.StringList<java.lang.String>", classInformation.toString());
			assertEquals("org.intelligentsia.dowsers.core.reflexion.StringList", classInformation.getType().getName());
			assertEquals("java.lang.String", classInformation.getGenericClass().get(0).getName());
		} finally {
			Closeables.closeQuietly(ois);
		}
	}

}
