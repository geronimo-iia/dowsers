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

import junit.framework.Assert;

import org.intelligentsia.dowsers.command.CommandBus;
import org.junit.Before;
import org.junit.Test;

/**
 * CommandBusTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class CommandBusTest {

	private CommandBus commandBus;
	private FakeHelloCommandHandler commandHandler;

	@Before
	public void setup() {
		commandBus = new CommandBus();
		commandHandler = new FakeHelloCommandHandler(commandBus);
	}

	@Test
	public void testCalled() {

		final FakeHelloCommand command = new FakeHelloCommand(commandBus, "John");
		Assert.assertEquals("John", command.getName());
		Assert.assertEquals("", commandHandler.getLastCalled());
		Assert.assertNotSame("John", commandHandler.getLastCalled());
		// Execute
		command.execute();
		// invariant
		Assert.assertEquals("John", command.getName());
		Assert.assertNotSame("", commandHandler.getLastCalled());
		Assert.assertEquals("John", commandHandler.getLastCalled());
	}

	@Test
	public void testOrdonnedCommandCalled() {
		new FakeHelloCommand(commandBus, "John").execute();
		new FakeHelloCommand(commandBus, "Johnny").execute();
		new FakeHelloCommand(commandBus, "Johnny BeGood").execute();

		Assert.assertNotSame("", commandHandler.getLastCalled());
		Assert.assertEquals("Johnny BeGood", commandHandler.getLastCalled());
	}

}
