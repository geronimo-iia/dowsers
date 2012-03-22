/**
 * 
 */
package com.iia.cqrs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.iia.cqrs.annotation.TODO;
import com.iia.cqrs.events.DomainEvent;
import com.iia.cqrs.events.DomainEventInvoker;
import com.iia.cqrs.events.EventProvider;
import com.iia.cqrs.events.processor.EventProcessor;
import com.iia.cqrs.events.processor.EventProcessorProvider;

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
public class Aggregate implements EventProvider, RegisterEntity {

	private EventProcessorProvider eventProcessorProvider;
	private EventProcessor eventProcessor;
	private final Entity root;
	private Map<UUID, Entity> entities;
	private long eventVersion = 0l;
	/**
	 * List of uncommitted changes.
	 */
	private final List<DomainEvent> uncommittedChanges = new ArrayList<DomainEvent>();;

	/**
	 * Build a new instance of Aggregate with specified root entity.
	 * 
	 * @param root
	 *            root entity
	 */
	public Aggregate(final Entity root) {
		super();
		this.root = Preconditions.checkNotNull(root);
		entities = Maps.newHashMap();
		register(root);
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
		if (history != null) {
			long lastEventVersion = 0l;
			for (final DomainEvent domainEvent : history) {
				// warn entity
				// some DE are not for root but for child. => getChild(UUID id)
				eventProcessor.apply(root, domainEvent);
				lastEventVersion = domainEvent.getVersion();
			}
			eventVersion = lastEventVersion;
		}
	}

	/**
	 * Increment version of this aggregate context.
	 */
	@Override
	public void incrementVersion() {
		root.setIdentifier(root.getIdentifier().nextVersion());
	}

	@TODO("Choose between incrementVersion and updateVersion")
	@Override
	public void updateVersion(final long version) {
		root.setIdentifier(root.getIdentifier().withVersion(version));
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

	private long getNewEventVersion() {
		return ++eventVersion;
	}

	/**
	 * @see com.iia.cqrs.RegisterEntity#register(com.iia.cqrs.Entity)
	 */
	@Override
	public void register(final Entity entity) {
		/**
		 * 
		 * When we Apply a domain event we will first assign the aggregate root
		 * Id to the event so that we can keep track to which aggregate root
		 * this event belongs to.<br />
		 * This is ever done by constructor of all DomainEvent type.
		 * 
		 * Secondly we get a new version and assign this to the event, this is
		 * to maintain the correct order of the events. Then we call the apply
		 * method which will make the state change to the aggregate root. And
		 * finally we will add this domain event to the internal list of applied
		 * events.
		 * 
		 */

		// entity.setDomainEventInvoker(this);
		final EventProcessor eventProcessor = eventProcessorProvider.get(entity.getClass());
		entity.setDomainEventInvoker(new DomainEventInvoker() {
			@Override
			public <T extends DomainEvent> void apply(T domainEvent) {
				Preconditions.checkState(domainEvent.getEntityIdentity().equals(entity.getIdentifier().getIdentity()));
				// inc version
				domainEvent.setVersion(getNewEventVersion());
				// call the apply method which will make the state change to the
				// entity
				eventProcessor.apply(entity, domainEvent);
				// add add this domain event to the internal list of applied
				// events.
				uncommittedChanges.add(domainEvent);
			}
		});
		
		// add in map
		entities.put(entity.getIdentifier().getIdentity(), entity);
	}

}
