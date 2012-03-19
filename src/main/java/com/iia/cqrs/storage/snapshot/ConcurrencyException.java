/**
 * 
 */
package com.iia.cqrs.storage.snapshot;

import java.util.UUID;

/**
 * ConcurrencyException.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class ConcurrencyException extends StoreException {

	private static final long serialVersionUID = 8541946754385925170L;

	/**
	 * Build a new instance of ConcurrencyException.
	 * 
	 * @param identity
	 */
	public ConcurrencyException(final UUID identity) {
		super(identity);
	}

	/**
	 * Build a new instance of ConcurrencyException.
	 * 
	 * @param identity
	 * @param message
	 * @param cause
	 */
	public ConcurrencyException(final UUID identity, final String message, final Throwable cause) {
		super(identity, message, cause);
	}

	/**
	 * Build a new instance of ConcurrencyException.
	 * 
	 * @param identity
	 * @param message
	 */
	public ConcurrencyException(final UUID identity, final String message) {
		super(identity, message);
	}

	/**
	 * Build a new instance of ConcurrencyException.
	 * 
	 * @param identity
	 * @param cause
	 */
	public ConcurrencyException(final UUID identity, final Throwable cause) {
		super(identity, cause);
	}

}
