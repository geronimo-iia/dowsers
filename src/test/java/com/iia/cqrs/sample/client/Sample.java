/**
 * 
 */
package com.iia.cqrs.sample.client;

import com.iia.cqrs.command.CommandInvoker;
import com.iia.cqrs.sample.commands.CreateContactCommand;

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
	public Sample(CommandInvoker commandInvoker) {
		super();
		this.commandInvoker = commandInvoker;
	}

	public void createContact(String name) {
		commandInvoker.invoke(new CreateContactCommand(name));
	}
}
