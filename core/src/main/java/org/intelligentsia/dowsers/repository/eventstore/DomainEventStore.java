/**
 * 
 */
package org.intelligentsia.dowsers.repository.eventstore;

import java.util.UUID;

import org.intelligentsia.dowsers.domain.ConcurrencyException;

/**
 * DomainEventStore stores and tracks ordered streams of events.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface DomainEventStore {

	/**
	 * Creates a new event stream. The stream is initialized with the data and
	 * events provided by source.
	 * 
	 * @param identity
	 *            the stream id of the stream to create.
	 * @param source
	 *            provides the initial version and initial events.
	 * @throws StreamEverExistsException
	 *             a stream with the specified id already exists.
	 */
	public void createDomainEventStream(UUID identity, DomainEventStreamSource source) throws StreamEverExistsException;

	/**
	 * Adds the events from source to the specified stream.
	 * 
	 * @param identity
	 *            stream identity.
	 * @param source
	 *            provides the expected version of the stream and events to
	 *            store
	 * @throws EmptyResultException
	 *             the specified stream does not exist.
	 * @throws ConcurrencyException
	 *             thrown when the expected version does not match the actual
	 *             version of the stream.
	 * @throws IllegalArgumentException
	 *             if expectedVersion is strictly lower or equal than current in
	 *             store
	 */
	void storeDomainEventsIntoStream(UUID identity, DomainEventStreamSource source) throws EmptyResultException, ConcurrencyException, IllegalArgumentException;

	/**
	 * Loads the events associated with the stream into the provided sink.
	 * 
	 * @param streamId
	 *            the stream id
	 * @param sink
	 *            the sink to send the stream version and events to.
	 * @throws EmptyResultException
	 *             no stream with the specified id exists.
	 */
	public void loadDomainEventsFromLatestStreamVersion(UUID streamId, DomainEventStreamSink sink) throws EmptyResultException;

	/**
	 * Loads the events associated with the stream into the provided sink. Only
	 * the events that existed before and at the requested version are loaded.
	 * 
	 * @param streamId
	 *            the stream id
	 * @param version
	 *            the version (inclusive) of the event stream to load.
	 * @param sink
	 *            the sink to send the stream data and events to.
	 * @throws EmptyResultException
	 *             no stream with the specified id exists or the version is
	 *             lower than the initial version of the stream.
	 */
	// public void loadDomainEventsFromStreamUptoVersion(UUID streamId, long
	// version,
	// DomainEventStreamSink sink) throws EmptyResultException;

}
