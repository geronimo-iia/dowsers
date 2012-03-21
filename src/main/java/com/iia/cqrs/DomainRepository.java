/**
 * 
 */
package com.iia.cqrs;

import java.util.UUID;

import com.iia.cqrs.annotation.TODO;
import com.iia.cqrs.annotation.TODOs;

/**
 * DomainRepository interfaces.
 * 
 * 
 * Domain is completely write only, so the repository only has to be able to Get
 * an Entity by its Id and it must be able to save it.<br />
 * DomainRepository completely get rid of any impedance mismatch between the
 * domain and the persistence layer.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface DomainRepository {

	/**
	 * Find entity with the specified identity.
	 * 
	 * @param expectedType
	 *            expected type entity
	 * @param identity
	 *            identity what we looking for
	 * @return an entity instance of the expected type and identity or null if
	 *         none exists.
	 * @throws NullPointerException
	 *             if expectedType or identity is null
	 */
	@TODO("Evaluate gain of adding expected type parameter.")
	public <T> T findByIdentifier(Class<T> expectedType, UUID identity) throws NullPointerException;

	/**
	 * Add specific entity to the domain repository.
	 * 
	 * @param entity
	 *            entity to add
	 * @throws NullPointerException
	 *             if entity is null
	 */
	@TODOs({ @TODO("Specify concurrent runtime exception?"), @TODO("Raise something if we add a deleted entity ?") })
	public <T> void add(T entity) throws NullPointerException;

	/**
	 * Remove entity
	 * 
	 * @param expectedType
	 *            expected type entity
	 * @param identity
	 *            identity of entity to remove
	 * @throws NullPointerException
	 *             if expectedType or identity is null
	 */
	public <T> void remove(Class<T> expectedType, UUID identity) throws NullPointerException;

}
