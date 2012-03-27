/**
 * 
 */
package org.intelligentsia.cqrs.eventstore;

import java.util.UUID;

/**
 * StreamEverExistsException.
 * 
 * @author JGT
 * 
 */
public class StreamEverExistsException extends EventStoreException {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = 7145436474310916670L;

	/**
	 * Build a new instance of StreamEverExistsException.
	 * 
	 * @param identity
	 */
	public StreamEverExistsException(final UUID identity) {
		super(identity);
	}

	/**
	 * Build a new instance of StreamEverExistsException.
	 * 
	 * @param identity
	 * @param message
	 * @param cause
	 */
	public StreamEverExistsException(final UUID identity, final String message, final Throwable cause) {
		super(identity, message, cause);
	}

	/**
	 * Build a new instance of StreamEverExistsException.
	 * 
	 * @param identity
	 * @param message
	 */
	public StreamEverExistsException(final UUID identity, final String message) {
		super(identity, message);
	}

	/**
	 * Build a new instance of StreamEverExistsException.
	 * 
	 * @param identity
	 * @param cause
	 */
	public StreamEverExistsException(final UUID identity, final Throwable cause) {
		super(identity, cause);
	}

}
