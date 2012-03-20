/**
 * 
 */
package com.iia.cqrs.storage.memento;

import java.util.UUID;

import com.iia.cqrs.storage.Memento;

/**
 * EntityMemento.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public abstract class EntityMemento implements Memento {

	private static final long serialVersionUID = 1L;

	private UUID identity;

	private long version;

	/**
	 * Build a new instance of EntityMemento.
	 */
	public EntityMemento() {
		super();
	}

	/**
	 * @return the identity
	 */
	public UUID getIdentity() {
		return identity;
	}

	/**
	 * @param identity
	 *            the identity to set
	 */
	public void setIdentity(UUID identity) {
		this.identity = identity;
	}

	/**
	 * @return the version
	 */
	public long getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(long version) {
		this.version = version;
	}

}
