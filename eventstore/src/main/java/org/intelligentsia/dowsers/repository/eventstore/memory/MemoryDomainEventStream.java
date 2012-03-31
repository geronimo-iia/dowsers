/**
 * 
 */
package org.intelligentsia.dowsers.repository.eventstore.memory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.intelligentsia.dowsers.event.DomainEvent;
import org.intelligentsia.dowsers.repository.eventstore.DomainEventStreamSink;
import org.intelligentsia.dowsers.repository.eventstore.DomainEventStreamSource;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
class MemoryDomainEventStream implements DomainEventStreamSink, DomainEventStreamSource, Serializable {
	/**
	 * serialVersionUID:long.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Version of which this domainEvents apply.
	 */
	private long version = -1L;
	/**
	 * Ordered collection of DomainEvent.
	 */
	private List<DomainEvent> domainEvents;

	/**
	 * Build a new instance of DomainEventStream.
	 */
	public MemoryDomainEventStream() {
		super();
	}

	/**
	 * Build a new instance of DomainEventStream.
	 * 
	 * @param version
	 * @param domainEvents
	 */
	public MemoryDomainEventStream(final long version, final Collection<? extends DomainEvent> domainEvents) {
		super();
		this.version = version;
		this.domainEvents = new ArrayList<DomainEvent>(domainEvents);
	}

	/**
	 * @return the domainEvents
	 */
	@Override
	public Collection<? extends DomainEvent> getDomainEvents() {
		return domainEvents;
	}

	/**
	 * @param domainEvents
	 *            the domainEvents to set
	 */
	@Override
	public void setDomainEvents(final Collection<? extends DomainEvent> domainEvents) {
		this.domainEvents = new ArrayList<DomainEvent>(domainEvents); 
	}

	public void addDomainEvents(final Collection<? extends DomainEvent> domainEvents) {
		this.domainEvents.addAll(domainEvents);
	}

	/**
	 * @return the version
	 */
	@Override
	public long getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	@Override
	public void setVersion(final long version) {
		this.version = version;
	}

}
