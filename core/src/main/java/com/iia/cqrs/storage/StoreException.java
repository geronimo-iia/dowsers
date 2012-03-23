/**
 * 
 */
package com.iia.cqrs.storage;

import java.util.UUID;

/**
 * StoreException extends RuntimeException. Root of store exception.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class StoreException extends RuntimeException {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -8148778302923827851L;

	/**
	 * identity instance.
	 */
	private final UUID identity;

	/**
	 * Build a new instance of StoreException.
	 * 
	 * @param message
	 */
	public StoreException(final String message) {
		super(message);
		identity = null;
	}

	/**
	 * Build a new instance of StoreException.
	 * 
	 * @param identity
	 */
	public StoreException(final UUID identity) {
		super();
		this.identity = identity;
	}

	/**
	 * Build a new instance of StoreException.
	 * 
	 * @param message
	 */
	public StoreException(final UUID identity, final String message) {
		super(message);
		this.identity = identity;
	}

	/**
	 * 
	 * Build a new instance of StoreException.
	 * 
	 * @param identity
	 * @param cause
	 */
	public StoreException(final UUID identity, final Throwable cause) {
		super(cause);
		this.identity = identity;
	}

	/**
	 * Build a new instance of StoreException.
	 * 
	 * @param message
	 * @param cause
	 */
	public StoreException(final UUID identity, final String message, final Throwable cause) {
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
