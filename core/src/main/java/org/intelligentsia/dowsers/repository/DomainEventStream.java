package org.intelligentsia.dowsers.repository;

import org.intelligentsia.dowsers.domain.DomainEvent;
import org.intelligentsia.dowsers.repository.eventstore.DomainEventStreamSink;
import org.intelligentsia.dowsers.repository.eventstore.DomainEventStreamSource;

/**
 * DomainEventStream.
 * 
 *@author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DomainEventStream implements DomainEventStreamSink, DomainEventStreamSource {

	private long version;
	private Iterable<? extends DomainEvent> domainEvents;

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
	public DomainEventStream(final long version, final Iterable<? extends DomainEvent> domainEvents) {
		super();
		this.version = version;
		this.domainEvents = domainEvents;
	}

	/**
	 * @return the domainEvents
	 */
	@Override
	public Iterable<? extends DomainEvent> getDomainEvents() {
		return domainEvents;
	}

	/**
	 * @param domainEvents
	 *            the domainEvents to set
	 */
	@Override
	public void setDomainEvents(final Iterable<? extends DomainEvent> domainEvents) {
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
