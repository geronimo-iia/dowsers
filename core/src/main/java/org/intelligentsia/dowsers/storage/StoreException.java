/**
 * 
 */
package org.intelligentsia.dowsers.storage;

import java.util.UUID;

import org.intelligentsia.dowsers.DowsersException;

 

/**
 * StoreException extends RuntimeException. Root of store exception.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class StoreException extends DowsersException {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Build a new instance of StoreException.
	 * @param message
	 */
	public StoreException(String message) {
		super(message);
	}

	/**
	 * Build a new instance of StoreException.
	 * @param identity
	 * @param message
	 * @param cause
	 */
	public StoreException(UUID identity, String message, Throwable cause) {
		super(identity, message, cause);
	}

	/**
	 * Build a new instance of StoreException.
	 * @param identity
	 * @param message
	 */
	public StoreException(UUID identity, String message) {
		super(identity, message);
	}

	/**
	 * Build a new instance of StoreException.
	 * @param identity
	 * @param cause
	 */
	public StoreException(UUID identity, Throwable cause) {
		super(identity, cause);
	}

	/**
	 * Build a new instance of StoreException.
	 * @param identity
	 */
	public StoreException(UUID identity) {
		super(identity);
	}

	
}
