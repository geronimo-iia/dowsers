/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import com.google.common.base.Preconditions;

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
 * 
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DomainEntity extends Entity implements RegisterEntity {

	/**
	 * Aggregate instance essentially used to register child entity.
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
	public DomainEntity(final AggregateFactory aggregateFactory, final Identifier identifier)
			throws NullPointerException {
		super(identifier);
		aggregate = aggregateFactory.create(this);
	}

	/**
	 * Register specified entity onto this aggregate.
	 * 
	 * @throws IllegalStateException
	 *             if identifier of specified entity is a initial version.
	 * @see org.intelligentsia.dowsers.domain.RegisterEntity#register(org.intelligentsia.dowsers.domain.Entity)
	 */
	@Override
	public final void register(Entity entity) throws NullPointerException, IllegalStateException {
		Preconditions.checkState(entity.getIdentifier().isForInitialVersion());
		aggregate.register(entity);
	}

}
