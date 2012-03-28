/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import org.intelligentsia.dowsers.eventprocessor.CacheEventProcessorProvider;
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
		aggregateFactory = new GenericAggregateFactory(new CacheEventProcessorProvider());
	}

	@Test
	public void testFactory() {
		assertNotNull(aggregateFactory);
		GenericDomainEntityFactory defaultDomainEntityFactory = new GenericDomainEntityFactory(aggregateFactory);
		DummyDomainEntity domainEntity = defaultDomainEntityFactory.create(DummyDomainEntity.class);
		assertNotNull(domainEntity);
		assertNotNull(domainEntity.getIdentifier());
		assertNotNull(domainEntity.getAggregate());
		assertEquals(domainEntity.getIdentifier(), domainEntity.getAggregate().getIdentifier());
	}

	@Test
	public void checkNextVersionAccess() {
		DummyDomainEntity domainEntity = new DummyDomainEntity(aggregateFactory);
		domainEntity.nextVersion();
	}

	@Test
	public void checkSetVersionAccess() {
		DummyDomainEntity domainEntity = new DummyDomainEntity(aggregateFactory);
		domainEntity.setVersion(2L);
	}
}
