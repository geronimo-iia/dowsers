/**
 * 
 */
package org.intelligentsia.dowsers.storage;

import java.util.UUID;

/**
 * EmptyResultException.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class EmptyResultException extends StoreException {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID =  1L;

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
