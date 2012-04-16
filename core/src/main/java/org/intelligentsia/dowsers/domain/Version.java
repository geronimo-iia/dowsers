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
package org.intelligentsia.dowsers.domain;

import java.io.Serializable;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
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
 */
public class Version implements Serializable, Comparable<Version> {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Initial Version Value (0).
	 */
	@VisibleForTesting
	static final transient long INITIAL_VERSION = 0;
	/**
	 * Latest version value (Long.MAX_VALUE).
	 */
	@VisibleForTesting
	static final transient long LATEST_VERSION = Long.MAX_VALUE;

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
		Preconditions.checkArgument(version >= Version.INITIAL_VERSION, "version must be greater than or equal to INITIAL_VERSION");
		this.version = version;
	}

	/**
	 * @return an initial version.
	 */
	public static Version forInitialVersion() {
		return Version.forSpecificVersion(Version.INITIAL_VERSION);
	}

	/**
	 * @return an latest version
	 */
	public static Version forLatestVersion() {
		return Version.forSpecificVersion(Version.LATEST_VERSION);
	}

	/**
	 * @param version
	 *            version value
	 * @return an instanceof specified version
	 * 
	 * @throws IllegalArgumentException
	 *             if version < INITIAL_VERSION
	 */
	public static Version forSpecificVersion(final long version) throws IllegalArgumentException {
		return new Version(version);
	}

	/**
	 * @return Boolean.TRUE if this version is an initial version.
	 */
	public boolean isForInitialVersion() {
		return version == Version.INITIAL_VERSION;
	}

	/**
	 * @return Boolean.TRUE if this version is a latest version.
	 */
	public boolean isForLatestVersion() {
		return version == Version.LATEST_VERSION;
	}

	/**
	 * @return Boolean.TRUE if this version is specific (not latest version)
	 */
	public boolean isForSpecificVersion() {
		return !isForLatestVersion();
	}

	/**
	 * @return next version.
	 */
	public Version nextVersion() {
		if (isForLatestVersion()) {
			return this;
		} else {
			return Version.forSpecificVersion(version + 1);
		}
	}

	@Override
	public String toString() {
		return Long.toString(version);
	}

	/**
	 * @return version value
	 */
	public long toLong() {
		return version;
	}

	/**
	 * @param value
	 *            string value of version
	 * @return a Version instance
	 * @throws NumberFormatException
	 *             if value is not a number
	 * @throws IllegalArgumentException
	 *             if value < INITIAL_VERSION
	 */
	public static Version parseVersion(final String value) throws NumberFormatException, IllegalArgumentException {
		return Version.parseVersion(Long.parseLong(value));
	}

	/**
	 * @param value
	 *            string value of version
	 * @return a Version instance
	 * @throws IllegalArgumentException
	 *             if value < INITIAL_VERSION
	 */
	public static Version parseVersion(final Long value) throws IllegalArgumentException {
		return Version.forSpecificVersion(value);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		// take long implementation of hashCode
		return (int) (version ^ version >>> 32);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Version other = (Version) obj;
		return Objects.equal(other.toLong(), toLong());
	}

	/**
	 * Returns a negative integer, zero, or a positive integer as this object
	 * version is less than, equal to, or greater than the specified object
	 * version.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final Version o) {
		final long thisVal = version;
		final long anotherVal = o.version;
		return thisVal < anotherVal ? -1 : thisVal == anotherVal ? 0 : 1;
	}
}
