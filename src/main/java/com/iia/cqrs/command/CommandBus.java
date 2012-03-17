/**
 * 
 */
package com.iia.cqrs.command;


/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface CommandBus {

	public void send(Command command);

	public void register(CommandHandler commandHandler);
}
