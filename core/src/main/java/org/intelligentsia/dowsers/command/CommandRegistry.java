/**
 * 
 */
package org.intelligentsia.dowsers.command;

/**
 * CommandRegistry register command handlers instance.
 * 
 * A command handler subscribe to a particular command like this:
 * 
 * <pre>
 * // handler for CreateAddressBookCommand request
 * &#064;Subscribe
 * public void onCreateAddressBookCommand(CreateAddressBookCommand command) {
 * 	// do something
 * }
 * </pre>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface CommandRegistry {

	/**
	 * Register a new command handler instance.
	 * 
	 * @param commandHandler
	 *            command handler instance
	 * @throws NullPointerException
	 *             if commandHandler is null
	 */
	public void register(final Object commandHandler) throws NullPointerException;
}
