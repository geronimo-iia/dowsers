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
import org.intelligentsia.dowsers.domain.AggregateFactory;
import org.intelligentsia.dowsers.domain.DefaultAggregateFactory;
import org.intelligentsia.dowsers.domain.DefaultDomainEntityFactory;
import org.intelligentsia.dowsers.domain.DomainEntity;
import org.intelligentsia.dowsers.events.processor.CacheEventProcessorProvider;
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
	DefaultDomainEntityFactory defaultDomainEntityFactory;

	@Before
	public void initialize() {
		aggregateFactory = new DefaultAggregateFactory(new CacheEventProcessorProvider());
		defaultDomainEntityFactory = new DefaultDomainEntityFactory(aggregateFactory);
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
