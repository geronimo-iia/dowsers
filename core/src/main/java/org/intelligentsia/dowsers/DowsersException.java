/**
 * 
 */
package org.intelligentsia.dowsers;

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
	private final String identity;

	public DowsersException(final String identity) {
		super();
		this.identity = identity;
	}

	public DowsersException(final String identity, final String message) {
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
	public DowsersException(final String identity, final Throwable cause) {
		super(cause);
		this.identity = identity;
	}

	/**
	 * Build a new instance of DowsersException.
	 * 
	 * @param message
	 * @param cause
	 */
	public DowsersException(final String identity, final String message, final Throwable cause) {
		super(message, cause);
		this.identity = identity;
	}

	/**
	 * @return the identity
	 */
	public String getIdentity() {
		return identity;
	}

}
