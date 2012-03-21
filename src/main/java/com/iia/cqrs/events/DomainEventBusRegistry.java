/**
 * 
 */
package com.iia.cqrs.events;

import com.iia.cqrs.Entity;

/**
 * DomainEventBusRegistry.
 * 
 * 
 * Each entity has to register the domain events and the internal event handlers with the base class.
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface DomainEventBusRegistry {

	/**
	 * Register an entity on internal event bus.
	 * 
	 * @param entity
	 */
	public <T extends Entity> void register(T entity);

}
