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
package org.intelligentsia.dowsers.event.snapshot;

import java.util.List;

import org.intelligentsia.dowsers.event.DomainEvent;

/**
 * SnapshotEvent.
 * 
 * Idea behind his to store a summary of all domain event that construct actual
 * entity.
 * 
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class SnapshotEvent extends DomainEvent {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -1653888378364399891L;

	/**
	 * Minimal summary of what happens on related entity.
	 */
	private final List<DomainEvent> summary;

	/**
	 * Entity version.
	 */
	private final long version;

	/**
	 * 
	 * Build a new instance of SnapshotEvent.
	 * 
	 * @param entityIdentity
	 *            entity Identity
	 * @param version
	 *            version of aggregate
	 * @param summary
	 *            domain event summary
	 */
	public SnapshotEvent(final String entityIdentity, final long version, final List<DomainEvent> summary) {
		super(entityIdentity);
		this.summary = summary;
		this.version = version;
	}

	/**
	 * @return the version
	 */
	public long getVersion() {
		return version;
	}

	/**
	 * @return the summary
	 */
	public List<DomainEvent> getSummary() {
		return summary;
	}

}
