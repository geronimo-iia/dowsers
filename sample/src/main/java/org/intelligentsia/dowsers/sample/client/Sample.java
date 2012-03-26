/**
 * 
 */
package org.intelligentsia.dowsers.sample.client;

import org.intelligentsia.dowsers.command.CommandInvoker;
import org.intelligentsia.dowsers.sample.commands.CreateContactCommand;


/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Sample {
	private final CommandInvoker commandInvoker;

	/**
	 * Build a new instance of <code>Sample</code>
	 * 
	 * @param commandInvoker
	 */
	public Sample(final CommandInvoker commandInvoker) {
		super();
		this.commandInvoker = commandInvoker;
	}

	public void createContact(final String name) {
		new CreateContactCommand(commandInvoker, name).execute();
	}
}
