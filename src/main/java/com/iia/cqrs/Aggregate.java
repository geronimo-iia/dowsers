/**
 * 
 */
package com.iia.cqrs;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import com.iia.cqrs.annotation.TODO;
import com.iia.cqrs.events.DomainEvent;
import com.iia.cqrs.events.DomainEventInvoker;
import com.iia.cqrs.events.EventProvider;
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
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Aggregate implements EventProvider, DomainEventInvoker {

	private EventProcessor eventProcessor;
	private Entity root = null;

	/**
	 * List of uncommitted changes.
	 */
	private final List<DomainEvent> uncommittedChanges = new ArrayList<DomainEvent>();;

	/**
	 * Build a new instance of Aggregate.
	 */
	public Aggregate() {
		super();
	}

	/**
	 * Attache specified entity as root entoty for this aggregate.
	 * 
	 * @param root
	 *            entity
	 * @throws NullPointerException
	 *             if root is null
	 * @throws IllegalStateException
	 *             if root is ever set
	 */
	public void attach(Entity root) throws NullPointerException, IllegalStateException {
		 Preconditions.checkNotNull(root);
		 Preconditions.checkState(this.root==null);
		 this.root=root;
		 // call back entity
		 this.root.setDomainEventInvoker(this);
	}

	/**
	 * @see com.iia.cqrs.events.EventProvider#getIdentifier()
	 */
	@Override
	public Identifier getIdentifier() {
		return root.getIdentifier();
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
	public void updateVersion(final int version) {
		root.setIdentifier(root.getIdentifier().withVersion(version));
	}

	/**
	 * Increment version of this aggregate context.
	 * 
	 */
	@TODO("Resolve duplicate instance of identifier")
	public void incrementVersion() {
		root.setIdentifier(root.getIdentifier().nextVersion());
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
	 */
	@Override
	public void apply(final DomainEvent domainEvent) {
		// assert source.equals(root)

		// this is ever done in constructor
		// domainEvent.setEntityIdentifier(source.getIdentifier());

		eventProcessor.apply(root, domainEvent);
		uncommittedChanges.add(domainEvent);
	}

}
