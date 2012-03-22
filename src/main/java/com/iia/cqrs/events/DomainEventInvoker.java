/**
 * 
 */
package com.iia.cqrs.events;


/**
 * DomainEventInvoker.
 * 
 * @author jgt
 * 
 */
public interface DomainEventInvoker {
	/**
	 * Process specified domain event
	 * 
	 * @param domainEvent
	 *            domain Event to process.
	 */
	public <T extends DomainEvent> void apply(T domainEvent);
}
