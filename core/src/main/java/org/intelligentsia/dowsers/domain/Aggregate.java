/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import org.intelligentsia.dowsers.Registry;
import org.intelligentsia.dowsers.events.DomainEventInvoker;

/**
 * Aggregate declare methods to manage an aggregate of entities.
 * 
 * 
 * Aggregate act as a context of a root entity.
 * 
 * A collection of objects and entities that are bound together by a root
 * entity, otherwise known as an aggregate root or domain entity. <br />
 * The aggregate root guarantees the consistency of changes being made within
 * the aggregate by forbidding external objects from holding references to its
 * members.
 * 
 * <ul>
 * <li>Group of Value Objects and entities</li>
 * <li>One entity within the aggregate is the aggregate root (domain entity)</li>
 * <li>All access to the objects inside go through the root entity</li>
 * <li>Aggregates are consistency boundaries</li>
 * </ul>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface Aggregate extends Registry<LocalDomainEntity> {

	/**
	 * Register the root domain entity for this aggregate.
	 * 
	 * @param domainEntity
	 *            domain entity root for this aggregate
	 * @throws NullPointerException
	 *             if domainEntity is null
	 * @throws IllegalStateException
	 *             if a root entity is ever registered
	 */
	public void registerRoot(final DomainEntity domainEntity) throws NullPointerException, IllegalStateException;

	/**
	 * Get an instance of DomainEventInvoker dedicated for this entity.
	 * 
	 * @param entity
	 *            entity instance.
	 * @return a DomainEventInvoker instance
	 * @throws NullPointerException
	 *             if entity is null
	 * @throws IllegalStateException
	 *             if entity is not register in this aggregate
	 */
	public DomainEventInvoker getDomainEventInvoker(Entity entity) throws NullPointerException, IllegalStateException;
}
