/**
 * 
 */
package com.iia.cqrs.command;

/**
 * CommandHandler is just a marker for CommandBus.
 * Register methods with
 * 
 * <pre>
 * &#064;Subscribe
 * void onCreateContactCommand(CreateContactCommand e) {
 * 	recordChange(e.getContact());
 * }
 * </pre>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface CommandHandler {

}
