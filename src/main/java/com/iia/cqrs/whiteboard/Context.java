/**
 * 
 */
package com.iia.cqrs.whiteboard;

/**
 * Context act as Whiteboard.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface Context {

	public Registry getRegistry();

	public <T> Tracker<T> getTracker(Class<T> type);
}
