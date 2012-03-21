/**
 * 
 */
package com.iia.cqrs.events;

import com.iia.cqrs.Entity;
import com.iia.cqrs.annotation.TODO;

/**
 * EventProcessor.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface EventProcessor {

	/**
	 * Process specified domain event on source entity.
	 * 
	 * @param source
	 * @param domainEvent
	 *            domain Event to process.
	 */
	public <T extends DomainEvent> void apply(Entity source, T domainEvent);

	/**
	 * Register an entity on thiso processor.
	 * 
	 * @param entityType
	 *            entity type
	 */
	@TODO("Considere to remove this method from EventProcesor Interface")
	public <T extends Entity> void register(Class<T> entityType);
}
