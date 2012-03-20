/**
 * 
 */
package com.iia.cqrs.storage.snapshot;

import java.util.UUID;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.iia.cqrs.storage.ConcurrencyException;

/**
 * MemoryCacheSnapshotBuilder.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MemoryCacheSnapshotBuilder implements SnapShotStorage {

	private final Cache<UUID, SnapShot> snapshots;

	public MemoryCacheSnapshotBuilder(final Cache<UUID, SnapShot> cache) {
		super();
		snapshots = Preconditions.checkNotNull(cache);
	}

	/**
	 * @see com.iia.cqrs.storage.snapshot.SnapShotStorage#findLatestSnapShot(java.util.UUID)
	 */
	@Override
	public <T> SnapShot findLatestSnapShot(final UUID identity) {
		return snapshots.getIfPresent(identity);
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
