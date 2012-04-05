/**
 * 
 */
package org.intelligentsia.dowsers.eventstore.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.intelligentsia.dowsers.domain.ConcurrencyException;
import org.intelligentsia.dowsers.eventstore.EmptyResultException;
import org.intelligentsia.dowsers.eventstore.EventStore;
import org.intelligentsia.dowsers.eventstore.StreamEverExistsException;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
 * MapEventStore implement a full EventStore in memory using underlaying Map..
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MapEventStore<EventType> implements EventStore<EventType> {

	/**
	 * Map of Identity, MemoryEventStream.
	 */
	private final Map<String, MemoryEventStream> memoryEventStreams;

	/**
	 * Build a new instance of MapEventStore.
	 */
	public MapEventStore() {
		super();
		memoryEventStreams = Maps.newHashMap();
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#create(java.lang.String,
	 *      long, java.lang.String, java.util.Collection)
	 */
	@Override
	public void create(final String identity, final long initialVersion, final String name, final Collection<EventType> initialEvents) throws StreamEverExistsException {
		if (memoryEventStreams.containsKey(identity)) {
			throw new StreamEverExistsException(identity);
		}
		memoryEventStreams.put(identity, new MemoryEventStream(identity, initialVersion, name, initialEvents));
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#store(java.lang.String,
	 *      long, java.util.Collection)
	 */
	@Override
	public long store(final String identity, long expectedVersion, final Collection<EventType> events) throws EmptyResultException, ConcurrencyException, IllegalArgumentException {
		final MemoryEventStream memoryEventStream = getEventStream(identity);
		if (memoryEventStream.getCurrentVersion() != expectedVersion) {
			throw new ConcurrencyException(identity, "");
		}
		return memoryEventStream.add(expectedVersion++, events).getCurrentVersion();
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#loadFromLatestVersion(java.lang.String,
	 *      java.util.Collection)
	 */
	@Override
	public long loadFromLatestVersion(final String identity, final Collection<EventType> events) throws EmptyResultException {
		return getEventStream(identity).load(events).getCurrentVersion();
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#currentVersion(java.lang.String)
	 */
	@Override
	public long currentVersion(final String identity) throws EmptyResultException {
		return getEventStream(identity).getCurrentVersion();
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#loadUptoVersion(java.lang.String,
	 *      long, java.util.Collection)
	 */
	@Override
	public void loadUptoVersion(final String identity, final long version, final Collection<EventType> events) throws EmptyResultException {
		getEventStream(identity).loadUpToVersion(version, events);
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#loadFromVersion(java.lang.String,
	 *      long, java.util.Collection)
	 */
	@Override
	public void loadFromVersion(final String identity, final long version, final Collection<EventType> events) throws EmptyResultException {
		getEventStream(identity).loadFromVersion(version, events);
	}

	protected MemoryEventStream getEventStream(final String identity) {
		if (!memoryEventStreams.containsKey(identity)) {
			throw new EmptyResultException(identity);
		}
		return memoryEventStreams.get(identity);
	}

	/**
	 * MemoryEventStream.
	 * 
	 */
	private class MemoryEventStream {
		private final String identity;
		// private final String name;
		private final List<MemoryEvents> memoryEvents;
		private long currentVersion;

		public MemoryEventStream(final String identity, final long version, final String name, final Collection<EventType> initialEvents) {
			super();
			this.identity = Preconditions.checkNotNull(identity);
			this.currentVersion = version;
			// this.name = name;
			memoryEvents = new ArrayList<MemoryEvents>();
			memoryEvents.add(new MemoryEvents(currentVersion, initialEvents));
		}

		public MemoryEventStream add(final long version, final Collection<EventType> initialEvents) {
			this.currentVersion = version;
			memoryEvents.add(new MemoryEvents(currentVersion, initialEvents));
			Collections.sort(memoryEvents);
			return this;
		}

		public MemoryEventStream load(final Collection<EventType> dest) {
			for (final MemoryEvents e : memoryEvents) {
				dest.addAll(e.getEvents());
			}
			return this;
		}

		public MemoryEventStream loadUpToVersion(final long version, final Collection<EventType> dest) {
			for (final MemoryEvents e : memoryEvents) {
				if (e.getVersion() <= version) {
					dest.addAll(e.getEvents());
				}
			}
			return this;
		}

		public MemoryEventStream loadFromVersion(final long version, final Collection<EventType> dest) {
			for (final MemoryEvents e : memoryEvents) {
				if (e.getVersion() > version) {
					dest.addAll(e.getEvents());
				}
			}
			return this;
		}

		/**
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return identity.hashCode();
		}

		/**
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final MemoryEventStream other = (MemoryEventStream) obj;
			if (identity == null) {
				if (other.identity != null) {
					return false;
				}
			} else if (!identity.equals(other.identity)) {
				return false;
			}
			return true;
		}

		/**
		 * @return the currentVersion
		 */
		public long getCurrentVersion() {
			return currentVersion;
		}

	}

	/**
	 * 
	 * Event naturally ordered on version
	 * 
	 * 
	 */
	private class MemoryEvents implements Comparable<MemoryEvents> {

		private final long version;
		private final Collection<EventType> events;

		/**
		 * Build a new instance of Event.
		 * 
		 * @param version
		 *            stream version of which memoryEvents sequence apply
		 * @param memoryEvents
		 *            ordered memoryEvents
		 */
		public MemoryEvents(final long version, final Collection<EventType> events) {
			super();
			this.version = version;
			this.events = Preconditions.checkNotNull(events);
		}

		@Override
		public int compareTo(final MemoryEvents o) {
			return (int) (version - o.version);
		}

		/**
		 * @return the version
		 */
		public long getVersion() {
			return version;
		}

		/**
		 * @return the memoryEvents
		 */
		public Collection<EventType> getEvents() {
			return events;
		}

	}

}
