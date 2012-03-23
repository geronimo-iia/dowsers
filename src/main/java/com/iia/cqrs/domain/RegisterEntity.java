/**
 * 
 */
package com.iia.cqrs.domain;

/**
 * RegisterEntity declare methods to register entity on a container like domain entity.
 */
public interface RegisterEntity {

	/**
	 * Register specified entity.
	 * 
	 * @param entity
	 * 
	 * @throws NullPointerException
	 *             if entity is null
	 */
	public void register(Entity entity) throws NullPointerException;
}
