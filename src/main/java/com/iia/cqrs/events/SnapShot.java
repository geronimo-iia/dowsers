/**
 * 
 */
package com.iia.cqrs.events;

import com.iia.cqrs.Identifier;
import com.iia.cqrs.storage.memento.Memento;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class SnapShot {

	private final Identifier eventProviderIdentifier;
	private final Memento memento;

	/**
	 * Build a new instance of <code>SnapShot</code>
	 * 
	 * @param eventProviderId
	 * @param version
	 * @param memento
	 */
	public SnapShot(Identifier eventProviderIdentifier, long version, Memento memento) {
		super();
		this.eventProviderIdentifier = eventProviderIdentifier;
		this.memento = memento;
	}

	public Identifier getEventProviderIdentifier() {
		return this.eventProviderIdentifier;
	}

	public Memento getMemento() {
		return this.memento;
	}

}
