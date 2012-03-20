/**
 * 
 */
package com.iia.cqrs.storage.snapshot;

import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;
import com.iia.cqrs.storage.ConcurrencyException;

/**
 * MemoryMapSnapshotStorage.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MemoryMapSnapshotStorage implements SnapShotStorage {

	private final Map<UUID, SnapShot> snapshots;

	/**
	 * Build a new instance of MemoryMapSnapshotStorage.
	 */
	public MemoryMapSnapshotStorage() {
		super();
		snapshots = Maps.newConcurrentMap();
	}

	/**
	 * @see com.iia.cqrs.storage.snapshot.SnapShotStorage#findLatestSnapShot(java.util.UUID)
	 */
	@Override
	public <T> SnapShot findLatestSnapShot(final UUID identity) {
		return snapshots.get(identity);
	}

	/**
	 * @see com.iia.cqrs.storage.snapshot.SnapShotStorage#store(com.iia.cqrs.storage.snapshot.SnapShot)
	 */
	@Override
	public void store(final SnapShot snapshot) throws ConcurrencyException {
		final SnapShot current = findLatestSnapShot(snapshot.getIdentity());
		if (current != null) {
			if (snapshot.getVersion() < current.getVersion()) {
				throw new ConcurrencyException(snapshot.getIdentity(), "A newer snapshot version exists");
			}
		}
		snapshots.put(snapshot.getIdentity(), snapshot);
	}

}
