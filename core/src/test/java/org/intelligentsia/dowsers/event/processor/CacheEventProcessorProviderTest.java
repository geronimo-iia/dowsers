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

import org.intelligentsia.dowsers.event.processor.CacheEventProcessorProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.cache.CacheBuilder;

/**
 * CacheEventProcessorProviderTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class CacheEventProcessorProviderTest {

	private CacheEventProcessorProvider processorProvider;

	@Before
	public void initialize() {
		processorProvider = new CacheEventProcessorProvider(CacheBuilder.newBuilder());
	}

	@Test
	public void testRegister() {
		try {
			processorProvider.register(FakeEntity.class);
			Assert.fail("Expected RuntimeException");
		} catch (final RuntimeException e) {
		}
		processorProvider.register(FakeBeautifullEntity.class);
	}

	@Test
	public void testAutomaticRegistration() {
		try {
			processorProvider.get(FakeEntity.class);
			Assert.fail("Expected RuntimeException");
		} catch (final RuntimeException e) {
		}
		processorProvider.get(FakeBeautifullEntity.class);
	}

	@Test
	public void testMutipleCallFromRegistration() {
		processorProvider.register(FakeBeautifullEntity.class);
		processorProvider.get(FakeBeautifullEntity.class);
		processorProvider.get(FakeBeautifullEntity.class);
		processorProvider.get(FakeBeautifullEntity.class);
	}

	@Test
	public void testMutipleCall() {
		processorProvider.get(FakeBeautifullEntity.class);
		processorProvider.get(FakeBeautifullEntity.class);
		processorProvider.get(FakeBeautifullEntity.class);
	}
}
