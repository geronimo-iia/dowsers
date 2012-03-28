package org.intelligentsia.dowsers.eventstore;

import java.util.UUID;

import org.intelligentsia.dowsers.domain.ConcurrencyException;
import org.intelligentsia.dowsers.storage.EmptyResultException;

/**
 * Stores and tracks ordered streams of events.
 * 
 * 
 * Check OptimisticLocking necessity
 * 
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
	public void createEventStream(UUID streamId, EventSource<EventType> source) throws StreamEverExistsException;

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
	public void storeEventsIntoStream(UUID streamId, long expectedVersion, EventSource<EventType> source) throws EmptyResultException, ConcurrencyException, IllegalArgumentException;

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
	public void loadEventsFromLatestStreamVersion(UUID streamId, EventSink<EventType> sink) throws EmptyResultException;

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
	public void loadEventsFromExpectedStreamVersion(UUID streamId, long expectedVersion, EventSink<EventType> sink) throws EmptyResultException, ConcurrencyException;

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
	public void loadEventsFromStreamUptoVersion(UUID streamId, long version, EventSink<EventType> sink) throws EmptyResultException;

}
