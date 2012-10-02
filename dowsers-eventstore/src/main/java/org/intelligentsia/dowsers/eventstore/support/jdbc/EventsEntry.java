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

import hirondelle.date4j.DateTime;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EventsEntry {
	private final String identity;
	private final String timeStamp;
	private final long version;
	private final byte[] data;

	/**
	 * Build a new instance of EventsEntry.
	 * 
	 * @param identity
	 * @param timeStamp
	 * @param version
	 * @param data
	 */
	public EventsEntry(String identity, String timeStamp, long version, byte[] data) {
		super();
		this.identity = identity;
		this.timeStamp = timeStamp;
		this.version = version;
		this.data = data;
	}

	/**
	 * @return the identity
	 */
	public String getIdentity() {
		return identity;
	}

	/**
	 * @return the timeStamp
	 */
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @return the version
	 */
	public long getVersion() {
		return version;
	}

	/**
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

}
