/**
 * 
 */
package org.intelligentsia.dowsers.ed;

import java.util.UUID;

import org.intelligentsia.dowsers.domain.ConcurrencyException;
import org.intelligentsia.dowsers.domain.DomainEvent;
import org.intelligentsia.dowsers.eventstore.StreamEverExistsException;
import org.intelligentsia.dowsers.storage.EmptyResultException;

/**
 * DomainEventStore.
 * 
 * @author jgt
 * 
 */
public interface DomainEventStore {


	/**
	 * Creates a new event stream. The stream is initialized with the data and
	 * events provided by source.
	 * 
	 * @param identity
	 *            the identity of the stream to create.
	 * @param initialVersion initial version.
	 * @param domainEntityType domain entity type.
	 * @param events  initial events.
	 * @throws StreamEverExistsException
	 *             a stream with the specified id already exists.
	 */
	public void createEventStream(UUID identity, long initialVersion, Class<?> domainEntityType, Iterable<? extends DomainEvent> events) throws StreamEverExistsException;


	public void appendEventsIntoStream(UUID streamId, Class<?> domainEntityType, Iterable<? extends DomainEvent> events) throws EmptyResultException, ConcurrencyException, IllegalArgumentException;

	public Iterable<? extends DomainEvent> loadAllDomainEvents(UUID identity, Class<?> domainEntityType) throws EmptyResultException;

}
