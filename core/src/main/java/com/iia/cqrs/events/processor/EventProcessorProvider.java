/**
 * 
 */
package com.iia.cqrs.events.processor;

import com.iia.cqrs.domain.Entity;

/**
 * EventProcessorProvider provide EventProcessor for specified entity type.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface EventProcessorProvider {

	/**
	 * @param <T>
	 * @param entityType
	 *            entity Type
	 * @return EventProcessor for specified entity type
	 * @throws RuntimeException
	 *             if no EventProcessor if found for specified entity type.
	 */
	public <T extends Entity> EventProcessor get(Class<T> entityType) throws RuntimeException;

}
