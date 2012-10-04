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
import org.intelligentsia.dowsers.core.Version;
import org.intelligentsia.dowsers.event.GenericDomainAggregateFactory;
import org.intelligentsia.dowsers.event.processor.CacheEventProcessorProvider;

import org.junit.Before;
import org.junit.Test;

/**
 * DomainEntityTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class DomainEntityTest {

	private AggregateFactory aggregateFactory;

	@Before
	public void initialize() {
		aggregateFactory = new GenericDomainAggregateFactory(new CacheEventProcessorProvider());
	}

	@Test
	public void testFactoryInstanciateIdentityAggregateAndVersion() {
		assertNotNull(aggregateFactory);
		GenericDomainEntityFactory defaultDomainEntityFactory = new GenericDomainEntityFactory(aggregateFactory);
		DummyDomainEntity domainEntity = defaultDomainEntityFactory.create(DummyDomainEntity.class);
		assertNotNull(domainEntity);
		assertNotNull(domainEntity.getIdentity());
		assertNotNull(domainEntity.getAggregate());
		assertNotNull(domainEntity.getVersion());
	}

	@Test
	public void checkInitialVersionOnCreation() {
		GenericDomainEntityFactory defaultDomainEntityFactory = new GenericDomainEntityFactory(aggregateFactory);
		DummyDomainEntity domainEntity = defaultDomainEntityFactory.create(DummyDomainEntity.class);
		assertNotNull(domainEntity.getVersion());
		assertEquals(Version.forInitialVersion(), domainEntity.getVersion());
	}
}