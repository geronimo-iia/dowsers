package org.intelligentsia.dowsers.repository.eventstore;

import org.intelligentsia.dowsers.DowsersException;

/**
 * EmptyResultException.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class EmptyResultException extends DowsersException {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Build a new instance of EmptyResultException.
	 * 
	 * @param identity
	 * @param message
	 * @param cause
	 */
	public EmptyResultException(final String identity, final String message, final Throwable cause) {
		super(identity, message, cause);
	}

	/**
	 * Build a new instance of EmptyResultException.
	 * 
	 * @param identity
	 * @param message
	 */
	public EmptyResultException(final String identity, final String message) {
		super(identity, message);
	}

	/**
	 * Build a new instance of EmptyResultException.
	 * 
	 * @param identity
	 * @param cause
	 */
	public EmptyResultException(final String identity, final Throwable cause) {
		super(identity, cause);
	}

	/**
	 * Build a new instance of EmptyResultException.
	 * 
	 * @param identity
	 */
	public EmptyResultException(final String identity) {
		super(identity);
	}

}
