/**
 * 
 */
package org.intelligentsia.dowsers.storage.snapshot;

import java.util.UUID;

import org.intelligentsia.dowsers.domain.Identifier;
import org.intelligentsia.dowsers.storage.ConcurrencyException;
import org.intelligentsia.dowsers.storage.EmptyResultException;


/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface SnapShotProvider {

	/**
	 * @param identity
	 * @return latest Snapshot for specified identity.
	 */
	public <T> SnapShot findLatestSnapShot(Class<T> type, UUID identity) throws EmptyResultException;

	/**
	 * @param identifier
	 * @return Snapshot instance for specified identity and version.
	 * @throw EmptyResultException if no snapshot exist for specified
	 *        identifier.
	 */
	public SnapShot getSpecificSnapShot(Identifier identifier) throws EmptyResultException;

	/**
	 * @param identifier
	 * @return Snapshot instance for specified identity and version.
	 * @throw EmptyResultException if no snapshot exist for specified identity
	 *        and/or before specified version
	 */
	public SnapShot getSnapShotBefore(Identifier identifier) throws EmptyResultException;

	/**
	 * Store specified snapshot.
	 * 
	 * @param snapShot
	 *            snapshot to store
	 * @throws ConcurrencyException
	 *             if a snapshot with same identifier is ever present.
	 */
	public void store(SnapShot snapShot) throws ConcurrencyException;

	/**
	 * @param identity
	 * @return number of snapshot for specified identity.
	 */
	public long getCount(UUID identity);

	/**
	 * Remove all Snapshot for specified identifier with version less or equal
	 * to {@link Identifier#getVersion()}.
	 * 
	 * @param identifier
	 *            identifier
	 */
	public void remove(Identifier identifier);
}
