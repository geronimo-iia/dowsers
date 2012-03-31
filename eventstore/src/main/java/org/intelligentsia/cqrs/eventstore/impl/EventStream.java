/**
 * 
 */
package org.intelligentsia.cqrs.eventstore.impl;

import java.util.ArrayList;
import java.util.List;

import org.intelligentsia.cqrs.eventstore.EmptyResultException;
import org.intelligentsia.cqrs.eventstore.EventSink;

/**
 * EventStream.
 * 
 * @author JGT
 * 
 */
public class EventStream<E> {
	private final String type;
	private long version;
	private long timestamp;
	private final List<VersionedEvent<E>> events = new ArrayList<VersionedEvent<E>>();

	public EventStream(final String type, final long version, final long timestamp, final Iterable<? extends E> initialEvents) {
		this.type = type;
		this.version = version;
		this.timestamp = timestamp;
		addEvents(initialEvents);
	}

	public void sendEventsAtVersionToSink(final long version, final EventSink<E> sink) {
		final List<E> result = new ArrayList<E>();
		VersionedEvent<E> lastEvent = null;
		for (final VersionedEvent<E> event : events) {
			if (event.getVersion() > version) {
				break;
			}
			lastEvent = event;
			result.add(event.getEvent());
		}

		sendEventsToSink(result, lastEvent, sink);
	}

	public void sendEventsAtTimestampToSink(final long timestamp, final EventSink<E> sink) {
		final List<E> result = new ArrayList<E>();
		VersionedEvent<E> lastEvent = null;
		for (final VersionedEvent<E> event : events) {
			if (event.getTimestamp() > timestamp) {
				break;
			}
			lastEvent = event;
			result.add(event.getEvent());
		}

		sendEventsToSink(result, lastEvent, sink);
	}

	private void sendEventsToSink(final List<E> events, final VersionedEvent<E> lastEvent, final EventSink<E> sink) {
		if (lastEvent == null) {
			throw new EmptyResultException("no event found for specified version or timestamp");
		}
		sink.setVersion(lastEvent.getVersion());
		sink.setTimestamp(lastEvent.getTimestamp());
		sink.setEvents(events);
	}

	public String getType() {
		return type;
	}

	public long getVersion() {
		return version;
	}

	/**
	 * Set version
	 * 
	 * @param version
	 * @throws IllegalArgumentException
	 *             if version argument is <= at current version
	 */
	public void setVersion(final long version) throws IllegalArgumentException {
		if (this.version > version) {
			throw new IllegalArgumentException("version cannot decrease");
		}
		this.version = version;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final long timestamp) {
		if (this.timestamp > timestamp) {
			throw new IllegalArgumentException("timestamp cannot decrease");
		}
		this.timestamp = timestamp;
	}

	public void addEvents(final Iterable<? extends E> eventsToAdd) {
		for (final E event : eventsToAdd) {
			this.events.add(new VersionedEvent<E>(this.version, this.timestamp, event));
		}
	}

}