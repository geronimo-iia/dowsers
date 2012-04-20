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
package org.intelligentsia.dowsers.event.processor;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.intelligentsia.dowsers.core.IdentifierFactoryProvider;
import org.intelligentsia.dowsers.event.processor.EntityEventProcessor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


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
		for (final String name : Arrays.asList("badReturnType", "wrongParameterNumber", "wrongParameterType")) {
			final Method method = EntityEventProcessorTest.findMethodNamed(FakeEntity.class, name);
			Assert.assertNotNull(method);
			Assert.assertTrue(name, !processor.hasEventSignature(method));
		}
		final Method theGoodOne = EntityEventProcessorTest.findMethodNamed(FakeEntity.class, "theGoodOne");
		Assert.assertNotNull(theGoodOne);
		Assert.assertTrue(processor.hasEventSignature(theGoodOne));
	}

	@Test
	public void checkRegisterFailure() {
		for (final String name : Arrays.asList("badReturnType", "wrongParameterNumber", "wrongParameterType")) {
			final Method method = EntityEventProcessorTest.findMethodNamed(FakeEntity.class, name);
			Assert.assertNotNull(method);
			try {
				processor.register(FakeEntity.class, method);
				Assert.fail("expect IllegalStateException");
			} catch (final IllegalStateException e) {
			}
		}
	}

	@Test
	public void testRegisterAcceptanceAndVisibility() {
		final Method theGoodOne = EntityEventProcessorTest.findMethodNamed(FakeEntity.class, "theGoodOne");
		Assert.assertNotNull(theGoodOne);
		Assert.assertTrue(!theGoodOne.isAccessible());
		Assert.assertEquals(theGoodOne, processor.register(FakeEntity.class, theGoodOne));
		Assert.assertTrue(theGoodOne.isAccessible());

	}

	@Test
	public void checkDuplicateRegisterFailure() {
		final Method theGoodOne = EntityEventProcessorTest.findMethodNamed(FakeEntity.class, "theGoodOne");
		Assert.assertNotNull(theGoodOne);
		processor.register(FakeEntity.class, theGoodOne);
		try {
			processor.register(FakeEntity.class, theGoodOne);
			Assert.fail("expect IllegalStateException");
		} catch (final IllegalStateException e) {
		}
	}

	@Test
	public void testHandledInvokation() {
		final FakeEntity entity = new FakeEntity(IdentifierFactoryProvider.generateNewIdentifier());

		// no handler
		Assert.assertEquals(0L, entity.getHandledCounter());
		processor.apply(entity, new FakeDomainEventA(entity));
		Assert.assertEquals(0L, entity.getHandledCounter());

		// with handler
		final Method theGoodOne = EntityEventProcessorTest.findMethodNamed(FakeEntity.class, "theGoodOne");
		Assert.assertNotNull(theGoodOne);
		processor.register(FakeEntity.class, theGoodOne);
		processor.apply(entity, new FakeDomainEventA(entity));
		Assert.assertEquals(1L, entity.getHandledCounter());
	}

	@Test
	public void checkNoFailureInQuietMode() {
		EntityEventProcessor entityEventProcessor = new EntityEventProcessor(Boolean.TRUE);
		entityEventProcessor.register(FakeEntity.class);

		entityEventProcessor = new EntityEventProcessor(Boolean.TRUE);
		entityEventProcessor.register(FakeBeautifullEntity.class);
	}

	@Test
	public void checkFailureInStandardMode() {
		try {
			processor.register(FakeEntity.class);
			Assert.fail("expect IllegalStateException");
		} catch (final IllegalStateException e) {
		}
	}

	@Test
	public void testResgiterFakeBeautifullEntity() {
		processor.register(FakeBeautifullEntity.class);
	}

	@Test
	public void checkFailIfEverInitialized() {

		processor.register(FakeBeautifullEntity.class);
		try {
			processor.register(FakeBeautifullEntity.class);
			Assert.fail("expect IllegalStateException");
		} catch (final IllegalStateException e) {
		}

		// no exception in quiet mode
		final EntityEventProcessor entityEventProcessor = new EntityEventProcessor(Boolean.TRUE);
		entityEventProcessor.register(FakeBeautifullEntity.class);
		entityEventProcessor.register(FakeBeautifullEntity.class);
	}

	protected static Method findMethodNamed(final Class<?> type, final String name) {
		for (final Method method : type.getDeclaredMethods()) {
			if (method.getName().equals(name)) {
				return method;
			}
		}
		return null;
	}
}
