/**
 * 
 */
package com.iia.cqrs.storage.snapshot;

import java.util.UUID;

import com.iia.cqrs.storage.ConcurrencyException;

/**
 * SnapShotStorage.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public interface SnapShotStorage {
	/**
	 * @param identity
	 * @return latest Snapshot for specified identity.
	 */
	public <T> SnapShot findLatestSnapShot(UUID identity);

	/**
	 * Store specified snapshot.
	 * 
	 * @param snapShot
	 *            snapshot to store
	 * @throws ConcurrencyException
	 *             thrown when the expected version is older than actual version
	 *             of the snapShot.
	 */
	public void store(SnapShot snapShot) throws ConcurrencyException;
}
