/**
 * 
 */
package org.intelligentsia.dowsers.repository.eventsource.snapshot;

import org.intelligentsia.dowsers.domain.ConcurrencyException;

import com.google.common.base.Preconditions;
import com.google.common.cache.LoadingCache;

/**
 * MemoryCacheSnapshotStorage.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MemoryCacheSnapshotStorage implements SnapShotStorage {

	private final LoadingCache<String, SnapShot> snapshots;

	public MemoryCacheSnapshotStorage(final LoadingCache<String, SnapShot> cache) {
		super();
		snapshots = Preconditions.checkNotNull(cache);
	}

	/**
	 * @see org.intelligentsia.dowsers.repository.eventsource.snapshot.SnapShotStorage#findLatestSnapShot(java.util.UUID)
	 */
	@Override
	public <T> SnapShot findLatestSnapShot(final String identity) {
		return snapshots.getIfPresent(identity);
	}

	/**
	 * @see org.intelligentsia.dowsers.repository.eventsource.snapshot.SnapShotStorage#store(org.intelligentsia.dowsers.repository.eventsource.snapshot.SnapShot)
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
