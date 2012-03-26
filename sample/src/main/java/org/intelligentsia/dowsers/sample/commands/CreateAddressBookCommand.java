/**
 * 
 */
package org.intelligentsia.dowsers.sample.commands;

import org.intelligentsia.dowsers.command.Command;
import org.intelligentsia.dowsers.command.CommandInvoker;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class CreateAddressBookCommand extends Command {

	/**
	 * Build a new instance of CreateAddressBookCommand.
	 * 
	 * @param commandInvoker
	 * @throws NullPointerException
	 */
	public CreateAddressBookCommand(final CommandInvoker commandInvoker) throws NullPointerException {
		super(commandInvoker);
	}

}
