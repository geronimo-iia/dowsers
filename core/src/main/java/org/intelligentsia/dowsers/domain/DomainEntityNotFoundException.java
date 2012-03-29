/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import org.intelligentsia.dowsers.DowsersException;

/**
 * DomainEntityNotFoundException.
 * 
 * @author jgt
 * 
 */
public class DomainEntityNotFoundException extends DowsersException {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = 1022622007007509677L;

	/**
	 * Build a new instance of DomainEntityNotFoundException.
	 * 
	 * @param identity
	 */
	public DomainEntityNotFoundException(final String identity) {
		super(identity);
	}

	/**
	 * Build a new instance of DomainEntityNotFoundException.
	 * 
	 * @param identity
	 * @param message
	 */
	public DomainEntityNotFoundException(final String identity, final String message) {
		super(identity, message);
	}

	/**
	 * Build a new instance of DomainEntityNotFoundException.
	 * 
	 * @param identity
	 * @param cause
	 */
	public DomainEntityNotFoundException(final String identity, final Throwable cause) {
		super(identity, cause);
	}

	/**
	 * Build a new instance of DomainEntityNotFoundException.
	 * 
	 * @param identity
	 * @param message
	 * @param cause
	 */
	public DomainEntityNotFoundException(final String identity, final String message, final Throwable cause) {
		super(identity, message, cause);
	}

}
