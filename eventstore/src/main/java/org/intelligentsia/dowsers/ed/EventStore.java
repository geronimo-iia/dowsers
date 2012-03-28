package org.intelligentsia.dowsers.ed;

import java.util.UUID;

import org.intelligentsia.dowsers.domain.ConcurrencyException;
import org.intelligentsia.dowsers.domain.DomainEvent;
import org.intelligentsia.dowsers.eventstore.StreamEverExistsException;
import org.intelligentsia.dowsers.storage.EmptyResultException;

/**
 * Stores and tracks ordered streams of events.
 * 
 * 
 * Check OptimisticLocking necessity
 * 
 * @author jgt
 */
public interface EventStore<EventType> {

	/**
	 * Creates a new event stream. The stream is initialized with the data and
	 * events provided by source.
	 * 
	 * @param streamId
	 *            the stream id of the stream to create.
	 * @param source
	 *            provides the type, initial version, initial timestamp, and
	 *            initial events.
	 * @throws StreamEverExistsException
	 *             a stream with the specified id already exists.
	 */
	public void createEventStream(UUID streamId, long initialVersion, Class<?> entityTypeName, Iterable<? extends DomainEvent> events) throws StreamEverExistsException;

	/**
	 * Adds the events from source to the specified stream.
	 * 
	 * @param streamId
	 *            the stream id.
	 * @param expectedVersion
	 *            the expected version of the stream.
	 * @param source
	 *            the stream data and events source.
	 * @throws EmptyResultException
	 *             the specified stream does not exist.
	 * @throws ConcurrencyException
	 *             thrown when the expected version does not match the actual
	 *             version of the stream.
	 * @throws IllegalArgumentException
	 *             if expectedVersion is strictly lower or equal than current in
	 *             store
	 */
	public void storeEventsIntoStream(UUID streamId, long expectedVersion, Class<?> entityTypeName, Iterable<? extends DomainEvent> events) throws EmptyResultException, ConcurrencyException, IllegalArgumentException;

	/**
	 * 
	 * @param streamId
	 * @param entityTypeName
	 * @param events
	 * @throws EmptyResultException
	 * @throws ConcurrencyException
	 * @throws IllegalArgumentException
	 */
	public void appendEventsIntoStream(UUID streamId, Class<?> entityTypeName, Iterable<? extends DomainEvent> events) throws EmptyResultException, ConcurrencyException, IllegalArgumentException;

	public Iterable<? extends DomainEvent> loadAllDomainEvents(UUID identity, Class<?> entityTypeName) throws EmptyResultException;

	/**
	 * Loads the events associated with the stream into the provided sink.
	 * 
	 * @param streamId
	 *            the stream id
	 * @param sink
	 *            the sink to send the stream data and events to.
	 * @throws EmptyResultException
	 *             no stream with the specified id exists.
	 */
	public Iterable<? extends DomainEvent> loadEventsFromLatestStreamVersion(UUID streamId, Class<?> entityTypeName) throws EmptyResultException;

	/**
	 * Loads the events associated with the stream into the provided sink.
	 * 
	 * @param streamId
	 *            the stream id
	 * @param expectedVersion
	 *            the expected version of the stream.
	 * @param sink
	 *            the sink to send the stream data and events to.
	 * @throws EmptyResultException
	 *             no stream with the specified id exists.
	 * @throws ConcurrencyException
	 *             thrown when the expected version does not match the actual
	 *             version of the stream.
	 */
	public Iterable<? extends DomainEvent> loadEventsFromExpectedStreamVersion(UUID streamId, long expectedVersion, Class<?> entityTypeName) throws EmptyResultException, ConcurrencyException;

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
	public Iterable<? extends DomainEvent> loadEventsFromStreamUptoVersion(UUID streamId, long maximumVersion, Class<?> entityTypeName) throws EmptyResultException;

}
