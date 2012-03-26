package org.intelligentsia.dowsers.domain;

/**
 * EntityRegistry declare methods to register entity on a container like domain
 * entity.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface EntityRegistry {

	/**
	 * Register specified entity.
	 * 
	 * @param entity
	 * 
	 * @throws NullPointerException
	 *             if entity is null
	 */
	public void register(final Entity entity) throws NullPointerException;
}
