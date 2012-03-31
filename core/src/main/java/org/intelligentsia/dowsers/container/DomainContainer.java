/**
 * 
 */
package org.intelligentsia.dowsers.container;

import org.intelligentsia.dowsers.command.CommandHandlerRegistry;
import org.intelligentsia.dowsers.command.CommandInvoker;
import org.intelligentsia.dowsers.domain.AggregateFactory;
import org.intelligentsia.dowsers.event.processor.EventProcessorProvider;

/**
 * DomainContainer declares methods to access on configured components.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface DomainContainer {

	public AggregateFactory getAggregateFactory();

	public CommandInvoker getCommandInvoker();

	public CommandHandlerRegistry getCommandRegistry();

	public EventProcessorProvider getEventProcessorProvider();

}
