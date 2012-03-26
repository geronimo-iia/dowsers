/**
 * 
 */
package org.intelligentsia.dowsers.events;

import java.util.List;

import org.intelligentsia.dowsers.domain.DomainEvent;

/**
 * IgnoreEventPublisher implements a dummy EventPublisher: all events are ignored.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class IgnoreEventPublisher implements EventPublisher {

	/**
	 * All events are ignored.
	 * 
	 * @see org.intelligentsia.dowsers.events.EventPublisher#publish(java.util.List)
	 */
	@Override
	public <T extends DomainEvent> void publish(List<T> events) {
		// nothing to do
	}

}
