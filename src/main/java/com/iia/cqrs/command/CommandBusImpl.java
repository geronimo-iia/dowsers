/**
 * 
 */
package com.iia.cqrs.command;

import java.util.UUID;
import java.util.concurrent.Executor;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class CommandBusImpl implements CommandBus {

	private EventBus eventBus;

	/**
	 * Build a new instance of <code>CommandBusImpl</code> which process command handler in a synchrony way.
	 * 
	 * @see EventBus
	 */
	public CommandBusImpl() {
		eventBus = new EventBus(UUID.randomUUID().toString());
	}

	/**
	 * 
	 * Build a new instance of <code>CommandBusImpl</code> which process command handler asynchronously.
	 * 
	 * @see AsyncEventBus
	 * @param executor
	 */
	public CommandBusImpl(Executor executor) {
		eventBus = new AsyncEventBus(UUID.randomUUID().toString(), executor);
	}

	@Override
	public void send(Command command) {
		eventBus.post(command);
	}

	@Override
	public void register(CommandHandler commandHandler) {
		eventBus.register(commandHandler);
	}
}
