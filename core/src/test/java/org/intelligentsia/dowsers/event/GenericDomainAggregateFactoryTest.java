package org.intelligentsia.dowsers.event;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import org.intelligentsia.dowsers.event.processor.CacheEventProcessorProvider;

/** 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class GenericDomainAggregateFactoryTest {

	private GenericDomainAggregateFactory aggregateFactory;

	@Before
	public void initialize() {
		aggregateFactory = new GenericDomainAggregateFactory(new CacheEventProcessorProvider());
	}

	@Test
	public void testRootNotInitialized() {
		DomainAggregate aggregate = (DomainAggregate) aggregateFactory.newInstance();
		Assert.assertNotNull(aggregate);
		Assert.assertTrue(!aggregate.hasRootRegistered());
	}
}
