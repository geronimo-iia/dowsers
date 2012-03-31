/**
 * 
 */
package org.intelligentsia.dowsers.event;

import org.intelligentsia.dowsers.domain.Aggregate;
import org.intelligentsia.dowsers.domain.AggregateFactory;
import org.intelligentsia.dowsers.domain.DomainEntity;
import org.intelligentsia.dowsers.event.processor.EventProcessorProvider;

import com.google.common.base.Preconditions;

/**
 * GenericDomainAggregateFactory implement a generic Factory for Aggregate
 * class. Hide EventProcessorProvider initialization.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class GenericDomainAggregateFactory implements AggregateFactory {

	/**
	 * EventProcessorProvider instance to use to create Aggregate instance.
	 */
	private final EventProcessorProvider eventProcessorProvider;

	/**
	 * Build a new instance of GenericDomainAggregateFactory.
	 * 
	 * @param eventProcessorProvider
	 *            eventProcessorProvider instance
	 * @throws NullPointerException
	 *             if eventProcessorProvider is null
	 */
	public GenericDomainAggregateFactory(final EventProcessorProvider eventProcessorProvider) throws NullPointerException {
		super();
		this.eventProcessorProvider = Preconditions.checkNotNull(eventProcessorProvider);
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.AggregateFactory#create(org.intelligentsia.dowsers.domain.DomainEntity)
	 */
	@Override
	public <T extends DomainEntity> DomainAggregate newInstance(final T domainEntity) throws NullPointerException {
		final DomainAggregate domainAggregate = new DomainAggregate(eventProcessorProvider);
		domainAggregate.registerRoot(domainEntity);
		return domainAggregate;
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.AggregateFactory#newInstance()
	 */
	@Override
	public <T extends DomainEntity> Aggregate newInstance() {
		return new DomainAggregate(eventProcessorProvider);
	}

}
