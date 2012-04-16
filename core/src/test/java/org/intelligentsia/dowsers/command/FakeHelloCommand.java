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

import org.intelligentsia.dowsers.command.Command;
import org.intelligentsia.dowsers.command.CommandInvoker;

import com.google.common.base.Preconditions;

/**
 * FakeHelloCommand.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class FakeHelloCommand extends Command {

	/**
	 * Name instance.
	 */
	private final String name;

	/**
	 * Build a new instance of FakeHelloCommand.
	 * 
	 * @param commandInvoker
	 * @param name
	 * @throws NullPointerException
	 */
	public FakeHelloCommand(final CommandInvoker commandInvoker, final String name) throws NullPointerException {
		super(commandInvoker);
		this.name = name;
	}

	/**
	 * @see org.intelligentsia.dowsers.command.Command#execute()
	 */
	@Override
	public void execute() throws NullPointerException, IllegalArgumentException {
		Preconditions.checkNotNull(name, "name cannot be null");
		Preconditions.checkArgument(!"".equals(name), "name must not be empty");
		// call super
		super.execute();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
