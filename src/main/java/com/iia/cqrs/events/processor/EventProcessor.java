/**
 * 
 */
package com.iia.cqrs.events.processor;

import com.iia.cqrs.Entity;
import com.iia.cqrs.events.DomainEvent;

/**
 * EventProcessor manage event processing.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface EventProcessor {

	/**
	 * Process specified domain event on source entity.
	 * 
	 * @param entity
	 *            entity which raise event and listen
	 * @param domainEvent
	 *            domain Event to process.
	 */
	public void apply(Entity entity, DomainEvent domainEvent);

	/**
	 * Register handler of specified entity type.
	 * 
	 * @param <T>
	 * @param entityType
	 *            entity Type
	 * @throws IllegalStateException
	 *             if entity type cannot be registered
	 */
	public <T extends Entity> void register(Class<T> entityType) throws IllegalStateException;
}
