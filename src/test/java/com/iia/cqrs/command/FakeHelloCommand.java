/**
 * 
 */
package com.iia.cqrs.command;

import com.google.common.base.Preconditions;

/**
 * FakeHelloCommand.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class FakeHelloCommand extends Command {

	/**
	 * Name instance.
	 */
	private final String name;

	/**
	 * Build a new instance of FakeHelloCommand.
	 * 
	 * @param commandInvoker
	 * @param name
	 * @throws NullPointerException
	 */
	public FakeHelloCommand(final CommandInvoker commandInvoker, final String name) throws NullPointerException {
		super(commandInvoker);
		this.name = name;
	}

	/**
	 * @see com.iia.cqrs.command.Command#execute()
	 */
	@Override
	public void execute() throws NullPointerException, IllegalArgumentException {
		Preconditions.checkNotNull(name, "name cannot be null");
		Preconditions.checkArgument(!"".equals(name), "name must not be empty");
		// call super
		super.execute();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
