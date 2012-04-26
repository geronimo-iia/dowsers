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
package org.intelligentsia.dowsers.core;

import com.google.common.base.Preconditions;

/**
 * Version manage sequence of version.
 * 
 * The version is used to detect concurrency violations, meaning this is used to
 * prevent conflicts that occur because between the time the command was send
 * and the entity was saved an other user or process has updated the same
 * entity.
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public enum Version {
	/**
	 * Initial value of all version.
	 */
	INITIAL(0),
	/**
	 * Latest value of all version.
	 */
	LATEST(Long.MAX_VALUE);
	;

	/**
	 * Version instance (long).
	 */
	private final long version;

	/**
	 * Build a new instance of Version.
	 * 
	 * @param version
	 *            version value (must be greater than or equal to
	 *            INITIAL_VERSION)
	 * @throws IllegalArgumentException
	 *             if version < INITIAL_VERSION
	 */
	private Version(final long version) throws IllegalArgumentException {
		this.version = Version.forSpecificVersion(version);
	}

	/**
	 * @param version
	 *            version value
	 * @return an instanceof specified version
	 * 
	 * @throws IllegalArgumentException
	 *             if version < INITIAL_VERSION
	 */
	public static long forSpecificVersion(final long version) throws IllegalArgumentException {
		Preconditions.checkArgument(version >= 0, "version must be greater than or equal to INITIAL_VERSION");
		return version;
	}

	/**
	 * @return Initial version value.
	 */
	public static long forInitialVersion() {
		return INITIAL.toLong();
	}

	/**
	 * @return latest version value.
	 */
	public static long forLatestVersion() {
		return LATEST.toLong();
	}

	/**
	 * @param version
	 *            version value to test
	 * @return Boolean.TRUE if specified version is an initial version.
	 */
	public static boolean isForInitialVersion(final long version) {
		return version == Version.INITIAL.toLong();
	}

	/**
	 * @param version
	 *            version value to test
	 * @return Boolean.TRUE if specified version is a latest version.
	 */
	public static boolean isForLatestVersion(final long version) {
		return version == Version.LATEST.toLong();
	}

	/**
	 * @param version
	 *            version value to test
	 * @return Boolean.TRUE if specified version is specific (not latest
	 *         version)
	 */
	public static boolean isForSpecificVersion(final long version) {
		return !Version.isForLatestVersion(version);
	}

	/**
	 * @param version
	 *            version value to test
	 * @return next version.
	 */
	public static long nextVersion(final long version) {
		if (Version.isForLatestVersion(version)) {
			return LATEST.toLong();
		} else {
			return Version.forSpecificVersion(version + 1);
		}
	}

	/**
	 * @return version value
	 */
	public long toLong() {
		return version;
	}

}
