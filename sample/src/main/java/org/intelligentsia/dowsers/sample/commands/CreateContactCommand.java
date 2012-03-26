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
public class CreateContactCommand extends Command {

	private final String name;

	/**
	 * Build a new instance of CreateContactCommand.
	 * 
	 * @param commandInvoker
	 * @param name
	 * @throws NullPointerException
	 */
	public CreateContactCommand(final CommandInvoker commandInvoker, final String name) throws NullPointerException {
		super(commandInvoker);
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
