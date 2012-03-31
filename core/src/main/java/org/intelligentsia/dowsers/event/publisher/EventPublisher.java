package org.intelligentsia.dowsers.event.publisher;

import java.util.List;

import org.intelligentsia.dowsers.event.DomainEvent;


/**
 * EventPublisher declares method to publish Domain event to an external bus.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface EventPublisher {

	/**
	 * Publish specifie list of event.
	 * 
	 * @param events
	 *            events to publish.
	 */
	public <T extends DomainEvent> void publish(List<T> events);
}