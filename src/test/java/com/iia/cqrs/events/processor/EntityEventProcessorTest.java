/**
 * 
 */
package com.iia.cqrs.events.processor;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * EntityEventProcessorTest.
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityEventProcessorTest {

	private EntityEventProcessor processor;

	@Before
	public void initialize() {
		processor = new EntityEventProcessor();
	}

	@Test
	public void testHasEventSignature() {
		for (String name : Arrays.asList("badReturnType", "wrongParameterNumber", "wrongParameterType")) {
			Method method = findMethodNamed(FakeEntity.class, name);
			assertNotNull(method);
			assertTrue(name, !processor.hasEventSignature(method));
		}
		Method theGoodOne = findMethodNamed(FakeEntity.class, "theGoodOne");
		assertNotNull(theGoodOne);
		assertTrue(processor.hasEventSignature(theGoodOne));
	}

	@Test
	public void testRegisterFailure() {
		for (String name : Arrays.asList("badReturnType", "wrongParameterNumber", "wrongParameterType")) {
			Method method = findMethodNamed(FakeEntity.class, name);
			assertNotNull(method);
			try {
				processor.register(FakeEntity.class, method);
				fail("expect IllegalStateException");
			} catch (IllegalStateException e) {
			}
		}
	}

	@Test
	public void testRegisterAcceptanceAndVisibility() {
		Method theGoodOne = findMethodNamed(FakeEntity.class, "theGoodOne");
		assertNotNull(theGoodOne);
		assertTrue(!theGoodOne.isAccessible());
		assertEquals(theGoodOne, processor.register(FakeEntity.class, theGoodOne));
		assertTrue(theGoodOne.isAccessible());

	}

	@Test
	public void testDuplicateRegisterFailure() {
		Method theGoodOne = findMethodNamed(FakeEntity.class, "theGoodOne");
		assertNotNull(theGoodOne);
		processor.register(FakeEntity.class, theGoodOne);
		try {
			processor.register(FakeEntity.class, theGoodOne);
			fail("expect IllegalStateException");
		} catch (IllegalStateException e) {
		}
	}

	@Test
	public void testHandledInvokation() {
		FakeEntity entity = new FakeEntity();

		// no handler
		assertEquals(0L, entity.getHandledCounter());
		processor.apply(entity, new FakeDomainEventA(entity.getIdentifier()));
		assertEquals(0L, entity.getHandledCounter());

		// with handler
		Method theGoodOne = findMethodNamed(FakeEntity.class, "theGoodOne");
		assertNotNull(theGoodOne);
		processor.register(FakeEntity.class, theGoodOne);
		processor.apply(entity, new FakeDomainEventA(entity.getIdentifier()));
		assertEquals(1L, entity.getHandledCounter());
	}

	protected static Method findMethodNamed(Class<?> type, String name) {
		for (Method method : type.getDeclaredMethods()) {
			if (method.getName().equals(name)) {
				return method;
			}
		}
		return null;
	}
}
