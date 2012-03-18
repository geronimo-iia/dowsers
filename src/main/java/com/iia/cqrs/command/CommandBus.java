/**
 * 
 */
package com.iia.cqrs.command;

import java.util.UUID;
import java.util.concurrent.Executor;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

/**
 * CommandHandler is just a marker for CommandBus.
 * Register methods with
 * 
 * <pre>
 * &#064;Subscribe
 * void onCreateContactCommand(CreateContactCommand e) {
 * 	recordChange(e.getContact());
 * }
 * </pre>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class CommandBus implements CommandInvoker, CommandRegistry {

	private EventBus eventBus;

	/**
	 * Build a new instance of <code>CommandBusImpl</code> which process command handler in a synchrony way.
	 * 
	 * @see EventBus
	 */
	public CommandBus() {
		eventBus = new EventBus(UUID.randomUUID().toString());
	}

	/**
	 * 
	 * Build a new instance of <code>CommandBusImpl</code> which process command handler asynchronously.
	 * 
	 * @see AsyncEventBus
	 * @param executor
	 */
	public CommandBus(Executor executor) {
		eventBus = new AsyncEventBus(UUID.randomUUID().toString(), executor);
	}

	@Override
	public void invoke(Command command) {
		eventBus.post(command);
	}

	@Override
	public void register(Object commandHandler) {
		eventBus.register(commandHandler);
	}
}
