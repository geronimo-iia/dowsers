/**
 * 
 */
package org.intelligentsia.dowsers.eventstore;

import java.util.List;
import java.util.UUID;

import org.intelligentsia.dowsers.domain.DomainEvent;

/**
 * DomainEventStreamSource represent Source for DomainEventStream.
 * 
 * Every DomainEventStream source has its own sequence of event. This represents
 * a stream of historical domain events.
 * 
 * The order of events in this stream must represent the actual chronological
 * order in which the events happened.
 * 
 * @author jgt
 * 
 */
public interface DomainEventStreamSource {

	/**
	 * @return domain entity type
	 */
	public String getDomainEntityType();

	/**
	 * @return domain entity identity
	 */
	public UUID getDomainentityIdentity();

	public long getVersion();

	public List<? extends DomainEvent> getEvents();
}
