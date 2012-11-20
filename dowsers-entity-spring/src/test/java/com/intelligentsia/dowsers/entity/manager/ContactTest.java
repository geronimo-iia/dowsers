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

import static junit.framework.Assert.assertNotNull;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.model.Contact;

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

		Assert.assertNotNull(entityManager.newInstance(Contact.class));

	}

}
