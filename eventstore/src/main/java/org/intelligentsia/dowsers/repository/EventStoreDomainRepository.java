/**
 * 
 */
package org.intelligentsia.dowsers.repository;

import java.util.ArrayList;
import java.util.List;

import org.intelligentsia.dowsers.container.DomainEntityFactory;
import org.intelligentsia.dowsers.domain.AbstractDomainRepository;
import org.intelligentsia.dowsers.domain.ConcurrencyException;
import org.intelligentsia.dowsers.domain.DomainEntity;
import org.intelligentsia.dowsers.domain.DomainEntityNotFoundException;
import org.intelligentsia.dowsers.domain.Version;
import org.intelligentsia.dowsers.event.DomainAggregate;
import org.intelligentsia.dowsers.event.DomainEvent;
import org.intelligentsia.dowsers.event.DomainEventProvider;
import org.intelligentsia.dowsers.eventstore.EventStore;

import com.google.common.base.Preconditions;

/**
 * EventStoreDomainRepository.
 * 
 * “Captures all changes to an application state as a sequence of events.”
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EventStoreDomainRepository extends AbstractDomainRepository {

	/**
	 * EventStore instance.
	 */
	private final EventStore<DomainEvent> domainEventStore;

	/**
	 * Build a new instance of EventStoreDomainRepository.
	 * 
	 * @param domainEventStore
	 * @param domainEntityFactory
	 */
	public EventStoreDomainRepository(final EventStore<DomainEvent> domainEventStore, final DomainEntityFactory domainEntityFactory) {
		super(domainEntityFactory);
		this.domainEventStore = Preconditions.checkNotNull(domainEventStore);
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.DomainRepository#find(java.lang.Class,
	 *      java.util.UUID)
	 */
	@Override
	public <T extends DomainEntity> T find(final Class<T> expectedType, final String identity) throws DomainEntityNotFoundException, NullPointerException {
		Preconditions.checkNotNull(identity);
		// Instantiate a new domain entity
		final T entity = domainEntityFactory.create(Preconditions.checkNotNull(expectedType));
		// Retrieve DomainEventProvider
		final DomainEventProvider domainEventProvider = getDomainAggregate(entity);
		// Retrieve domain event stream
		List<DomainEvent> events = new ArrayList<DomainEvent>();
		Version version = Version.forSpecificVersion(domainEventStore.loadFromLatestVersion(identity, events));
		// load from history
		domainEventProvider.loadFromHistory(events, version);
		return entity;
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.DomainRepository#store(org.intelligentsia.dowsers.domain.DomainEntity)
	 */
	@Override
	public <T extends DomainEntity> void store(final T domainEntity) throws NullPointerException, ConcurrencyException {
		// Retrieve DomainEventProvider.
		final DomainEventProvider domainEventProvider = getDomainAggregate(domainEntity);
		// expected version on store 
		long expectedVersion = domainEventProvider.getVersion().toLong();
		// store
		Version finalVersion = Version.forSpecificVersion(domainEventStore.store(domainEventProvider.getIdentity(), expectedVersion, domainEventProvider.getUncommittedChanges()));
		// mark change as committed
		domainEventProvider.markChangesCommitted(finalVersion);
	}

	/**
	 * @param domainEntity
	 *            domain entity instance
	 * @return associated DomainAggregate instance with specified domain entity.
	 * @throws NullPointerException
	 *             if domainEntity is null
	 */
	protected <T extends DomainEntity> DomainAggregate getDomainAggregate(final T domainEntity) throws NullPointerException {
		return (DomainAggregate) getAggregate(Preconditions.checkNotNull(domainEntity));
	}
}
