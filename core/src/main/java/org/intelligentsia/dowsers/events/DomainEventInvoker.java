/**
 * 
 */
package org.intelligentsia.dowsers.events;

/**
 * DomainEventInvoker declares methods to apply Domain Event from Entity.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
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
