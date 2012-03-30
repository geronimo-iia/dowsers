package org.intelligentsia.dowsers.repository.eventstore;

import org.intelligentsia.dowsers.events.DomainEvent;

/**
 * DomainEventStreamSource.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface DomainEventStreamSource {

	public long getVersion();

	public Iterable<? extends DomainEvent> getDomainEvents();
}
