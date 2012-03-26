/**
 * 
 */
package org.intelligentsia.dowsers.command;

import java.util.UUID;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

/**
 * CommandBus implements a CommandHandlerRegistry and a CommandInvoker based on
 * {@link EventBus} guava framework.<br/>
 * To process command in a synchrony way use {@link EventBus} or
 * {@link AsyncEventBus} for asynchrony way.
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class CommandBus implements CommandInvoker, CommandHandlerRegistry {

	/**
	 * EventBus instance.
	 */
	private final EventBus eventBus;

	/**
	 * Build a new instance of <code>CommandBus</code> which process command
	 * handler in a synchrony way.
	 * 
	 */
	public CommandBus() {
		this(new EventBus(UUID.randomUUID().toString()));
	}

	/**
	 * 
	 * Build a new instance of <code>CommandBus</code>.
	 * 
	 * To use in a synchrony way use {@link EventBus} or {@link AsyncEventBus}
	 * to asynchrony way.
	 * 
	 * 
	 * @see eventBus
	 * @param eventBus
	 *            event bus instance (@see {@link AsyncEventBus} or @see
	 *            {@link EventBus} )
	 * @throws NullPointerException
	 *             if eventBus is null
	 */
	public CommandBus(final EventBus eventBus) throws NullPointerException {
		this.eventBus = Preconditions.checkNotNull(eventBus);
	}

	/**
	 * @see org.intelligentsia.dowsers.command.CommandInvoker#invoke(org.intelligentsia.dowsers.command.Command)
	 */
	@Override
	public void invoke(final Command command) {
		eventBus.post(command);
	}

	/**
	 * @see org.intelligentsia.dowsers.command.CommandHandlerRegistry#register(java.lang.Object)
	 */
	@Override
	public void register(final Object commandHandler) {
		eventBus.register(commandHandler);
	}

	public void unregister(final Object commandHandler) {
		eventBus.unregister(commandHandler);
	}

}
