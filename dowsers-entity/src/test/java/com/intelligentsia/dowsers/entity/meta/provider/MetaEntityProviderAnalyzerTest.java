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
package com.intelligentsia.dowsers.entity.meta.provider;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.Date;

import org.intelligentsia.dowsers.core.reflection.ClassInformation;
import org.intelligentsia.dowsers.core.reflection.Reflection;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.annotation.Attribute;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaModel;
import com.intelligentsia.dowsers.entity.model.Contact;
import com.intelligentsia.dowsers.entity.model.Person;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * MetaEntityProviderAnalyzerTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MetaEntityProviderAnalyzerTest {

	@Test
	public void testExtractMethodName() {
		assertEquals("firstName", MetaEntityProviderAnalyzer.extractName(Reflection.findMethod(A.class, "getFirstName")));
		assertEquals("firstName", MetaEntityProviderAnalyzer.extractName(Reflection.findMethod(A.class, "setFirstName")));

		assertEquals("lastName", MetaEntityProviderAnalyzer.extractName(Reflection.findMethod(A.class, "lastName")));
		assertEquals("ilastName", MetaEntityProviderAnalyzer.extractName(Reflection.findMethod(A.class, "IlastName")));
	}

	@Test
	public void testExtractValueClass() {
		assertEquals(ClassInformation.toClassInformation(String.class), //
				ClassInformation.toClassInformation(MetaEntityProviderAnalyzer.extractValueClass(Reflection.findMethod(A.class, "getFirstName"))));

		assertEquals(ClassInformation.toClassInformation(String.class), //
				ClassInformation.toClassInformation(MetaEntityProviderAnalyzer.extractValueClass(Reflection.findMethod(A.class, "lastName"))));

		assertEquals(ClassInformation.toClassInformation(String.class), //
				ClassInformation.toClassInformation(MetaEntityProviderAnalyzer.extractValueClass(Reflection.findMethod(A.class, "IlastName"))));

		assertEquals(ClassInformation.toClassInformation(String.class), //
				ClassInformation.toClassInformation(MetaEntityProviderAnalyzer.extractValueClass(Reflection.findMethod(A.class, "setFirstName"))));

	}

	@Test
	public void testHasAttributeSignature() {
		assertTrue(MetaEntityProviderAnalyzer.hasAttributeSignature(Reflection.findMethod(A.class, "getFirstName"), true));
		assertTrue(MetaEntityProviderAnalyzer.hasAttributeSignature(Reflection.findMethod(A.class, "lastName"), true));
		assertTrue(MetaEntityProviderAnalyzer.hasAttributeSignature(Reflection.findMethod(A.class, "IlastName"), true));
		assertTrue(MetaEntityProviderAnalyzer.hasAttributeSignature(Reflection.findMethod(A.class, "setFirstName"), true));
		assertFalse(MetaEntityProviderAnalyzer.hasAttributeSignature(Reflection.findMethod(A.class, "name"), true));
	}

	@Test
	public void testPersonAnalyze() {
		final MetaEntity metaEntity = MetaEntityProviderAnalyzer.analyze(ClassInformation.toClassInformation(Person.class));
		assertNotNull(metaEntity);
		assertEquals(Person.class.getName(), metaEntity.name());
		assertEquals(MetaModel.VERSION, metaEntity.version());
		assertNotNull(metaEntity.metaAttributes());
		assertFalse(metaEntity.metaAttributes().isEmpty());

		assertNotNull(metaEntity.metaAttributes().metaAttribute("identity"));

		assertNotNull(metaEntity.metaAttributes().metaAttribute("firstName"));
		assertEquals(ClassInformation.toClassInformation(String.class), metaEntity.metaAttributes().metaAttribute("firstName").valueClass());

		assertNotNull(metaEntity.metaAttributes().metaAttribute("lastName"));
		assertEquals(ClassInformation.toClassInformation(String.class), metaEntity.metaAttributes().metaAttribute("lastName").valueClass());

		assertNotNull(metaEntity.metaAttributes().metaAttribute("yearOld"));
		assertEquals(ClassInformation.toClassInformation(Integer.class), metaEntity.metaAttributes().metaAttribute("yearOld").valueClass());

	}

	@Test
	public void testContactAnalyze() {
		final MetaEntity metaEntity = MetaEntityProviderAnalyzer.analyze(ClassInformation.toClassInformation(Contact.class));
		assertNotNull(metaEntity);
		assertEquals(Contact.class.getName(), metaEntity.name());
		assertEquals(MetaModel.VERSION, metaEntity.version());
		assertNotNull(metaEntity.metaAttributes());
		assertFalse(metaEntity.metaAttributes().isEmpty());

		assertNotNull(metaEntity.metaAttributes().metaAttribute("identity"));

		assertNotNull(metaEntity.metaAttributes().metaAttribute("email"));
		assertEquals(ClassInformation.toClassInformation(String.class), metaEntity.metaAttributes().metaAttribute("email").valueClass());

		assertNotNull(metaEntity.metaAttributes().metaAttribute("belongingOrg"));
		assertEquals(ClassInformation.toClassInformation(Reference.class), metaEntity.metaAttributes().metaAttribute("belongingOrg").valueClass());

		assertNotNull(metaEntity.metaAttributes().metaAttribute("avatarUrl"));
		assertEquals(ClassInformation.toClassInformation(URL.class), metaEntity.metaAttributes().metaAttribute("avatarUrl").valueClass());

		assertNotNull(metaEntity.metaAttributes().metaAttribute("yearLong"));
		assertEquals(ClassInformation.toClassInformation(Long.class), metaEntity.metaAttributes().metaAttribute("yearLong").valueClass());

		assertNotNull(metaEntity.metaAttributes().metaAttribute("yearInteger"));
		assertEquals(ClassInformation.toClassInformation(Integer.class), metaEntity.metaAttributes().metaAttribute("yearInteger").valueClass());

		assertNotNull(metaEntity.metaAttributes().metaAttribute("bot"));
		assertEquals(ClassInformation.toClassInformation(Boolean.class), metaEntity.metaAttributes().metaAttribute("bot").valueClass());

		assertNotNull(metaEntity.metaAttributes().metaAttribute("dob"));
		assertEquals(ClassInformation.toClassInformation(Date.class), metaEntity.metaAttributes().metaAttribute("dob").valueClass());
	}

	@Test
	public void extractValueClassTest() {
		final Method method = Reflection.findMethod(B.class, "identity");
		assertNotNull("Method identity not found", method);
		final Class<?> result = MetaEntityProviderAnalyzer.extractValueClass(method);
		assertNotNull(result);
		assertEquals(ClassInformation.toClassInformation(Reference.class), ClassInformation.toClassInformation(result));
	}

	@Test
	public void testAttributeTypeOverride() {
		Method method = Reflection.findMethod(C.class, "name");
		assertNotNull("Method identity not found", method);
		Class<?> result = MetaEntityProviderAnalyzer.extractValueClass(method);
		assertNotNull(result);
		assertEquals(ClassInformation.toClassInformation(String.class), ClassInformation.toClassInformation(result));

		method = Reflection.findMethod(D.class, "name");
		assertNotNull("Method identity not found", method);
		result = MetaEntityProviderAnalyzer.extractValueClass(method);
		assertNotNull(result);
		assertEquals(ClassInformation.toClassInformation(String.class), ClassInformation.toClassInformation(result));
	}

	@Test
	public void testGenericAnalyze() {
		final Method method = Reflection.findMethod(C.class, "attributeNames");
		assertNotNull("Method identity not found", method);
		final Class<?> result = MetaEntityProviderAnalyzer.extractValueClass(method);
		assertNotNull(result);
		final ClassInformation reference = ClassInformation.parse(ImmutableSet.class.getName() + "<" + Object.class.getName() + ">");
		assertEquals(reference, ClassInformation.toClassInformation(result));
	}

	public interface C extends Entity {

		@Attribute(type = String.class)
		public Object name();

	}

	public interface D extends Entity {

		@Attribute(type = String.class)
		public void name(Object name);

	}

	public interface B extends Entity {

	}

	public interface A {

		String getFirstName();

		String lastName();

		String IlastName();

		void setFirstName(String name);

		void lastName(String name);

		void name(String first, String last);

	}

}
