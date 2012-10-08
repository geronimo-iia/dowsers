/**
 *        Licensed to the Apache Software Foundation (ASF) under one
 *        or more contributor license agreements.  See the NOTICE file
 *        distributed with this work for additional information
 *        regarding copyright ownership.  The ASF licenses this file
 *        to you under the Apache License, Version 2.0 (the
 *        "License"); you may not use this file except in compliance
 *        with the License.  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing,
 *        software distributed under the License is distributed on an
 *        "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *        KIND, either express or implied.  See the License for the
 *        specific language governing permissions and limitations
 *        under the License.
 *
 */
package org.intelligentsia.dowsers.command;

import org.intelligentsia.dowsers.core.Handler;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

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
	 * Command history instance.
	 */
	private final CommandHistory commandHistory;

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
		this(eventBus, null);
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
	 * @param commandHistory
	 *            command History instance
	 * @throws NullPointerException
	 *             if eventBus is null
	 */
	public CommandBus(final EventBus eventBus, final CommandHistory commandHistory) throws NullPointerException {
		super();
		this.eventBus = Preconditions.checkNotNull(eventBus);
		this.commandHistory = commandHistory;
	}

	/**
	 * @see org.intelligentsia.dowsers.command.CommandInvoker#invoke(org.intelligentsia.dowsers.command.Command)
	 */
	@Override
	public void invoke(final Command command) {
		eventBus.post(command);
	}

	/**
	 * @see org.intelligentsia.dowsers.command.CommandHandlerRegistry#register(org.intelligentsia.dowsers.command.CommandHandler)
	 */
	@Override
	public <T extends Command> void register(final Handler<T> commandHandler) {
		eventBus.register(new CommandBusHandler<T>(commandHandler));
	}

	/**
	 * 
	 * CommandHandlerWithHistory class is used to manage command history
	 * processing and event bus registration.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 * 
	 * @param <T>
	 */
	private class CommandBusHandler<T extends Command> {
		/**
		 * Handler where to delegate command process.
		 */
		private final Handler<T> handler;

		/**
		 * Build a new instance of CommandBusHandler.
		 * 
		 * @param handler
		 *            command Handler instance
		 */
		public CommandBusHandler(final Handler<T> handler) {
			super();
			this.handler = handler;
		}

		/**
		 * Handle specified command. If null, nothing is done.
		 * 
		 * @param command
		 *            command to process.
		 */
		@Subscribe
		public void handle(final T command) {
			if (command != null) {
				// delegate handler
				handler.handle(command);
				// if process is a success and we have an command history
				// instance add command instance
				if (commandHistory != null) {
					commandHistory.push(command);
				}
			}
		}

	}
}
