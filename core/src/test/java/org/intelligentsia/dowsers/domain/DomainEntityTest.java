/**
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
