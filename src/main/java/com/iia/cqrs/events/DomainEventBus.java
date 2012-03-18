/**
 * 
 */
package com.iia.cqrs.events;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface DomainEventBus {
	
	public <T extends DomainEvent> void apply(T domainEvent);
	
	public void register(Object object);
}
