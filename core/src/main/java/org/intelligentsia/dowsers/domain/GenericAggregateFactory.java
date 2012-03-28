/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import org.intelligentsia.dowsers.eventprocessor.EventProcessorProvider;

import com.google.common.base.Preconditions;

/**
 * GenericAggregateFactory implement a generic Factory for Aggregate class. Hide
 * EventProcessorProvider initialization.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class GenericAggregateFactory implements AggregateFactory {

	/**
	 * EventProcessorProvider instance to use to create Aggregate instance.
	 */
	private final EventProcessorProvider eventProcessorProvider;

	/**
	 * Build a new instance of GenericAggregateFactory.
	 * 
	 * @param eventProcessorProvider eventProcessorProvider instance
	 * @throws NullPointerException
	 *             if eventProcessorProvider is null
	 */
	public GenericAggregateFactory(final EventProcessorProvider eventProcessorProvider) throws NullPointerException {
		super();
		this.eventProcessorProvider = Preconditions.checkNotNull(eventProcessorProvider);
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.AggregateFactory#create(org.intelligentsia.dowsers.domain.DomainEntity)
	 */
	@Override
	public <T extends DomainEntity> Aggregate create(final T domainEntity) throws NullPointerException {
		return new Aggregate(eventProcessorProvider, domainEntity);
	}

}
