/**
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
