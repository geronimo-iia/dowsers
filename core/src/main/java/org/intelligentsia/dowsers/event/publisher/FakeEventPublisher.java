package org.intelligentsia.dowsers.event.publisher;

import java.util.List;

import org.intelligentsia.dowsers.event.DomainEvent;


/**
 * FakeEventPublisher implements a dummy EventPublisher: all events are ignored.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class FakeEventPublisher implements EventPublisher {

	/**
	 * All events are ignored.
	 * 
	 * @see org.intelligentsia.dowsers.event.publisher.EventPublisher#publish(java.util.List)
	 */
	@Override
	public <T extends DomainEvent> void publish(final List<T> events) {
		// nothing to do
	}

}
