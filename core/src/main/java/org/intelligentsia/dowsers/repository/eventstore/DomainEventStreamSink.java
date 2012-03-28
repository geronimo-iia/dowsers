package org.intelligentsia.dowsers.repository.eventstore;

import org.intelligentsia.dowsers.domain.DomainEvent;

/**
 * DomainEventStreamSink.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface DomainEventStreamSink {

	public void setVersion(long version);

	public void setDomainEvents(Iterable<? extends DomainEvent> domainEvents);
}
