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
package com.intelligentsia.dowsers.entity.manager;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.model.Contact;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * ContactTest: {@link Contact} extends {@link Entity}
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class ContactTest {
	protected DefaultListableBeanFactory registry = null;
	protected XmlBeanDefinitionReader reader = null;

	@Before
	public void initialize() {
		registry = new DefaultListableBeanFactory();
		reader = new XmlBeanDefinitionReader(registry);

	}

	public Resource getResource() {
		return new ClassPathResource("dowsers-context-sample1.xml");
	}

	@Test
	public void loadConfiguration() {
		reader.loadBeanDefinitions(getResource());
		final EntityManager entityManager = registry.getBean("entityManager", EntityManager.class);
		assertNotNull(entityManager);

		Contact contact= entityManager.newInstance(Contact.class);
		assertNotNull(contact);
		Reference id = contact.identity();
		
		contact.setEmail("jguibert@intelligents-ia.com");
		contact.setPhoneNumber("123456");
		contact.setYearInteger(10);
		contact.setYearLong(666666L);
		contact.setBot(Boolean.TRUE);
		Date date = new Date();
		contact.dob(date);
		
		entityManager.store(contact);
		
		Contact contact2 = entityManager.find(Contact.class, id);
		assertNotNull(contact2);
		assertEquals(contact.getEmail(), contact2.getEmail());
		assertEquals(contact.getPhoneNumber(), contact2.getPhoneNumber());
		assertEquals(contact.getYearInteger(), contact2.getYearInteger());
		assertEquals(contact.getYearLong(), contact2.getYearLong());
		assertEquals(contact.isBot(), contact2.isBot());
		assertEquals(contact.dob(), contact2.dob());
		
	}

}
