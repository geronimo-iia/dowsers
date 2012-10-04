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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * DefaultCommandHistory implements in memory CommandHistory.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DefaultCommandHistory implements CommandHistory {

	private final List<Command> commands;

	/**
	 * Build a new instance of DefaultCommandHistory.
	 */
	public DefaultCommandHistory() {
		commands = Collections.synchronizedList(new ArrayList<Command>());
	}

	/**
	 * @see org.intelligentsia.dowsers.command.CommandHistory#push(org.intelligentsia.dowsers.command.Command)
	 */
	@Override
	public void push(final Command command) {
		commands.add(command);
	}

	/**
	 * @see org.intelligentsia.dowsers.command.CommandHistory#history()
	 */
	@Override
	public Collection<Command> history() {
		return Collections.unmodifiableCollection(commands);
	}

	/**
	 * @see org.intelligentsia.dowsers.command.CommandHistory#flush()
	 */
	@Override
	public void clear() {
		commands.clear();
	}

}
