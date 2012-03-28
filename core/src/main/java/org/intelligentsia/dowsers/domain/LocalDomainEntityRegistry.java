package org.intelligentsia.dowsers.domain;

/**
 * LocalDomainEntityRegistry declare methods to register local domain entity on a container: domain
 * entity.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface LocalDomainEntityRegistry {

	/**
	 * Register specified entity.
	 * 
	 * @param localDomainEntity a local domain entity instance.
	 * 
	 * @throws NullPointerException
	 *             if entity is null
	 */
	public void register(final LocalDomainEntity localDomainEntity) throws NullPointerException;
}
