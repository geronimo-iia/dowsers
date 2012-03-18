/**
 * 
 */
package com.iia.cqrs.command;

/**
 *
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface CommandRegistry {
	
	public void register(Object commandHandler);
}
