/**
 * 
 */
package org.intelligentsia.cqrs.eventstore;

import java.util.UUID;

import org.intelligentsia.cqrs.eventstore.ConcurrencyException;
import org.intelligentsia.cqrs.eventstore.EmptyResultException;
import org.intelligentsia.dowsers.eventstore.StreamEverExistsException;

/**
 * Repository act as a repository for RootEntity instance.
 * 
 * @author JGT
 * 
 */
public interface Repository {

	/**
	 * Find an entity of specified type and identity. This root entity will not
	 * be automatically added to current repository session.
	 * 
	 * @param type
	 *            entity type
	 * @param identity
	 *            identity
	 * @return a RootEntity instance.
	 * @throws EntityRootNotFoundException
	 *             if entity is not found
	 * @throws NullPointerException
	 *             if type or identity is null
	 */
	public <T extends RootEntity> T findByIdentity(Class<T> type, UUID identity) throws EntityRootNotFoundException, NullPointerException;

	/**
	 * Find an entity of specified type and versioned identifier. The returned
	 * entity instance will be added to current repository session.
	 * 
	 * @param type
	 *            entity type
	 * @param versionedIdentifier
	 *            versioned identifier
	 * @return a RootEntity instance.
	 * @throws EntityRootNotFoundException
	 *             if entity is not found
	 * @throws ConcurrencyException
	 *             if different version of the returned entity is ever in
	 *             current session
	 * @throws NullPointerException
	 *             if type or versionedIdentifier is null
	 */
	public <T extends RootEntity> T findByVersionedIdentifier(Class<T> type, VersionedIdentifier versionedIdentifier) throws EntityRootNotFoundException, ConcurrencyException, NullPointerException;

	/**
	 * Add an entity on this repository
	 * 
	 * @param rootEntity
	 *            root entity to add
	 * @throws IllegalArgumentException
	 *             if root entity has no unsaved changes
	 * @throws ConcurrencyException
	 *             if different version of the returned entity is ever in
	 *             current repository session
	 * @throws NullPointerException
	 *             if rootEntity is null
	 */
	public <T extends RootEntity> void add(T rootEntity) throws ConcurrencyException, IllegalArgumentException, NullPointerException;

	/**
	 * Commit all session change. Use this methods with care. A session commit
	 * should occur at the end of command processing.
	 * 
	 * @throws StreamEverExistsException
	 * @throws EmptyResultException
	 * @throws ConcurrencyException
	 */
	public void commitChanges() throws StreamEverExistsException, EmptyResultException, ConcurrencyException;

}
