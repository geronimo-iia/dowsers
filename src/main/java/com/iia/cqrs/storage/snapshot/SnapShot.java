/**
 * 
 */
package com.iia.cqrs.storage.snapshot;
 

import java.util.TimeZone;

import hirondelle.date4j.DateTime;

import com.iia.cqrs.Identifier;
import com.iia.cqrs.storage.memento.Memento;

/**
 * SnapShot class.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class SnapShot {

	private final Identifier identifier;
	private final DateTime timestamp;
	private final Memento memento;

	/**
	 * Build a new instance of SnapShot.
	 * 
	 * @param identifier
	 * @param memento
	 */
	public SnapShot(Identifier identifier, Memento memento) {
		this(identifier, DateTime.now(TimeZone.getTimeZone("GMT")), memento);
	}

	/**
	 * Build a new instance of SnapShot.
	 * 
	 * @param identifier
	 * @param timestamp
	 * @param memento
	 */
	public SnapShot(Identifier identifier, DateTime timestamp, Memento memento) {
		super();
		this.identifier = identifier;
		this.timestamp = timestamp;
		this.memento = memento;
	}

	/**
	 * @return the identifier
	 */
	public Identifier getIdentifier() {
		return identifier;
	}

	/**
	 * @return the timestamp
	 */
	public DateTime getTimestamp() {
		return timestamp;
	}

	/**
	 * @return the memento
	 */
	public Memento getMemento() {
		return memento;
	}

}
