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
package org.intelligentsia.dowsers.eventstore.support.jdbc;

import net.sf.persist.annotations.Table;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
@Table(name = "EventStreamEntry")
public class EventStreamEntry {
	private final String identity;
	private final String name;
	private String timeStamp;
	private long currentVersion;

	/**
	 * Build a new instance of <code>EventStreamEntry</code>
	 * 
	 * @param identity
	 * @param name
	 * @param timeStamp
	 * @param currentVersion
	 */
	public EventStreamEntry(String identity, String name, String timeStamp, long currentVersion) {
		super();
		this.identity = identity;
		this.name = name;
		this.timeStamp = timeStamp;
		this.currentVersion = currentVersion;
	}

	/**
	 * @return the timeStamp
	 */
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp
	 *            the timeStamp to set
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * @return the currentVersion
	 */
	public long getCurrentVersion() {
		return currentVersion;
	}

	/**
	 * @param currentVersion
	 *            the currentVersion to set
	 */
	public void setCurrentVersion(long currentVersion) {
		this.currentVersion = currentVersion;
	}

	/**
	 * @return the identity
	 */
	public String getIdentity() {
		return identity;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
