/**
 * 
 */
package org.intelligentsia.cqrs.eventstore;

import java.util.UUID;

/**
 * EventStoreException extends RuntimeException. Root of event sore exception.
 * 
 * @author JGT
 * 
 */
public class EventStoreException extends RuntimeException {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -8148778302923827851L;

	/**
	 * identity instance.
	 */
	private final UUID identity;

	/**
	 * Build a new instance of EventStoreException.
	 * 
	 * @param message
	 */
	public EventStoreException(final String message) {
		super(message);
		identity = null;
	}

	/**
	 * Build a new instance of EventStoreException.
	 * 
	 * @param identity
	 */
	public EventStoreException(final UUID identity) {
		super();
		this.identity = identity;
	}

	/**
	 * Build a new instance of EventStoreException.
	 * 
	 * @param message
	 */
	public EventStoreException(final UUID identity, final String message) {
		super(message);
		this.identity = identity;
	}

	/**
	 * 
	 * Build a new instance of EventStoreException.
	 * 
	 * @param identity
	 * @param cause
	 */
	public EventStoreException(final UUID identity, final Throwable cause) {
		super(cause);
		this.identity = identity;
	}

	/**
	 * Build a new instance of EventStoreException.
	 * 
	 * @param message
	 * @param cause
	 */
	public EventStoreException(final UUID identity, final String message, final Throwable cause) {
		super(message, cause);
		this.identity = identity;
	}

	/**
	 * @return the identity
	 */
	public UUID getIdentity() {
		return identity;
	}

}
