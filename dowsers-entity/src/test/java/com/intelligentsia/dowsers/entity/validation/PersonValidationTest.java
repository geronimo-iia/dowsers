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
package com.intelligentsia.dowsers.entity.validation;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;

import com.intelligentsia.dowsers.entity.EntityFactories;
import com.intelligentsia.dowsers.entity.EntityFactories.EntityFactory;
import com.intelligentsia.dowsers.entity.model.MetaDataUtil;
import com.intelligentsia.dowsers.entity.model.Person;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * 
 * PersonValidationTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class PersonValidationTest {

	private EntityFactory<Person> factory;

	@Before
	public void initialize() {
		factory = EntityFactories.newEntityProxyDynamicFactory(Person.class, MetaDataUtil.getMetaEntityContextProvider().find(Reference.newReference(Person.class)));
	}

	@Test
	public void testValidation() {
		final Person me = factory.newInstance();
		me.setFirstName("Mario");
		me.setLastName("Fusco");
		me.setYearOld(35);
		final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		final Set<ConstraintViolation<Person>> violations = validator.validate(me);
		assertTrue(violations.isEmpty());
	}
}
