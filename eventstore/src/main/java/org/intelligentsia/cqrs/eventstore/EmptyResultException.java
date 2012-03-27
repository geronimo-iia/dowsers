/**
 * 
 */
package org.intelligentsia.cqrs.eventstore;

import java.util.UUID;

/**
 * EmptyResultException.
 * 
 * @author JGT
 * 
 */
public class EmptyResultException extends EventStoreException {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = 1814427702420293630L;

	/**
	 * Build a new instance of EmptyResultException.
	 */
	public EmptyResultException(final String message) {
		super(message);
	}

	/**
	 * Build a new instance of EmptyResultException.
	 * 
	 * @param identity
	 * @param message
	 * @param cause
	 */
	public EmptyResultException(final UUID identity, final String message, final Throwable cause) {
		super(identity, message, cause);
	}

	/**
	 * Build a new instance of EmptyResultException.
	 * 
	 * @param identity
	 * @param message
	 */
	public EmptyResultException(final UUID identity, final String message) {
		super(identity, message);
	}

	/**
	 * Build a new instance of EmptyResultException.
	 * 
	 * @param identity
	 * @param cause
	 */
	public EmptyResultException(final UUID identity, final Throwable cause) {
		super(identity, cause);
	}

	/**
	 * Build a new instance of EmptyResultException.
	 * 
	 * @param identity
	 */
	public EmptyResultException(final UUID identity) {
		super(identity);
	}

}
