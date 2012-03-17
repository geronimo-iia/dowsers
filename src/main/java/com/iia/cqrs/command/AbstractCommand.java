/**
 * 
 */
package com.iia.cqrs.command;

import java.util.UUID;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class AbstractCommand implements Command {

	private final UUID id;

	/**
	 * Build a new instance of <code>AbstractCommand</code>
	 * 
	 * @param id
	 */
	public AbstractCommand(UUID id) {
		super();
		this.id = id;
	}

	/**
	 * @see com.iia.cqrs.command.Command#getId()
	 */
	@Override
	public UUID getId() {
		return id;
	}

}
