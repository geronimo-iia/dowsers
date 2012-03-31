/**
 * 
 */
package org.intelligentsia.dowsers.repository.eventstore.memory;

import java.util.Map;

import org.intelligentsia.dowsers.domain.ConcurrencyException;
import org.intelligentsia.dowsers.repository.eventstore.DomainEventStore;
import org.intelligentsia.dowsers.repository.eventstore.DomainEventStreamSink;
import org.intelligentsia.dowsers.repository.eventstore.DomainEventStreamSource;
import org.intelligentsia.dowsers.repository.eventstore.EmptyResultException;
import org.intelligentsia.dowsers.repository.eventstore.StreamEverExistsException;

import com.google.common.collect.Maps;

/**
 * MemoryDomainEventStore provide a memory implementation of DomainEventStore interface.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MemoryDomainEventStore implements DomainEventStore {

	private final Map<String, MemoryDomainEventStream> memoryDomainEventStreams;

	/**
	 * Build a new instance of <code>MemoryDomainEventStore</code>
	 */
	public MemoryDomainEventStore() {
		super();
		memoryDomainEventStreams = Maps.newHashMap();
	}

	@Override
	public void createDomainEventStream(String identity, DomainEventStreamSource source)
			throws StreamEverExistsException {
		if (memoryDomainEventStreams.containsKey(identity)) {
			throw new StreamEverExistsException(identity);
		}
		memoryDomainEventStreams.put(identity,
				new MemoryDomainEventStream(source.getVersion(), source.getDomainEvents()));
	}

	@Override
	public void storeDomainEventsIntoStream(String identity, final long expectedVersion, DomainEventStreamSource source)
			throws EmptyResultException, ConcurrencyException, IllegalArgumentException {
		final MemoryDomainEventStream stream = getDomainEventStream(identity);
		if (stream.getVersion() != expectedVersion) {
			throw new ConcurrencyException(identity, "actual version: " + stream.getVersion() + ", expected version: "
					+ expectedVersion);
		}
		stream.setVersion(source.getVersion());
		stream.addDomainEvents(source.getDomainEvents());
	}

	/**
	 * @see org.intelligentsia.dowsers.repository.eventstore.DomainEventStore#loadDomainEventsFromLatestStreamVersion(java.lang.String,
	 *      org.intelligentsia.dowsers.repository.eventstore.DomainEventStreamSink)
	 */
	@Override
	public void loadDomainEventsFromLatestStreamVersion(String identity, DomainEventStreamSink sink)
			throws EmptyResultException {
		final MemoryDomainEventStream stream = getDomainEventStream(identity);
		sink.setVersion(stream.getVersion());
		sink.setDomainEvents(stream.getDomainEvents());
	}

	public MemoryDomainEventStream getDomainEventStream(final String identity) throws EmptyResultException {
		final MemoryDomainEventStream memoryDomainEventStream = memoryDomainEventStreams.get(identity);
		if (memoryDomainEventStream == null) {
			throw new EmptyResultException(identity, "unknown event stream ");
		}
		return memoryDomainEventStream;
	}
}
