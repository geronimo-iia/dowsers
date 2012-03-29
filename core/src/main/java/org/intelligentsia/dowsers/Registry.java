package org.intelligentsia.dowsers;

/**
 * Registry declare methods to register an object.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface Registry<T> {

	/**
	 * Register specified object.
	 * 
	 * @param object
	 *            a object to register.
	 * 
	 * @throws NullPointerException
	 *             if object is null
	 */
	public void register(final T object) throws NullPointerException;
}
