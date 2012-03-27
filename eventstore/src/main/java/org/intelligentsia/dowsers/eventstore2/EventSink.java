package org.intelligentsia.dowsers.eventstore2;

/**
 * Sink for change events. Every event stream has its own sequence of events
 * that are stored by the {@link EventStore}. When loading events the event
 * stream meta data and events are send to this {@link EventSink}.
 */
public interface EventSink<EventType> {

	/**
	 * @param type
	 */
	public void setType(String type);

	/**
	 * @param version
	 */
	public void setVersion(long version);

	/**
	 * @param timestamp
	 */
	public void setTimestamp(long timestamp);

	/**
	 * @param events
	 */
	public void setEvents(Iterable<? extends EventType> events);

}
