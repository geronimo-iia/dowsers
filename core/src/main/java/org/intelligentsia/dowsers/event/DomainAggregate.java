/**
 * 
 */
package org.intelligentsia.dowsers.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.intelligentsia.dowsers.domain.Aggregate;
import org.intelligentsia.dowsers.domain.DomainEntity;
import org.intelligentsia.dowsers.domain.Entity;
import org.intelligentsia.dowsers.domain.LocalDomainEntity;
import org.intelligentsia.dowsers.domain.Version;
import org.intelligentsia.dowsers.event.processor.EventProcessor;
import org.intelligentsia.dowsers.event.processor.EventProcessorProvider;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
 * DomainAggregate
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DomainAggregate implements Aggregate, DomainEventProvider {

	/**
	 * EventProcessorProvider instance.
	 */
	private final EventProcessorProvider eventProcessorProvider;

	/**
	 * The domain entity root instance.
	 */
	private DomainEntity root;
	/**
	 * Aggregate version.
	 */
	private Version version = Version.forInitialVersion();
	/**
	 * Entities used in this aggregate (included the domain entity root)
	 */
	private final Map<String, Entity> entities;
	/**
	 * Event ordinal.
	 */
	private long eventOrdinal = 0l;
	/**
	 * List of uncommitted changes.
	 */
	private final List<DomainEvent> uncommittedChanges = new ArrayList<DomainEvent>();

	/**
	 * Build a new instance of DomainAggregate with specified root entity.
	 * 
	 * @param eventProcessorProvider
	 *            event Processor Provider instance
	 * @throws NullPointerException
	 *             if eventProcessorProvider or domainEntity is null
	 */
	public DomainAggregate(final EventProcessorProvider eventProcessorProvider) throws NullPointerException {
		super();
		this.eventProcessorProvider = Preconditions.checkNotNull(eventProcessorProvider);
		entities = Maps.newHashMap();
	}

	/**
	 * @see org.intelligentsia.dowsers.event.DomainEventProvider#getIdentifier()
	 */
	@Override
	public String getIdentity() {
		return root.getIdentity();
	}

	/**
	 * @see org.intelligentsia.dowsers.event.DomainEventProvider#getVersion()
	 */
	@Override
	public Version getVersion() {
		return version;
	}

	/**
	 * It is basically apply events of the given aggregate.
	 * 
	 * @param history
	 *            events history
	 */
	@Override
	public void loadFromHistory(final Iterable<? extends DomainEvent> history, final Version version) throws IllegalStateException {
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
		this.version = version;
	}

	@Override
	public Collection<DomainEvent> getUncommittedChanges() {
		return uncommittedChanges;
	}

	@Override
	public boolean hasUncommittedChanges() {
		return !uncommittedChanges.isEmpty();
	}

	@Override
	public void markChangesCommitted(Version version) {
		eventOrdinal = 0l;
		this.version = version;
		uncommittedChanges.clear();
	}

	/**
	 * Indicates whether this aggregate has been marked as deleted. When
	 * <code>true</code>, it is an instruction to the repository to remove this
	 * instance at an appropriate time.
	 * <p/>
	 * Repositories should not return any instances of Aggregates that return
	 * <code>true</code> on <code>isDeleted()</code>.
	 * 
	 * @return <code>true</code> if this aggregate was marked as deleted,
	 *         otherwise <code>false</code>.
	 */
	//TODO implements this
	boolean isDeleted() {
		return true;
	}

	/**
	 * @see org.intelligentsia.dowsers.util.Registry#register(java.lang.Object)
	 */
	@Override
	public void register(final LocalDomainEntity object) throws NullPointerException {
		// add in map
		entities.put(object.getIdentity(), object);
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.Aggregate#registerRoot(org.intelligentsia.dowsers.domain.DomainEntity)
	 */
	@Override
	public void registerRoot(final DomainEntity domainEntity) throws NullPointerException, IllegalStateException {
		// check root not ever set
		Preconditions.checkState(!hasRootRegistered(), "domain entity root ever set");
		// set root member
		root = domainEntity;
		// add in map
		entities.put(root.getIdentity(), root);
	}

	/**
	 * @return true if a root is registered.
	 */
	public boolean hasRootRegistered() {
		return root != null;
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.Aggregate#getDomainEventInvoker(org.intelligentsia.dowsers.domain.Entity)
	 */
	@Override
	public DomainEventInvoker getDomainEventInvoker(final Entity entity) throws NullPointerException {
		// check entity previously registered
		Preconditions.checkState(entities.containsKey(entity.getIdentity()));
		// get specific event processor
		final EventProcessor eventProcessor = eventProcessorProvider.get(entity.getClass());
		// set domain invoker
		return new DefaultDomainEventInvoker(eventProcessor, entity);
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
		 *            event processor instance
		 * @param target
		 *            target instance
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
		 * @see org.intelligentsia.dowsers.event.DomainEventInvoker#apply(org.intelligentsia.dowsers.event.DomainEvent)
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
