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
package org.intelligentsia.dowsers.domain;

import static org.junit.Assert.*;

import org.intelligentsia.dowsers.container.GenericDomainEntityFactory;
import org.intelligentsia.dowsers.event.GenericDomainAggregateFactory;
import org.intelligentsia.dowsers.event.processor.CacheEventProcessorProvider;

import org.junit.Before;
import org.junit.Test;

/**
 * LocalDomainEntityTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class LocalDomainEntityTest {
	
	private AggregateFactory aggregateFactory;

	@Before
	public void initialize() {
		aggregateFactory = new GenericDomainAggregateFactory(new CacheEventProcessorProvider());
	}

	
	public DomainEntity getDomainEntity() {
		GenericDomainEntityFactory defaultDomainEntityFactory = new GenericDomainEntityFactory(aggregateFactory);
		return defaultDomainEntityFactory.create(DummyDomainEntity.class);
	}
	
	@Test
	public void checkOnRegisterCall() {
		DummyLocalEntity entity = new DummyLocalEntity();
		assertNotNull(entity.getIdentity());
		assertTrue(!entity.isOnRegisterCalled());
	
		getDomainEntity().register(entity);
		
		assertTrue(entity.isOnRegisterCalled());	
	}
}
