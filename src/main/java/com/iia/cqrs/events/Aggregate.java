/**
 * 
 */
package com.iia.cqrs.events;

import com.iia.cqrs.Identifier;

/**
 * /** Aggregate act as a context of a root entity.
 * 
 * <ul>
 * <li>Group of Entities & Value Objects</li>
 * <li>One entity within the aggregate is the aggregate root</li>
 * <li>All access to the objects inside go through the root entity</li>
 * <li>Aggregates are consistency boundaries</li>
 * </ul>
 * 
 */
public class Aggregate implements EventProvider, DomainEventBusInvoker {

	/**
	 * @see com.iia.cqrs.events.EventProvider#getIdentifier()
	 */
	@Override
	public Identifier getIdentifier() {
		return null;
	}

	/**
	 * @see com.iia.cqrs.events.EventProvider#loadFromHistory(java.lang.Iterable)
	 */
	@Override
	public void loadFromHistory(Iterable<DomainEvent> domainEvents) {
	}

	/**
	 * @see com.iia.cqrs.events.EventProvider#updateVersion(int)
	 */
	@Override
	public void updateVersion(int version) {
	}

	/**
	 * @see com.iia.cqrs.events.EventProvider#uncommitedChanges()
	 */
	@Override
	public Iterable<DomainEvent> uncommitedChanges() {
		return null;
	}

	/**
	 * @see com.iia.cqrs.events.EventProvider#clear()
	 */
	@Override
	public void clear() {
	}

	/**
	 * @see com.iia.cqrs.events.DomainEventBusInvoker#apply(com.iia.cqrs.events.DomainEvent)
	 */
	@Override
	public <T extends DomainEvent> void apply(T domainEvent) {
	}

}
