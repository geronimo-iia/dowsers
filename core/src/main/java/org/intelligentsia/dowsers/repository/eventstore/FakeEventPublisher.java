package org.intelligentsia.dowsers.repository.eventstore;

import java.util.List;

import org.intelligentsia.dowsers.events.DomainEvent;

/**
 * FakeEventPublisher implements a dummy EventPublisher: all events are ignored.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class FakeEventPublisher implements EventPublisher {

	/**
	 * All events are ignored.
	 * 
	 * @see org.intelligentsia.dowsers.repository.eventstore.EventPublisher#publish(java.util.List)
	 */
	@Override
	public <T extends DomainEvent> void publish(final List<T> events) {
		// nothing to do
	}

}
