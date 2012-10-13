package org.intelligentsia.dowsers.core;

/**
 * <code>DowsersException</code> is the root of all RuntimeException.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class DowsersException extends RuntimeException {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -5135745251190326307L;

	/**
	 * Build a new instance of <code>DowsersException</code>.
	 */
	public DowsersException() {
		super();
	}

	/**
	 * Build a new instance of <code>DowsersException</code>.
	 * 
	 * @param message
	 * @param cause
	 */
	public DowsersException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Build a new instance of <code>DowsersException</code>.
	 * 
	 * @param message
	 */
	public DowsersException(String message) {
		super(message);
	}

	/**
	 * Build a new instance of <code>DowsersException</code>.
	 * 
	 * @param cause
	 */
	public DowsersException(Throwable cause) {
		super(cause);
	}

}
