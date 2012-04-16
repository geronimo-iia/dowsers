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
package org.intelligentsia.dowsers.event;

import java.util.Collection;

import org.intelligentsia.dowsers.domain.Version;

/**
 * DomainEventProvider declares methods.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface DomainEventProvider {

	/**
	 * @return identity of root entity
	 */
	public String getIdentity();

	/**
	 * Returns the current version number of the aggregate, or <code>null</code>
	 * if the aggregate is newly created. This version must reflect the version
	 * number of the aggregate on which changes are applied.
	 * <p/>
	 * Each time the aggregate is <em>modified and stored</em> in a repository,
	 * the version number must be increased by at least 1. This version number
	 * can be used by optimistic locking strategies and detection of conflicting
	 * concurrent modification.
	 * <p/>
	 * Typically the sequence number of the last committed event on this
	 * aggregate is used as version number.
	 * 
	 * @return the current version number of this aggregate, or
	 *         <code>null</code> if no events were ever committed
	 */
	public Version getVersion();

	/**
	 * Loading historical domain events.It is basically apply events of the
	 * given aggregate.
	 * 
	 * @param history
	 *            list of DomainEvent
	 * @param version
	 *            current version of this event provider
	 * 
	 * @throws IllegalStateException
	 *             if error occurs when load history
	 */
	public void loadFromHistory(final Iterable<? extends DomainEvent> history, Version version) throws IllegalStateException;

	/**
	 * Returns an Iterable<DomainEvent> to the events in the aggregate that have
	 * been raised since creation or the last commit.
	 * 
	 * @return an iterable instance of of uncommitted changes.
	 */
	public Collection<DomainEvent> getUncommittedChanges();

	/**
	 * @return true if this aggregate has uncommite changes.
	 */
	public boolean hasUncommittedChanges();

	/**
	 * Mark all changes as committed: Clears the events currently marked as
	 * "uncommitted".
	 * 
	 * @param version
	 *            set specific version associated with this commit.
	 */
	public void markChangesCommitted(Version version);

}
