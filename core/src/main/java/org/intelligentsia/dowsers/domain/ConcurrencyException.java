/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import org.intelligentsia.dowsers.DowsersException;

/**
 * ConcurrencyException.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class ConcurrencyException extends DowsersException {

	private static final long serialVersionUID = 1L;

	/**
	 * Build a new instance of ConcurrencyException.
	 * 
	 * @param identity
	 * @param message
	 * @param cause
	 */
	public ConcurrencyException(final String identity, final String message, final Throwable cause) {
		super(identity, message, cause);
	}

	/**
	 * Build a new instance of ConcurrencyException.
	 * 
	 * @param identity
	 * @param message
	 */
	public ConcurrencyException(final String identity, final String message) {
		super(identity, message);
	}

	/**
	 * Build a new instance of ConcurrencyException.
	 * 
	 * @param identity
	 * @param cause
	 */
	public ConcurrencyException(final String identity, final Throwable cause) {
		super(identity, cause);
	}

}
