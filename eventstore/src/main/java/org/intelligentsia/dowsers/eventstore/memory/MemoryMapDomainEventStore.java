/**
 * 
 */
package org.intelligentsia.dowsers.eventstore.memory;

import java.util.Map;

import org.intelligentsia.dowsers.domain.ConcurrencyException;
import org.intelligentsia.dowsers.eventstore.EventStore;
import org.intelligentsia.dowsers.eventstore.EventStreamSink;
import org.intelligentsia.dowsers.eventstore.EventStreamSource;
import org.intelligentsia.dowsers.eventstore.EmptyResultException;
import org.intelligentsia.dowsers.eventstore.StreamEverExistsException;

import com.google.common.collect.Maps;

/**
 * MemoryMapDomainEventStore provide a memory implementation of EventStore interface.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MemoryMapDomainEventStore<EventType> implements EventStore<EventType> {

	private final Map<String, MemoryDomainEventStream<EventType>> memoryDomainEventStreams;

	/**
	 * Build a new instance of <code>MemoryMapDomainEventStore</code>
	 */
	public MemoryMapDomainEventStore() {
		super();
		memoryDomainEventStreams = Maps.newHashMap();
	}

	@Override
	public void createDomainEventStream(String identity, EventStreamSource<EventType> source)
			throws StreamEverExistsException {
		if (memoryDomainEventStreams.containsKey(identity)) {
			throw new StreamEverExistsException(identity);
		}
		memoryDomainEventStreams.put(identity,
				new MemoryDomainEventStream<EventType>(source.getVersion(), source.getEvents(), source.getClassName()));
	}

	@Override
	public void storeDomainEventsIntoStream(String identity, final long expectedVersion,
			EventStreamSource<EventType> source) throws EmptyResultException, ConcurrencyException,
			IllegalArgumentException {
		final MemoryDomainEventStream<EventType> stream = getDomainEventStream(identity);
		if (stream.getVersion() != expectedVersion) {
			throw new ConcurrencyException(identity, "actual version: " + stream.getVersion() + ", expected version: "
					+ expectedVersion);
		}
		stream.setVersion(source.getVersion());
		stream.addEvents(source.getEvents());
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#loadDomainEventsFromLatestStreamVersion(java.lang.String,
	 *      org.intelligentsia.dowsers.eventstore.EventStreamSink)
	 */
	@Override
	public void loadDomainEventsFromLatestStreamVersion(String identity, EventStreamSink<EventType> sink)
			throws EmptyResultException {
		final MemoryDomainEventStream<EventType> stream = getDomainEventStream(identity);
		sink.setVersion(stream.getVersion());
		sink.setEvents(stream.getEvents());
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#loadDomainEventsFromStreamUptoVersion(java.lang.String,
	 *      long, org.intelligentsia.dowsers.eventstore.EventStreamSink)
	 */
	@Override
	public void loadDomainEventsFromStreamUptoVersion(String identity, long version, EventStreamSink<EventType> sink)
			throws EmptyResultException {
	}

	public MemoryDomainEventStream<EventType> getDomainEventStream(final String identity) throws EmptyResultException {
		final MemoryDomainEventStream<EventType> memoryDomainEventStream = memoryDomainEventStreams.get(identity);
		if (memoryDomainEventStream == null) {
			throw new EmptyResultException(identity, "unknown event stream ");
		}
		return memoryDomainEventStream;
	}
}
