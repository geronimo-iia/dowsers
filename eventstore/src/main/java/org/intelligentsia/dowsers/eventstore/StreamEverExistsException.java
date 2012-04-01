package org.intelligentsia.dowsers.eventstore;

import org.intelligentsia.dowsers.DowsersException;

/**
 * StreamEverExistsException.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class StreamEverExistsException extends DowsersException {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -1L;

	/**
	 * Build a new instance of StreamEverExistsException.
	 * 
	 * @param identity
	 */
	public StreamEverExistsException(final String identity) {
		super(identity);
	}

	/**
	 * Build a new instance of StreamEverExistsException.
	 * 
	 * @param identity
	 * @param message
	 * @param cause
	 */
	public StreamEverExistsException(final String identity, final String message, final Throwable cause) {
		super(identity, message, cause);
	}

	/**
	 * Build a new instance of StreamEverExistsException.
	 * 
	 * @param identity
	 * @param message
	 */
	public StreamEverExistsException(final String identity, final String message) {
		super(identity, message);
	}

	/**
	 * Build a new instance of StreamEverExistsException.
	 * 
	 * @param identity
	 * @param cause
	 */
	public StreamEverExistsException(final String identity, final Throwable cause) {
		super(identity, cause);
	}

}
