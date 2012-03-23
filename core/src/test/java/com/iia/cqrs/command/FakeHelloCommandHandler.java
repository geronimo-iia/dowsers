/**
 * 
 */
package com.iia.cqrs.command;

/**
 * FakeHelloCommandHandler.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class FakeHelloCommandHandler extends CommandHandler<FakeHelloCommand> {

	/**
	 * for test purpose.
	 */
	private String lastCalled = "";

	/**
	 * Build a new instance of FakeHelloCommandHandler.
	 * 
	 * @param commandRegistry
	 * @throws NullPointerException
	 */
	public FakeHelloCommandHandler(final CommandRegistry commandRegistry) throws NullPointerException {
		super(commandRegistry);
	}

	/**
	 * @see com.iia.cqrs.command.CommandHandler#onCommand(com.iia.cqrs.command.Command)
	 */
	@Override
	public void onCommand(final FakeHelloCommand command) {
		lastCalled = command.getName();
	}

	/**
	 * @return the lastCalled
	 */
	public String getLastCalled() {
		return lastCalled;
	}

}
