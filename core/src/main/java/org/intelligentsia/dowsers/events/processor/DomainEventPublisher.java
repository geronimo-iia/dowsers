/**
 * 
 */
package org.intelligentsia.dowsers.events.processor;

import java.util.List;

import org.intelligentsia.dowsers.events.DomainEvent;


/**
 * DomainEventPublisher.
 * 
 * @author jgt
 * 
 */
public interface DomainEventPublisher {

	public <T extends DomainEvent> void publish(List<T> events);
}
