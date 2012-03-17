/**
 * 
 */
package com.iia.cqrs.command;

import java.util.UUID;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface Command {
	
	public UUID getId();
}
