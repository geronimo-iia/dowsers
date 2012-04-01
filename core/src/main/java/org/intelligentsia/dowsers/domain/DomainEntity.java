/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import org.intelligentsia.dowsers.util.Registry;

/**
 * DomainEntity represent a domain entity.
 * 
 * <ul>
 * <li>has a immutable Identity</li>
 * <li>can aggregate local domain entity (implement a registry of
 * LocalDomainEntity)</li>
 * <li>can be referenced by other domain entity</li>
 * </ul>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DomainEntity extends Entity implements Registry<LocalDomainEntity> {

	/**
	 * Aggregate instance essentially used to register local domain entity.
	 */
	private final Aggregate aggregate;

	/**
	 * Build a new instance of root Entity.
	 */
	public DomainEntity(final AggregateFactory aggregateFactory) {
		this(aggregateFactory, IdentifierFactoryProvider.generateNewIdentifier());
	}

	/**
	 * Build a new instance of root <code>Entity</code>
	 * 
	 * @param identifier
	 *            specified entity
	 * @throws NullPointerException
	 *             if identifier is null
	 */
	public DomainEntity(final AggregateFactory aggregateFactory, final String identifier) throws NullPointerException {
		super(identifier);
		// create aggregate with this root instance
		aggregate = aggregateFactory.newInstance(this);
		// set domain event invoker for root
		setDomainEventInvoker(aggregate.getDomainEventInvoker(this));
	}

	/**
	 * Register specified entity onto this aggregate.
	 * 
	 * @see org.intelligentsia.dowsers.domain.LocalDomainEntityRegistry#register(org.intelligentsia.dowsers.domain.Entity)
	 */
	@Override
	public final void register(final LocalDomainEntity entity) throws NullPointerException, IllegalStateException {
		// regsiter on aggregate
		aggregate.register(entity);
		// set domain event invoker
		entity.setDomainEventInvoker(aggregate.getDomainEventInvoker(entity));
		// callback
		entity.onRegister(this);
	}

	/**
	 * @return current version of this domain entity.
	 */
	public final Version getVersion() {
		//delegate call to aggregate
		return aggregate.getVersion();
	}

	/**
	 * @return aggregate instance
	 */
	protected Aggregate getAggregate() {
		return aggregate;
	}
}
