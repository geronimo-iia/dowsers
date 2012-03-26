/**
 * 
 */
package org.intelligentsia.dowsers.command;

import org.intelligentsia.dowsers.command.CommandHandler;
import org.intelligentsia.dowsers.command.CommandRegistry;

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
	 * @see org.intelligentsia.dowsers.command.CommandHandler#onCommand(org.intelligentsia.dowsers.command.Command)
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
