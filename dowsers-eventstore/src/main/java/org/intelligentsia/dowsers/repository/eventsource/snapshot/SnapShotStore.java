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

import org.springframework.expression.spel.ast.Identifier;

import org.intelligentsia.dowsers.domain.ConcurrencyException;
import org.intelligentsia.dowsers.eventstore.EmptyResultException;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface SnapShotStore {

	/**
	 * @param identity
	 * @return latest Snapshot for specified identity.
	 */
	public <T> SnapShot findLatestSnapShot(Class<T> type, String identity) throws EmptyResultException;

	/**
	 * @param identifier
	 * @return Snapshot instance for specified identity and version.
	 * @throw EmptyResultException if no snapshot exist for specified
	 *        identifier.
	 */
	public SnapShot getSpecificSnapShot(String identity, long version) throws EmptyResultException;

	/**
	 * @param identifier
	 * @return Snapshot instance for specified identity and version.
	 * @throw EmptyResultException if no snapshot exist for specified identity
	 *        and/or before specified version
	 */
	//public SnapShot getSnapShotBefore(String identity, long version) throws EmptyResultException;

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
	//public long getCount(String identity);

	/**
	 * Remove all Snapshot for specified identifier with version less or equal
	 * to {@link Identifier#getVersion()}.
	 * 
	 * @param identifier
	 *            identifier
	 */
	public void remove(String identifier, long version);
	
	public void removeUpToVersion(String identifier, long version);
}
