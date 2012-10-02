/**
 *        Licensed to the Apache Software Foundation (ASF) under one
 *        or more contributor license agreements.  See the NOTICE file
 *        distributed with this work for additional information
 *        regarding copyright ownership.  The ASF licenses this file
 *        to you under the Apache License, Version 2.0 (the
 *        "License"); you may not use this file except in compliance
 *        with the License.  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing,
 *        software distributed under the License is distributed on an
 *        "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *        KIND, either express or implied.  See the License for the
 *        specific language governing permissions and limitations
 *        under the License.
 *
 */
/**
 * 
 */
package org.intelligentsia.dowsers.eventstore.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.intelligentsia.dowsers.domain.ConcurrencyException;
import org.intelligentsia.dowsers.event.DomainEvent;
import org.intelligentsia.dowsers.eventstore.EmptyResultException;
import org.intelligentsia.dowsers.eventstore.EventStore;
import org.intelligentsia.dowsers.eventstore.StreamEverExistsException;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
 * MemoryMapEventStore implement a full EventStore in memory using underlaying Map..
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MemoryMapEventStore<EventType> implements EventStore<EventType> {

	/**
	 * Map of Identity, MemoryEventStream.
	 */
	private final Map<String, MemoryEventStream> memoryEventStreams;

	/**
	 * Build a new instance of MemoryMapEventStore.
	 */
	public MemoryMapEventStore() {
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
		if (memoryEventStream.currentVersion != expectedVersion) {
			throw new ConcurrencyException(identity, "");
		}
		return memoryEventStream.add(expectedVersion++, events).currentVersion;
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#loadFromLatestVersion(java.lang.String,
	 *      java.util.Collection)
	 */
	@Override
	public long loadFromLatestVersion(final String identity, final Collection<EventType> events) throws EmptyResultException {
		return getEventStream(identity).load(events).currentVersion;
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#currentVersion(java.lang.String)
	 */
	@Override
	public long currentVersion(final String identity) throws EmptyResultException {
		return getEventStream(identity).currentVersion;
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
		public final String identity;
		// private final String name;
		public final List<MemoryEvents> memoryEvents;
		public long currentVersion;

		public MemoryEventStream(final String identity, final long version, final String name, final Collection<Object> initialEvents) {
			super();
			this.identity = Preconditions.checkNotNull(identity);
			this.currentVersion = version;
			// this.name = name;
			memoryEvents = new ArrayList<MemoryEvents>();
			memoryEvents.add(new MemoryEvents(currentVersion, initialEvents));
		}

		public MemoryEventStream add(final long version, final Collection<Object> initialEvents) {
			this.currentVersion = version;
			memoryEvents.add(new MemoryEvents(currentVersion, initialEvents));
			Collections.sort(memoryEvents);
			return this;
		}

		public MemoryEventStream load(final Collection<Object> dest) {
			for (final MemoryEvents e : memoryEvents) {
				dest.addAll(e.events);
			}
			return this;
		}

		public MemoryEventStream loadUpToVersion(final long version, final Collection<Object> dest) {
			for (final MemoryEvents e : memoryEvents) {
				if (e.version <= version) {
					dest.addAll(e.events);
				}
			}
			return this;
		}

		public MemoryEventStream loadFromVersion(final long version, final Collection<Object> dest) {
			for (final MemoryEvents e : memoryEvents) {
				if (e.version > version) {
					dest.addAll(e.events);
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

 
	}

	/**
	 * 
	 * Event naturally ordered on version
	 * 
	 * 
	 */
	private static class MemoryEvents implements Comparable<MemoryEvents> {

		public final long version;
		public final Collection<? extends DomainEvent> events;

		/**
		 * Build a new instance of Event.
		 * 
		 * @param version
		 *            stream version of which memoryEvents sequence apply
		 * @param memoryEvents
		 *            ordered memoryEvents
		 */
		public MemoryEvents(final long version, final Collection<? extends DomainEvent> events) {
			super();
			this.version = version;
			this.events = Preconditions.checkNotNull(events);
		}

		@Override
		public int compareTo(final MemoryEvents o) {
			return (int) (version - o.version);
		}

		 
	}

}
