/**
 * 
 */
package com.iia.cqrs.whiteboard;

/**
 * Registry of service.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface Registry {

	/**
	 * Register specified instance of type T.
	 * 
	 * @param <T>
	 * @param type
	 * @param instance
	 */
	public <T> void register(Class<T> type, T instance);
	
	
	public <T> Iterable<T> find(Class<T> type);
}
