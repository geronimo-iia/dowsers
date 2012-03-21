/**
 * 
 */
package com.iia.cqrs.events;


/**
 * DomainEventBusInvoker. 
 *
 *  @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface DomainEventBusInvoker {
 
	public <T extends DomainEvent> void apply(T domainEvent);
}
