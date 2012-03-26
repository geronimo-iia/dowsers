/**
 * 
 */
package org.intelligentsia.dowsers.events;

import org.intelligentsia.dowsers.annotation.Note;
import org.intelligentsia.dowsers.domain.Entity;

/**
 * DomainEventBusRegistry.
 * 
 * 
 * Each entity has to register the domain events and the internal event handlers
 * with the base class.
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
@Note("Not used")
public interface DomainEventBusRegistry {

	/**
	 * Register an entity on internal event bus.
	 * 
	 * @param entity
	 */
	public <T extends Entity> void register(T entity);

}
