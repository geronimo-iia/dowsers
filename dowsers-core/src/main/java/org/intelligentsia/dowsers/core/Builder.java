package org.intelligentsia.dowsers.core;

/**
 * Builder interface declare method to build an instance of <code>T</code>.
 * 
 * @author JGT
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface Builder<T> {

	/**
	 * @return an instance of type T>
	 */
	public T build();
}
