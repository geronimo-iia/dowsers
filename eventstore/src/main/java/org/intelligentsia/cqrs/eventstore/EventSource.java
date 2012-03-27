package org.intelligentsia.cqrs.eventstore;

import java.util.List;

/**
 * Source for change events. Every event source has its own sequence of events
 * that are stored by the {@link EventStore}. In DDD your aggregates are the
 * event sources.
 */
public interface EventSource<EventType> {
	/**
	 * @return
	 */
	public String getType();

	/**
	 * @return
	 */
	public long getVersion();

	/**
	 * @return
	 */
	public long getTimestamp();

	/**
	 * @return
	 */
	public List<? extends EventType> getEvents();

}
