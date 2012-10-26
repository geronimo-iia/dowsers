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

/**
 * FakeHelloCommandHandler which implement an handler for FakeHelloCommand and
 * register himself on a CommandHandlerRegistry instance..
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class FakeHelloCommandHandler implements Handler<FakeHelloCommand> {

	/**
	 * for test purpose.
	 */
	private String lastCalled = null;

	/**
	 * Build a new instance of FakeHelloCommandHandler.
	 * 
	 * @param commandRegistry
	 *            command Registry where to register
	 * @throws NullPointerException
	 *             if commandRegistry is null
	 */
	public FakeHelloCommandHandler(final CommandHandlerRegistry commandRegistry) throws NullPointerException {
		super();
		Preconditions.checkNotNull(commandRegistry).register(this);
	}

	/**
	 * @see org.intelligentsia.dowsers.core.Handler#handle(java.lang.Object)
	 */
	@Override
	public void handle(final FakeHelloCommand command) {
		lastCalled = command.getName();
	}

	/**
	 * @return the lastCalled
	 */
	public String getLastCalled() {
		return lastCalled;
	}

}
