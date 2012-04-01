package org.intelligentsia.dowsers.repository;

import java.io.Serializable;
import java.util.Collection;

import org.intelligentsia.dowsers.event.DomainEvent;
import org.intelligentsia.dowsers.eventstore.EventStreamSink;
import org.intelligentsia.dowsers.eventstore.EventStreamSource;

/**
 * DomainEventStream.
 * 
 * The DomainEventStream represents a stream of historical domain events.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DomainEventStream implements EventStreamSink<DomainEvent>, EventStreamSource<DomainEvent>, Serializable {
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
	private Collection<DomainEvent> domainEvents;

	private String className;
	
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
	public DomainEventStream(final long version, final Collection<DomainEvent> domainEvents, final String className) {
		super();
		this.version = version;
		this.domainEvents = domainEvents;
		this.className=className;
	}

	/**
	 * @return the domainEvents
	 */
	@Override
	public Collection<DomainEvent> getEvents() {
		return domainEvents;
	}

	/**
	 * @param domainEvents
	 *            the domainEvents to set
	 */
	@Override
	public void setEvents(final Collection<DomainEvent> events) {
		this.domainEvents = events;
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

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
