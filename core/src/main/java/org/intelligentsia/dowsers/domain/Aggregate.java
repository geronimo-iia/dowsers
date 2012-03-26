/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.intelligentsia.dowsers.events.EventProvider;
import org.intelligentsia.dowsers.events.processor.EventProcessor;
import org.intelligentsia.dowsers.events.processor.EventProcessorProvider;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
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
public class Aggregate implements EventProvider, EntityRegistry {

	/**
	 * EventProcessorProvider instance.
	 */
	private final EventProcessorProvider eventProcessorProvider;

	/**
	 * The domain entity root instance.
	 */
	private final DomainEntity root;
	/**
	 * Entities used in this aggregate (included the domain entity root)
	 */
	private final Map<UUID, Entity> entities;
	/**
	 * Event ordinal.
	 */
	private long eventOrdinal = 0l;
	/**
	 * List of uncommitted changes.
	 */
	private final List<DomainEvent> uncommittedChanges = new ArrayList<DomainEvent>();;

	/**
	 * Build a new instance of Aggregate with specified root entity.
	 * 
	 * @param domainEntity
	 *            root domain entity
	 * @throws NullPointerException
	 *             if eventProcessorProvider or domainEntity is null
	 */
	public Aggregate(final EventProcessorProvider eventProcessorProvider, final DomainEntity domainEntity) throws NullPointerException {
		super();
		this.eventProcessorProvider = Preconditions.checkNotNull(eventProcessorProvider);
		root = Preconditions.checkNotNull(domainEntity);
		entities = Maps.newHashMap();
		register(root);
	}

	/**
	 * @see org.intelligentsia.dowsers.events.EventProvider#getIdentifier()
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
	public void loadFromHistory(final Iterable<? extends DomainEvent> history) throws IllegalStateException {
		if (history != null) {
			long ordinal = 0l;
			for (final DomainEvent domainEvent : history) {
				// Which entity
				final Entity entity = entities.get(domainEvent.getEntityIdentity());
				if (entity == null) {
					throw new IllegalStateException("No entity for " + domainEvent.getEntityIdentity());
				}
				// obtain processor
				final EventProcessor eventProcessor = eventProcessorProvider.get(entity.getClass());
				if (eventProcessor == null) {
					throw new IllegalStateException("No event processor for " + entity.getClass().getName());
				}
				// apply event
				eventProcessor.apply(entity, domainEvent);
				// obtain ordinal
				ordinal = domainEvent.getOrdinal();
			}
			eventOrdinal = ordinal;
		}
	}

	/**
	 * Increment version of this aggregate context.
	 */
	@Override
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
		eventOrdinal = 0l;
		uncommittedChanges.clear();
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.EntityRegistry#register(org.intelligentsia.dowsers.domain.Entity)
	 */
	@Override
	public void register(final Entity entity) {
		// get specific event processor
		final EventProcessor eventProcessor = eventProcessorProvider.get(entity.getClass());
		// set domain invoker
		entity.setDomainEventInvoker(new DefaultDomainEventInvoker(eventProcessor, entity));
		// add in map
		entities.put(entity.getIdentifier().getIdentity(), entity);
	}

	/**
	 * @return next event ordinal.
	 */
	private long nextEventOrdinal() {
		return ++eventOrdinal;
	}

	/**
	 * 
	 * DefaultDomainEventInvoker implements a default DomainEventInvoker for a
	 * specific entity.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	private class DefaultDomainEventInvoker implements DomainEventInvoker {
		/**
		 * attached event processor instance.
		 */
		private final EventProcessor eventProcessor;
		/**
		 * Target entity instance.
		 */
		private final Entity target;

		/**
		 * Build a new instance of <code>DefaultDomainEventInvoker</code>
		 * 
		 * @param eventProcessor
		 * @param target
		 * @throws NullPointerException
		 *             if eventProcessor or target is null
		 */
		public DefaultDomainEventInvoker(final EventProcessor eventProcessor, final Entity target) throws NullPointerException {
			super();
			this.eventProcessor = Preconditions.checkNotNull(eventProcessor);
			this.target = Preconditions.checkNotNull(target);
		}

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
		 * @see org.intelligentsia.dowsers.domain.DomainEventInvoker#apply(org.intelligentsia.dowsers.domain.DomainEvent)
		 */
		@Override
		public <T extends DomainEvent> void apply(final T domainEvent) {
			// set ordinal value from aggregate
			domainEvent.setOrdinal(nextEventOrdinal());
			// call the apply method which will make the state change to the
			// entity
			eventProcessor.apply(target, domainEvent);
			// add add this domain event to the internal list of applied
			// events.
			uncommittedChanges.add(domainEvent);
		}

	}
}
