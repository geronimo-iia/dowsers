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
 * Sequence manage sequence value.
 * 
 * The sequence is used to detect concurrency violations, meaning this is used
 * to prevent conflicts that occur because between the time the command was send
 * and the entity was saved an other user or process has updated the same
 * entity.
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public enum Sequence {
	/**
	 * Initial value of all sequence.
	 */
	INITIAL(0),
	/**
	 * Latest value of all sequence.
	 */
	LATEST(Long.MAX_VALUE);
	;

	/**
	 * sequence instance (long).
	 */
	private final long sequence;

	/**
	 * Build a new instance of Version.
	 * 
	 * @param sequence
	 *            sequence value (must be greater than or equal to
	 *            Sequence.INITIAL)
	 * @throws IllegalArgumentException
	 *             if sequence < INITIAL
	 */
	private Sequence(final long sequence) throws IllegalArgumentException {
		this.sequence = Sequence.forSpecificSequence(sequence);
	}

	/**
	 * @param sequence
	 *            sequence value
	 * @return an instance of specified sequence
	 * 
	 * @throws IllegalArgumentException
	 *             if sequence < INITIAL
	 */
	public static long forSpecificSequence(final long version) throws IllegalArgumentException {
		Preconditions.checkArgument(version >= 0, "sequence must be greater than or equal to INITIAL");
		return version;
	}

	/**
	 * @return Initial sequence value.
	 */
	public static long forInitialSequence() {
		return INITIAL.toLong();
	}

	/**
	 * @return latest sequence value.
	 */
	public static long forLatestSequence() {
		return LATEST.toLong();
	}

	/**
	 * @param sequence
	 *            sequence value to test
	 * @return Boolean.TRUE if specified sequence is an initial sequence.
	 */
	public static boolean isForInitialSequence(final long version) {
		return version == Sequence.INITIAL.toLong();
	}

	/**
	 * @param sequence
	 *            sequence value to test
	 * @return Boolean.TRUE if specified sequence is a latest sequence.
	 */
	public static boolean isForLatestSequence(final long version) {
		return version == Sequence.LATEST.toLong();
	}

	/**
	 * @param sequence
	 *            sequence value to test
	 * @return Boolean.TRUE if specified sequence is specific (not latest
	 *         sequence)
	 */
	public static boolean isForSpecificSequence(final long version) {
		return !Sequence.isForLatestSequence(version);
	}

	/**
	 * @param sequence
	 *            sequence value to test
	 * @return next sequence.
	 */
	public static long nextSequence(final long version) {
		if (Sequence.isForLatestSequence(version)) {
			return LATEST.toLong();
		} else {
			return Sequence.forSpecificSequence(version + 1);
		}
	}

	/**
	 * @return sequence value
	 */
	public long toLong() {
		return sequence;
	}

}
