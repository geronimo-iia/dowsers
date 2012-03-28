/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import com.google.common.annotations.VisibleForTesting;

/**
 * DomainEntity represent a domain entity.
 * 
 * <ul>
 * <li>has a immutable Identity</li>
 * <li>has a version</li>
 * <li>can aggregate simple entity</li>
 * <li>can be referenced by other domain entity</li>
 * </ul>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DomainEntity extends Entity implements LocalDomainEntityRegistry {

	/**
	 * Aggregate instance essentially used to register local domain entity.
	 */
	private final Aggregate aggregate;

	/**
	 * Build a new instance of root Entity.
	 */
	public DomainEntity(final AggregateFactory aggregateFactory) {
		this(aggregateFactory, Identifier.random());
	}

	/**
	 * Build a new instance of root <code>Entity</code>
	 * 
	 * @param identifier
	 *            specified entity
	 * @throws NullPointerException
	 *             if identifier is null
	 */
	public DomainEntity(final AggregateFactory aggregateFactory, final Identifier identifier) throws NullPointerException {
		super(identifier);
		aggregate = aggregateFactory.create(this);
	}

	/**
	 * Register specified entity onto this aggregate.
	 * 
	 * @see org.intelligentsia.dowsers.domain.LocalDomainEntityRegistry#register(org.intelligentsia.dowsers.domain.Entity)
	 */
	@Override
	public final void register(final LocalDomainEntity entity) throws NullPointerException, IllegalStateException {
		aggregate.register(entity);
	}

	/**
	 * @return DomainEventProvider instance.
	 */
	@VisibleForTesting
	DomainEventProvider getDomainEventProvider() {
		return aggregate;
	}

}
