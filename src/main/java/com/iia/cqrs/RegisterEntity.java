/**
 * 
 */
package com.iia.cqrs;

/**
 * RegisterEntity declare methods to register entity on root entity.
 */
public interface RegisterEntity {

	/**
	 * Register specified entity.
	 * 
	 * @param entity
	 */
	public void register(Entity entity);
}
