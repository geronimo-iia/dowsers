/**
 * 
 */
package com.iia.cqrs.events;

/**
 * RegisterEventProvider.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public interface RegisterEventProvider {

	public void register(EventProvider eventProvider);

}
