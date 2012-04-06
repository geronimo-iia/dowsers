/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at
 
         http://www.apache.org/licenses/LICENSE-2.0
 
       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */
package org.intelligentsia.dowsers.command;

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
 * 	public FakeHelloCommandHandler(final CommandHandlerRegistry commandRegistry) throws NullPointerException {
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
 * CommandHandler act as transaction boundaries for change on domain model.
 * CommandHandler should have a singleton scope.
 * 
 * 
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
	public CommandHandler(final CommandHandlerRegistry commandRegistry) throws NullPointerException {
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
