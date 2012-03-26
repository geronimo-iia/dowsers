/**
 * 
 */
package org.intelligentsia.dowsers.events;

import java.util.List;

import org.intelligentsia.dowsers.domain.DomainEvent;

/**
 * EventPublisher.
 * 
 * @author jgt
 * 
 */
public interface EventPublisher {

	public <T extends DomainEvent> void publish(List<T> events);
}
