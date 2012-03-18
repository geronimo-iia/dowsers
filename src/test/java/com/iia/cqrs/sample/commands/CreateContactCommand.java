/**
 * 
 */
package com.iia.cqrs.sample.commands;

import com.iia.cqrs.command.Command;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class CreateContactCommand extends Command {

	private final String name;

	/**
	 * Build a new instance of <code>CreateContactCommand</code>
	 * 
	 * @param name
	 */
	public CreateContactCommand(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
