package org.intelligentsia.cqrs.eventstore.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.intelligentsia.cqrs.eventstore.ConcurrencyException;
import org.intelligentsia.cqrs.eventstore.EmptyResultException;
import org.intelligentsia.cqrs.eventstore.EventSink;
import org.intelligentsia.cqrs.eventstore.EventSource;
import org.intelligentsia.cqrs.eventstore.EventStore;
import org.intelligentsia.cqrs.eventstore.StreamEverExistsException;

/**
 * Stores and tracks ordered streams of events.
 */
public class MemoryEventStore<E> implements EventStore<E> {

	public Map<UUID, EventStream<E>> eventStreams = new HashMap<UUID, EventStream<E>>();

	@Override
	public void createEventStream(final UUID streamId, final EventSource<E> source) throws StreamEverExistsException {
		if (eventStreams.containsKey(streamId)) {
			throw new StreamEverExistsException(streamId);
		}
		eventStreams.put(streamId, new EventStream<E>(source.getType(), source.getVersion(), source.getTimestamp(), source.getEvents()));
	}

	@Override
	public void storeEventsIntoStream(final UUID streamId, final long expectedVersion, final EventSource<E> source) throws EmptyResultException, ConcurrencyException, IllegalArgumentException {
		final EventStream<E> stream = getStream(streamId);
		if (stream.getVersion() != expectedVersion) {
			throw new ConcurrencyException(streamId, "actual version: " + stream.getVersion() + ", expected version: " + expectedVersion);
		}
		stream.setVersion(source.getVersion());
		stream.setTimestamp(source.getTimestamp());
		stream.addEvents(source.getEvents());
	}

	@Override
	public void loadEventsFromLatestStreamVersion(final UUID streamId, final EventSink<E> sink) throws EmptyResultException {
		final EventStream<E> stream = getStream(streamId);
		sink.setType(stream.getType());
		stream.sendEventsAtVersionToSink(stream.getVersion(), sink);
	}

	@Override
	public void loadEventsFromExpectedStreamVersion(final UUID streamId, final long expectedVersion, final EventSink<E> sink) throws EmptyResultException, ConcurrencyException {
		final EventStream<E> stream = getStream(streamId);
		if (stream.getVersion() != expectedVersion) {
			throw new ConcurrencyException(streamId, "actual version: " + stream.getVersion() + ", expected version: " + expectedVersion);
		}
		sink.setType(stream.getType());
		stream.sendEventsAtVersionToSink(stream.getVersion(), sink);
	}

	@Override
	public void loadEventsFromStreamUptoVersion(final UUID streamId, final long version, final EventSink<E> sink) throws EmptyResultException {
		final EventStream<E> stream = getStream(streamId);
		sink.setType(stream.getType());

		final long actualVersion = Math.min(stream.getVersion(), version);
		stream.sendEventsAtVersionToSink(actualVersion, sink);
	}

	@Override
	public void loadEventsFromStreamUptoTimestamp(final UUID streamId, final long timestamp, final EventSink<E> sink) throws EmptyResultException {
		final EventStream<E> stream = getStream(streamId);
		sink.setType(stream.getType());

		final long actualTimestamp = Math.min(stream.getTimestamp(), timestamp);
		stream.sendEventsAtTimestampToSink(actualTimestamp, sink);
	}

	public EventStream<E> getStream(final UUID streamId) {
		final EventStream<E> stream = eventStreams.get(streamId);
		if (stream == null) {
			throw new EmptyResultException(streamId, "unknown event stream " + streamId);
		}
		return stream;
	}

}
