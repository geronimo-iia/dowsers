/**
 * 
 */
package com.iia.cqrs.command;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.Subscribe;

/**
 * CommandHandler is an utility class to implements command handler.
 * 
 * A command handler has only one responsibility and that is to handle the one
 * particular command by executing the appropriate domain behavior. <br />
 * The command handler should not be doing any domain logic itself. <br />
 * If there is a need for this than that logic should be moved into a service of
 * its own. <br />
 * 
 * 
 * Example of CommandHandler implementation:
 * 
 * <pre>
 * public class FakeHelloCommandHandler extends CommandHandler&lt;FakeHelloCommand&gt; {
 * 
 * 	private String lastCalled;
 * 
 * 	public FakeHelloCommandHandler(final CommandRegistry commandRegistry) throws NullPointerException {
 * 		super(commandRegistry);
 * 	}
 * 
 * 	&#064;Override
 * 	public void onCommand(final FakeHelloCommand command) {
 * 		lastCalled = command.getName();
 * 	}
 * 
 * 	public String getLastCalled() {
 * 		return lastCalled;
 * 	}
 * 
 * }
 * 
 * </pre>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public abstract class CommandHandler<T extends Command> {

	/**
	 * Build a new instance of CommandHandler and register herself on command
	 * registry.
	 * 
	 * @param commandRegistry
	 *            command registry instance
	 * @throws NullPointerException
	 *             if commandRegistry is null
	 */
	public CommandHandler(CommandRegistry commandRegistry) throws NullPointerException {
		Preconditions.checkNotNull(commandRegistry).register(this);
	}

	/**
	 * Process specified command. If null, nothing is done.
	 * 
	 * @param command
	 *            command to process.
	 */
	@Subscribe
	public abstract void onCommand(T command);

}
