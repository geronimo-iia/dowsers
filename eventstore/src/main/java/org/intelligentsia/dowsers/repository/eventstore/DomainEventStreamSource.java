package org.intelligentsia.dowsers.repository.eventstore;

import java.util.Collection;

import org.intelligentsia.dowsers.event.DomainEvent;

/**
 * DomainEventStreamSource.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface DomainEventStreamSource {

	public long getVersion();

	public Collection<? extends DomainEvent> getDomainEvents();
}
