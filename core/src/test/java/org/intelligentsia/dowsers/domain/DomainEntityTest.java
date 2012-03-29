/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import static org.junit.Assert.assertNotNull;

import org.intelligentsia.dowsers.container.GenericDomainEntityFactory;
import org.intelligentsia.dowsers.eventprocessor.CacheEventProcessorProvider;
import org.intelligentsia.dowsers.events.GenericDomainAggregateFactory;
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
	public void testFactory() {
		assertNotNull(aggregateFactory);
		GenericDomainEntityFactory defaultDomainEntityFactory = new GenericDomainEntityFactory(aggregateFactory);
		DummyDomainEntity domainEntity = defaultDomainEntityFactory.create(DummyDomainEntity.class);
		assertNotNull(domainEntity);
		assertNotNull(domainEntity.getIdentity());
		assertNotNull(domainEntity.getAggregate());
	}

}
