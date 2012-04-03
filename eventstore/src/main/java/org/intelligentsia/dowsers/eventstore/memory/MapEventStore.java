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

	private Map<String, EventStream> eventStreams;

	/**
	 * Build a new instance of MapEventStore.
	 */
	public MapEventStore() {
		super();
		eventStreams = Maps.newHashMap();
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#create(java.lang.String,
	 *      long, java.lang.String, java.util.Collection)
	 */
	@Override
	public void create(String identity, long initialVersion, String name, Collection<EventType> initialEvents) throws StreamEverExistsException {
		if (eventStreams.containsKey(identity)) {
			throw new StreamEverExistsException(identity);
		}
		eventStreams.put(identity, new EventStream(identity, initialVersion, name, initialEvents));
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#store(java.lang.String,
	 *      long, java.util.Collection)
	 */
	@Override
	public long store(String identity, long expectedVersion, Collection<EventType> events) throws EmptyResultException, ConcurrencyException, IllegalArgumentException {
		EventStream eventStream = getEventStream(identity);
		if (eventStream.getCurrentVersion() != expectedVersion) {
			throw new ConcurrencyException(identity, "");
		}
		return eventStream.add(expectedVersion++, events).getCurrentVersion();
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#loadFromLatestVersion(java.lang.String,
	 *      java.util.Collection)
	 */
	@Override
	public long loadFromLatestVersion(String identity, Collection<EventType> events) throws EmptyResultException {
		return getEventStream(identity).load(events).getCurrentVersion();
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#currentVersion(java.lang.String)
	 */
	@Override
	public long currentVersion(String identity) throws EmptyResultException {
		return getEventStream(identity).getCurrentVersion();
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#loadUptoVersion(java.lang.String,
	 *      long, java.util.Collection)
	 */
	@Override
	public void loadUptoVersion(String identity, long version, Collection<EventType> events) throws EmptyResultException {
		getEventStream(identity).loadUpToVersion(version, events);
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#loadFromVersion(java.lang.String,
	 *      long, java.util.Collection)
	 */
	@Override
	public void loadFromVersion(String identity, long version, Collection<EventType> events) throws EmptyResultException {
		getEventStream(identity).loadFromVersion(version, events);
	}

	protected EventStream getEventStream(String identity) {
		if (!eventStreams.containsKey(identity)) {
			throw new EmptyResultException(identity);
		}
		return eventStreams.get(identity);
	}

	/**
	 * 
	 * EventStream.
	 * 
	 */
	public class EventStream {
		private final String identity;
		// private final String name;
		private final List<Events> events;
		private long currentVersion;

		public EventStream(String identity, long version, String name, Collection<EventType> initialEvents) {
			super();
			this.identity = Preconditions.checkNotNull(identity);
			this.currentVersion = version;
			// this.name = name;
			events = new ArrayList<Events>();
			events.add(new Events(currentVersion, initialEvents));
		}

		public EventStream add(long version, Collection<EventType> initialEvents) {
			this.currentVersion = version;
			events.add(new Events(currentVersion, initialEvents));
			Collections.sort(events);
			return this;
		}

		public EventStream load(Collection<EventType> dest) {
			for (Events e : events) {
				dest.addAll(e.getEvents());
			}
			return this;
		}

		public EventStream loadUpToVersion(final long version, Collection<EventType> dest) {
			for (Events e : events) {
				if (e.getVersion() <= version)
					dest.addAll(e.getEvents());
			}
			return this;
		}

		public EventStream loadFromVersion(final long version, Collection<EventType> dest) {
			for (Events e : events) {
				if (e.getVersion() > version)
					dest.addAll(e.getEvents());
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
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			EventStream other = (EventStream) obj;
			if (identity == null) {
				if (other.identity != null)
					return false;
			} else if (!identity.equals(other.identity))
				return false;
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
	 * Event naturaly ordered on version
	 * 
	 * 
	 */
	public class Events implements Comparable<Events> {

		private final long version;
		private final Collection<EventType> events;

		/**
		 * Build a new instance of Event.
		 * 
		 * @param version
		 * @param events
		 */
		public Events(long version, Collection<EventType> events) {
			super();
			this.version = version;
			this.events = Preconditions.checkNotNull(events);
		}

		@Override
		public int compareTo(Events o) {
			return (int) (version - o.version);
		}

		/**
		 * @return the version
		 */
		public long getVersion() {
			return version;
		}

		/**
		 * @return the events
		 */
		public Collection<EventType> getEvents() {
			return events;
		}

	}

}
