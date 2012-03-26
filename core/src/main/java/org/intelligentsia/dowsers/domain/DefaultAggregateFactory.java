/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import org.intelligentsia.dowsers.events.processor.EventProcessorProvider;

import com.google.common.base.Preconditions;

/**
 * DefaultAggregateFactory implement a default Factory for Aggregate class.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DefaultAggregateFactory implements AggregateFactory {

	private final EventProcessorProvider eventProcessorProvider;

	/**
	 * Build a new instance of DefaultAggregateFactory.
	 * 
	 * @param eventProcessorProvider
	 * @throws NullPointerException
	 *             if eventProcessorProvider is null
	 */
	public DefaultAggregateFactory(final EventProcessorProvider eventProcessorProvider) throws NullPointerException {
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
