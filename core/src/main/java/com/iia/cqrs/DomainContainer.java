/**
 * 
 */
package com.iia.cqrs;

import com.iia.cqrs.command.CommandInvoker;
import com.iia.cqrs.command.CommandRegistry;
import com.iia.cqrs.domain.AggregateFactory;
import com.iia.cqrs.events.processor.EventProcessorProvider;

/**
 * DomainContainer declares methods to access on configured components.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface DomainContainer {

	public AggregateFactory getAggregateFactory();

	public CommandInvoker getCommandInvoker();

	public CommandRegistry getCommandRegistry();

	public EventProcessorProvider getEventProcessorProvider();
}
