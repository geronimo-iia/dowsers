/**
 * 
 */
package org.intelligentsia.dowsers;

import java.util.UUID;

/**
 * DowsersException extends RuntimeException. Root exception of dowsers project.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DowsersException extends RuntimeException {
	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * identity instance.
	 */
	private final UUID identity;

	/**
	 * Build a new instance of DowsersException.
	 * 
	 * @param message
	 */
	public DowsersException(final String message) {
		super(message);
		identity = null;
	}

	/**
	 * Build a new instance of DowsersException.
	 * 
	 * @param identity
	 */
	public DowsersException(final UUID identity) {
		super();
		this.identity = identity;
	}

	public DowsersException(final UUID identity, final String message) {
		super(message);
		this.identity = identity;
	}

	/**
	 * 
	 * Build a new instance of DowsersException.
	 * 
	 * @param identity
	 * @param cause
	 */
	public DowsersException(final UUID identity, final Throwable cause) {
		super(cause);
		this.identity = identity;
	}

	/**
	 * Build a new instance of DowsersException.
	 * 
	 * @param message
	 * @param cause
	 */
	public DowsersException(final UUID identity, final String message, final Throwable cause) {
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
