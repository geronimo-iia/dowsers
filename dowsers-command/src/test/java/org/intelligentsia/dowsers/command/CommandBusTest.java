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

import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * CommandBusTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class CommandBusTest {

	@Before
	public void setup() {

	}

	@Test
	public void testCalled() {
		final CommandBus commandBus = CommandBus.newCommandBus();
		final FakeHelloCommandHandler commandHandler = new FakeHelloCommandHandler(commandBus);

		final FakeHelloCommand command = new FakeHelloCommand(commandBus, "John");
		Assert.assertEquals("John", command.getName());
		// Execute
		command.execute();
		// invariant
		Assert.assertEquals("John", command.getName());
		Assert.assertNotSame("", commandHandler.getLastCalled());
		Assert.assertEquals("John", commandHandler.getLastCalled());
	}

	@Test
	public void testOrdonnedCommandCalled() {
		final CommandBus commandBus = CommandBus.newCommandBus();
		final FakeHelloCommandHandler commandHandler = new FakeHelloCommandHandler(commandBus);

		Assert.assertEquals(null, commandHandler.getLastCalled());
		new FakeHelloCommand(commandBus, "John");
		Assert.assertEquals("John", commandHandler.getLastCalled());
		new FakeHelloCommand(commandBus, "Johnny");
		Assert.assertEquals("Johnny", commandHandler.getLastCalled());
		new FakeHelloCommand(commandBus, "Johnny BeGood");
		Assert.assertNotSame("", commandHandler.getLastCalled());
		Assert.assertEquals("Johnny BeGood", commandHandler.getLastCalled());
	}

	@Test
	public void testCommandHistory() {
		final CommandHistory history = new CommandHistoryInMemory();
		final CommandBus commandBus = CommandBus.newCommandBus(history);
		final FakeHelloCommandHandler commandHandler = new FakeHelloCommandHandler(commandBus);

		final FakeHelloCommand one = new FakeHelloCommand(commandBus, "A");
		final FakeHelloCommand two = new FakeHelloCommand(commandBus, "B");
		final FakeHelloCommand three = new FakeHelloCommand(commandBus, "C");

		Assert.assertEquals("C", commandHandler.getLastCalled());

		final Iterator<Command> commands = history.history().iterator();
		Assert.assertNotNull(commands);
		Assert.assertEquals(one.getId(), commands.next().getId());
		Assert.assertEquals(two.getId(), commands.next().getId());
		Assert.assertEquals(three.getId(), commands.next().getId());
	}

}
