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

import org.intelligentsia.dowsers.core.reflection.ClassInformation;
import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * ClassInformationTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class ClassInformationTest {

	@Test
	public void testAnalyze() {
		ClassInformation information = new ClassInformation(new String());
		assertNotNull(information);
		assertEquals(String.class, information.getType());
		assertTrue(information.getGenericClass().size() == 0);
	}

	@Test
	public void testAnalyze1() {
		ClassInformation information = new ClassInformation(new StringList());
		assertNotNull(information);
		assertEquals(StringList.class, information.getType());
		assertTrue(information.getGenericClass().size() == 1);
		assertEquals(String.class, information.getGenericClass().get(0));
	}

	@Test
	public void testAnalyze2() {
		ClassInformation information = new ClassInformation(new StringListFinal());
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
}
