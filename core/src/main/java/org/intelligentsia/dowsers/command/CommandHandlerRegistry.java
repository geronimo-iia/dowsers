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

import org.intelligentsia.dowsers.util.Handler;

/**
 * CommandHandlerRegistry register command handlers instance.
 * 
 * A command handler has only one responsibility: handle one particular command
 * by executing the appropriate domain behavior. <br />
 * The command handler should not be doing any domain logic itself. <br />
 * If there is a need for this than that logic should be moved into a service of
 * its own. <br />
 * CommandHandler act as transaction boundaries for change on domain model.<br />
 * CommandHandler should have a singleton scope.<br />
 * 
 * Example of CommandHandler implementation:
 * 
 * <pre>
 * public class FakeHelloCommandHandler implements CommandHandler&lt;FakeHelloCommand&gt; {
 * 
 * 	private String lastCalled;
 * 
 * 	&#064;Override
 * 	public void handle(final FakeHelloCommand command) {
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
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface CommandHandlerRegistry {

	/**
	 * Register a new command handler instance.
	 * 
	 * @param commandHandler
	 *            command handler instance
	 * @throws NullPointerException
	 *             if commandHandler is null
	 */
	public <T extends Command> void register(final Handler<T> commandHandler) throws NullPointerException;
}
