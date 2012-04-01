/**
 * 
 */
package org.intelligentsia.dowsers.eventstore.memory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.intelligentsia.dowsers.eventstore.EventStreamSink;
import org.intelligentsia.dowsers.eventstore.EventStreamSource;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
class MemoryDomainEventStream<EventType> implements EventStreamSink<EventType>, EventStreamSource<EventType>,
		Serializable {
	/**
	 * serialVersionUID:long.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Version of which this domainEvents apply.
	 */
	private long version = -1L;

	private String className;

	/**
	 * Build a new instance of <code>MemoryDomainEventStream</code>
	 * 
	 * @param version
	 * @param events
	 * @param className
	 */
	public MemoryDomainEventStream(long version, String className) {
		super();
		this.version = version;
		this.className = className;
	}

	public long getVersion() {
		return this.version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
