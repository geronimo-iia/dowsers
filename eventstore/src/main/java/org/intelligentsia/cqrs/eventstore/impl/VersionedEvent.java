package org.intelligentsia.cqrs.eventstore.impl;

/**
 * VersionedEvent.
 * 
 * @author JGT
 * 
 */
public class VersionedEvent<E> {

	private final long version;
	private final long timestamp;
	private final E event;

	/**
	 * Build a new instance of VersionedEvent.
	 * 
	 * @param version
	 * @param timestamp
	 * @param event
	 */
	public VersionedEvent(final long version, final long timestamp, final E event) {
		this.version = version;
		this.timestamp = timestamp;
		this.event = event;
	}

	public long getVersion() {
		return version;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public E getEvent() {
		return event;
	}
}