/**
 * 
 */
package com.iia.cqrs.sample.commands;

import com.iia.cqrs.command.Command;
import com.iia.cqrs.command.CommandInvoker;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class CreateAddressBookCommand extends Command {

	/**
	 * Build a new instance of CreateAddressBookCommand.
	 * @param commandInvoker
	 * @throws NullPointerException
	 */
	public CreateAddressBookCommand(CommandInvoker commandInvoker) throws NullPointerException {
		super(commandInvoker);
	}

 

}
