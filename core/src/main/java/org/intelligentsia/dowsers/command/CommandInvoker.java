/**
 * 
 */
package org.intelligentsia.dowsers.command;

/**
 * CommandInvoker asks the command to carry out the request.
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface CommandInvoker {
	/**
	 * Invoke specified command.
	 * 
	 * @param command
	 *            command to execute
	 */
	public void invoke(final Command command);
}
