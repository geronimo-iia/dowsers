package org.intelligentsia.dowsers.repository.eventstore;

import java.util.Collection;

import org.intelligentsia.dowsers.event.DomainEvent;

/**
 * DomainEventStreamSink.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface DomainEventStreamSink {

	public void setVersion(long version);

	public void setDomainEvents(Collection<? extends DomainEvent> domainEvents);
}
