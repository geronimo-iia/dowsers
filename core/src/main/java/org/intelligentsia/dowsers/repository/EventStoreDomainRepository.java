/**
 * 
 */
package org.intelligentsia.dowsers.repository;

import org.intelligentsia.dowsers.container.DomainEntityFactory;
import org.intelligentsia.dowsers.domain.AbstractDomainRepository;
import org.intelligentsia.dowsers.domain.ConcurrencyException;
import org.intelligentsia.dowsers.domain.DomainEntity;
import org.intelligentsia.dowsers.domain.DomainEntityNotFoundException;
import org.intelligentsia.dowsers.domain.Version;
import org.intelligentsia.dowsers.events.DomainAggregate;
import org.intelligentsia.dowsers.events.DomainEventProvider;
import org.intelligentsia.dowsers.repository.eventstore.DomainEventStore;

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
	 * DomainEventStore instance.
	 */
	private final DomainEventStore domainEventStore;

	/**
	 * Build a new instance of EventStoreDomainRepository.
	 * 
	 * @param domainEventStore
	 * @param domainEntityFactory
	 */
	public EventStoreDomainRepository(final DomainEventStore domainEventStore, final DomainEntityFactory domainEntityFactory) {
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
		// instanciate a new domain entity
		final T entity = domainEntityFactory.create(Preconditions.checkNotNull(expectedType));
		// retreive DomainEventProvider
		final DomainEventProvider domainEventProvider = getDomainAggregate(entity);
		// retreive domain event stream
		final DomainEventStream domainEventStream = new DomainEventStream();
		domainEventStore.loadDomainEventsFromLatestStreamVersion(identity, domainEventStream);
		// load from history
		domainEventProvider.loadFromHistory(domainEventStream.getDomainEvents(), Version.forSpecificVersion(domainEventStream.getVersion()));
		return entity;
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.DomainRepository#store(org.intelligentsia.dowsers.domain.DomainEntity)
	 */
	@Override
	public <T extends DomainEntity> void store(final T domainEntity) throws NullPointerException, ConcurrencyException {
		// retreive DomainEventProvider.
		final DomainEventProvider domainEventProvider = getDomainAggregate(domainEntity);
		// increment version
		domainEventProvider.incrementVersion();
		// create DomainEventStream
		final DomainEventStream domainEventStream = new DomainEventStream();
		domainEventStream.setVersion(domainEventProvider.getVersion().toLong());
		domainEventStream.setDomainEvents(domainEventProvider.getUncommittedChanges());
		// store
		domainEventStore.storeDomainEventsIntoStream(domainEventProvider.getIdentity(), domainEventStream);
		// mark change as committed
		domainEventProvider.markChangesCommitted();
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
