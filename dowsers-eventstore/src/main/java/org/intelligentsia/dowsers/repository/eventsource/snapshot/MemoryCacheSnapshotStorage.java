/**
 *        Licensed to the Apache Software Foundation (ASF) under one
 *        or more contributor license agreements.  See the NOTICE file
 *        distributed with this work for additional information
 *        regarding copyright ownership.  The ASF licenses this file
 *        to you under the Apache License, Version 2.0 (the
 *        "License"); you may not use this file except in compliance
 *        with the License.  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing,
 *        software distributed under the License is distributed on an
 *        "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *        KIND, either express or implied.  See the License for the
 *        specific language governing permissions and limitations
 *        under the License.
 *
 */
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
