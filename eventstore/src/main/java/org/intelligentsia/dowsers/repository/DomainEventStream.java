package org.intelligentsia.dowsers.repository;

import java.io.Serializable;
import java.util.Collection;

import org.intelligentsia.dowsers.event.DomainEvent;
import org.intelligentsia.dowsers.repository.eventstore.DomainEventStreamSink;
import org.intelligentsia.dowsers.repository.eventstore.DomainEventStreamSource;

/**
 * DomainEventStream.
 * 
 * The DomainEventStream represents a stream of historical domain events.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DomainEventStream implements DomainEventStreamSink, DomainEventStreamSource, Serializable {
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
	private Collection<? extends DomainEvent> domainEvents;

	/**
	 * Build a new instance of DomainEventStream.
	 */
	public DomainEventStream() {
		super();
	}

	/**
	 * Build a new instance of DomainEventStream.
	 * 
	 * @param version
	 * @param domainEvents
	 */
	public DomainEventStream(final long version, final Collection<? extends DomainEvent> domainEvents) {
		super();
		this.version = version;
		this.domainEvents = domainEvents;
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
		this.domainEvents = domainEvents;
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
