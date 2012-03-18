/**
 * 
 */
package com.iia.cqrs.command;

import java.util.UUID;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public abstract class Command {

	private final UUID id;

	/**
	 * Build a new instance of <code>Command</code>
	 */
	public Command() {
		super();
		id = UUID.randomUUID();
	}

	public Command(UUID id) {
		super();
		this.id = id;
	}

	/**
	 * @return id of command
	 */
	public UUID getId() {
		return id;
	}

}
