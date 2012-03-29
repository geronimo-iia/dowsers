/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import static org.junit.Assert.*;

import org.intelligentsia.dowsers.container.GenericDomainEntityFactory;
import org.intelligentsia.dowsers.eventprocessor.CacheEventProcessorProvider;
import org.intelligentsia.dowsers.events.GenericDomainAggregateFactory;
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
