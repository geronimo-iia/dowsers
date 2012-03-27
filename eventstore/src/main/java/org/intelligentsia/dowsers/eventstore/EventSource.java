package org.intelligentsia.dowsers.eventstore;

import java.util.List;

import org.intelligentsia.dowsers.domain.DomainEvent;

/**
 * Source for change events.
 * 
 * Every event source has its own sequence of events that are stored by the
 * {@link EventStore}. In DDD your aggregates are the event sources.
 */
public interface EventSource {
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
	public List<? extends DomainEvent> getEvents();

}
