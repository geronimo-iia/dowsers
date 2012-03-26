/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import org.intelligentsia.dowsers.events.processor.CacheEventProcessorProvider;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
		aggregateFactory = new DefaultAggregateFactory(new CacheEventProcessorProvider());
	}

	@Test
	public void testFactory() {
		assertNotNull(aggregateFactory);
		DefaultDomainEntityFactory defaultDomainEntityFactory = new DefaultDomainEntityFactory(aggregateFactory);
		DummyDomainEntity domainEntity = defaultDomainEntityFactory.create(DummyDomainEntity.class);
		assertNotNull(domainEntity);
		assertNotNull(domainEntity.getIdentifier());
		assertNotNull(domainEntity.getAggregate());
		assertEquals(domainEntity.getIdentifier(), domainEntity.getAggregate().getIdentifier());
	}
}
