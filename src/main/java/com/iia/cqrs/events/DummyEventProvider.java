/**
 * 
 */
package com.iia.cqrs.events;

import java.util.ArrayList;
import java.util.List;

import com.iia.cqrs.Entity;
import com.iia.cqrs.Identifier;
import com.iia.cqrs.annotation.TODO;
import com.iia.cqrs.events.processor.EventProcessor;

/**
 * Aggregate act as a context of a root entity.
 * 
 * A collection of objects that are bound together by a root entity, otherwise
 * known as an aggregate root. The aggregate root guarantees the consistency of
 * changes being made within the aggregate by forbidding external objects from
 * holding references to its members.
 * 
 * <ul>
 * <li>Group of Value Objects</li>
 * <li>One entity within the aggregate is the aggregate root</li>
 * <li>All access to the objects inside go through the root entity</li>
 * <li>Aggregates are consistency boundaries</li>
 * </ul>
 * 
 * @author jgt
 * 
 */
public class DummyEventProvider implements EventProvider  {

	private EventProcessor eventProcessor;
	private Identifier identifier;
	private Entity root;

	/**
	 * List of uncommitted changes.
	 */
	private final List<DomainEvent> uncommittedChanges = new ArrayList<DomainEvent>();;

	/**
	 * @see com.iia.cqrs.events.EventProvider#getIdentifier()
	 */
	@Override
	public Identifier getIdentifier() {
		return identifier;
	}

	/**
	 * It is basically apply events of the given aggregate.
	 * 
	 * @param history
	 *            events history
	 */
	@Override
	public void loadFromHistory(final Iterable<? extends DomainEvent> history) {
		for (final DomainEvent domainEvent : history) {
			eventProcessor.apply(root, domainEvent);
		}
	}

	/**
	 * @see com.iia.cqrs.events.EventProvider#updateVersion(int)
	 */
	@Override
	@TODO("Resolve duplicate instance of identifier")
	public void updateVersion(int version) {
		identifier = identifier.withVersion(version);
	}

	/**
	 * Increment version of this aggregate context.
	 * 
	 */
	@TODO("Resolve duplicate instance of identifier")
	public void incrementVersion() {
		identifier = identifier.nextVersion();
	}

	@Override
	public Iterable<DomainEvent> getUncommittedChanges() {
		return uncommittedChanges;
	}

	/**
	 * Mark all changes as committed (clear all uncommittedChanges).
	 */
	@Override
	public void markChangesCommitted() {
		uncommittedChanges.clear();
	}

	/**
	 * When we Apply a domain event we will first assign the aggregate root Id
	 * to the event so that we can keep track to which aggregate root this event
	 * belongs to. Secondly we get a new version and assign this to the event,
	 * this is to maintain the correct order of the events. Then we call the
	 * apply method which will make the state change to the aggregate root. And
	 * finally we will add this domain event to the internal list of applied
	 * events.
	 * 
	 * @see com.iia.cqrs.events.processor.EventProcessor#apply(com.iia.cqrs.Entity, com.iia.cqrs.events.DomainEvent)
	 */
	@TODO("remove source, because its normaly equals to root?")
	public void apply(DomainEvent domainEvent) {
		// assert source.equals(root)

		// this is ever done in constructor
		// domainEvent.setEntityIdentifier(source.getIdentifier());

		eventProcessor.apply(root, domainEvent);
		uncommittedChanges.add(domainEvent);
	}

}
