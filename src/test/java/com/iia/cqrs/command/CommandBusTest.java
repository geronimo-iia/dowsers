/**
 * 
 */
package com.iia.cqrs.command;

import static junit.framework.Assert.*;

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
		
		FakeHelloCommand command = new FakeHelloCommand(commandBus, "John");
		assertEquals("John", command.getName());
		assertEquals("", commandHandler.getLastCalled());
		assertNotSame("John", commandHandler.getLastCalled());
		//Execute
		command.execute();
		//invariant
		assertEquals("John", command.getName());
		assertNotSame("", commandHandler.getLastCalled());
		assertEquals("John", commandHandler.getLastCalled());
	}
	
	@Test
	public void testOrdonnedCommandCalled() {
		new FakeHelloCommand(commandBus, "John").execute();
		new FakeHelloCommand(commandBus, "Johnny").execute();
		new FakeHelloCommand(commandBus, "Johnny BeGood").execute();
		
		assertNotSame("", commandHandler.getLastCalled());
		assertEquals( "Johnny BeGood", commandHandler.getLastCalled());
	}
	
}
