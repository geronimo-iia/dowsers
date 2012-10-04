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
/**
 * 
 */
package org.intelligentsia.dowsers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import junit.framework.Assert;

import org.intelligentsia.dowsers.annotation.Property;
import org.intelligentsia.dowsers.container.GenericDomainEntityFactory;
import org.intelligentsia.dowsers.domain.AggregateFactory;
import org.intelligentsia.dowsers.domain.DomainEntity;
import org.intelligentsia.dowsers.event.GenericDomainAggregateFactory;
import org.intelligentsia.dowsers.event.processor.CacheEventProcessorProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * DummyDomainEntityTest.
 * 
 * @author jgt
 * 
 */
public class DummyDomainEntityTest {
	private AggregateFactory aggregateFactory;
	GenericDomainEntityFactory defaultDomainEntityFactory;

	@Before
	public void initialize() {
		aggregateFactory = new GenericDomainAggregateFactory(new CacheEventProcessorProvider());
		defaultDomainEntityFactory = new GenericDomainEntityFactory(aggregateFactory);
	}

	@Test
	public void testProxyDynamic() throws IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setSuperclass(DomainEntity.class);
		proxyFactory.setFilter(new MethodFilter() {

			@Override
			public boolean isHandled(Method m) {
				return m.getName().startsWith("set") || m.getAnnotation(Property.class) != null;
			}
		});
		 
		MethodHandler handler = new MethodHandler() {
			@Override
			public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
				System.out.println("Handling " + thisMethod + " via the method handler");
				return null;
			}
		};
		
		//c.newInstance();
		@SuppressWarnings("unused")
		DomainEntity domainEntity  = (DomainEntity) proxyFactory.create(new Class<?>[] { AggregateFactory.class }, new Object[] { aggregateFactory }, handler); 
		
		//TestDomainEntity entity  = (TestDomainEntity) proxyFactory.create(new Class<?>[] { AggregateFactory.class }, new Object[] { aggregateFactory }, handler);
//		TestDomainEntity entity = 
//		
//		Assert.assertNotNull(entity);
//		
//		entity.setName("new name");
	}
	
	
	@Test
	public void testManagement() throws InstantiationException, IllegalAccessException {
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setSuperclass(TestDomainEntity.class);
		proxyFactory.setFilter(new MethodFilter() {

			@Override
			public boolean isHandled(Method m) {
				return m.getName().startsWith("set") || m.getAnnotation(Property.class) != null;
			}
		});
		
		
		MethodHandler handler = new MethodHandler() {
			@Override
			public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
				System.out.println("Handling " + thisMethod + " via the method handler");
				return null;
			}
		};
		Class<?> entityClass = proxyFactory.createClass();
		
		TestDomainEntity entity  = (TestDomainEntity) entityClass.newInstance();
		 ((ProxyObject) entity).setHandler(handler);
		
		Assert.assertNotNull(entity);
		
		entity.setName("new name");
	}
	
	
}
