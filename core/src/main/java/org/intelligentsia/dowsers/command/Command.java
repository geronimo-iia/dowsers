/**
 * 
 */
package org.intelligentsia.dowsers.command;

import java.util.UUID;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * Command declares an interface for executing an operation.<br />
 * 
 * Commands are created on the client application and then send to the Domain
 * layer.<br />
 * Their purpose is to capture "Why did this change happen?".<br />
 * All these commands will be send to the Command Bus which will delegate each
 * command to the command handler or command handlers.<br />
 * This also effectively means that there is only one entry point into the
 * domain and that is via the Bus.<br />
 * The responsibility of these command handlers is to execute the appropriate
 * behavior on the domain.
 * 
 * 
 * The Command concept encapsulates method calls in objects allowing us to issue
 * requests without knowing the requested operation or the requesting object.
 * 
 * By default, "execute" method call command invoker.
 * 
 * 
 * Example of Command implementation:
 * 
 * <pre>
 * public class FakeHelloCommand extends Command {
 * 
 * 	private final String name;
 * 
 * 	public FakeHelloCommand(final CommandInvoker commandInvoker, final String name) throws NullPointerException {
 * 		super(commandInvoker);
 * 		this.name = name;
 * 	}
 * 
 * 	&#064;Override
 * 	public void execute() throws NullPointerException, IllegalArgumentException {
 * 		Preconditions.checkNotNull(name, &quot;name cannot be null&quot;);
 * 		Preconditions.checkArgument(!&quot;&quot;.equals(name), &quot;name must not be empty&quot;);
 * 		// call super
 * 		super.execute();
 * 	}
 * 
 * 	public String getName() {
 * 		return name;
 * 	}
 * }
 * </pre>
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public abstract class Command {

	/**
	 * Command identifier.
	 */
	private final UUID id;
	/**
	 * CommandInvoker instance.
	 */
	private final CommandInvoker commandInvoker;

	/**
	 * Build a new instance of <code>Command</code>
	 * 
	 * @param commandInvoker
	 *            command Invoker instance.
	 * 
	 * @throws NullPointerException
	 *             if commandInvoker is null
	 */
	public Command(final CommandInvoker commandInvoker) throws NullPointerException {
		this(UUID.randomUUID(), commandInvoker);
	}

	/**
	 * Build a new instance of Command with specified identifier.
	 * 
	 * @param id
	 *            identifier
	 * @param commandInvoker
	 *            command Invoker instance.
	 * @throws NullPointerException
	 *             if id or commandInvoker is null
	 */
	protected Command(final UUID id, final CommandInvoker commandInvoker) throws NullPointerException {
		super();
		this.id = Preconditions.checkNotNull(id);
		this.commandInvoker = Preconditions.checkNotNull(commandInvoker);
	}

	/**
	 * Execute method. Validation should occurs in this methods before invoking
	 * herself with command invoker.
	 */
	public void execute() {
		commandInvoker.invoke(this);
	}

	/**
	 * @return id of command
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return id.toString();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		return Objects.hashCode(id);
	}

	/**
	 * Command are compared on their id.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Command other = (Command) obj;
		return Objects.equal(other.getId(), getId());
	}

}
