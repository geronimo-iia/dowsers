/**
 * 
 */
package org.intelligentsia.dowsers.eventstore;

import java.util.List;

import org.intelligentsia.dowsers.domain.DomainEvent;

/**
 * FakeEventPublisher implements a dummy EventPublisher: all events are ignored.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class FakeEventPublisher implements EventPublisher {

	/**
	 * All events are ignored.
	 * 
	 * @see org.intelligentsia.dowsers.eventstore.EventPublisher#publish(java.util.List)
	 */
	@Override
	public <T extends DomainEvent> void publish(List<T> events) {
		// nothing to do
	}

}
