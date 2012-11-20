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

import org.junit.Assert;
import org.junit.Test;

/**
 * SequenceTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class SequenceTest {

	private final long a = Sequence.forSpecificSequence(3);

	@Test
	public void shouldStoreSequence() {
		Assert.assertEquals(3, a);
	}

	@Test
	public void testForLatestSequence() {
		final long version = Sequence.forLatestSequence();
		Assert.assertEquals(Sequence.LATEST.toLong(), version);
		Assert.assertNotSame(Sequence.INITIAL.toLong(), version);
		Assert.assertTrue(!Sequence.isForInitialSequence(version));
		Assert.assertTrue(Sequence.isForLatestSequence(version));
		Assert.assertTrue(!Sequence.isForSpecificSequence(version));
	}

	@Test
	public void testForInitialSequence() {
		final long version = Sequence.forInitialSequence();
		Assert.assertNotSame(Sequence.LATEST.toLong(), version);
		Assert.assertEquals(Sequence.INITIAL.toLong(), version);
		Assert.assertTrue(Sequence.isForInitialSequence(version));
		Assert.assertTrue(!Sequence.isForLatestSequence(version));
		Assert.assertTrue(Sequence.isForSpecificSequence(version));
	}

	@Test
	public void testForSpecificSequence() {
		final long version = Sequence.forSpecificSequence(69);
		Assert.assertNotSame(Sequence.LATEST.toLong(), version);
		Assert.assertNotSame(Sequence.INITIAL.toLong(), version);
		Assert.assertTrue(!Sequence.isForInitialSequence(version));
		Assert.assertTrue(!Sequence.isForLatestSequence(version));
		Assert.assertTrue(Sequence.isForSpecificSequence(version));
	}

	@Test
	public void testNextSequence() {
		final long version = Sequence.forInitialSequence();
		long next = Sequence.nextSequence(version);

		Assert.assertTrue(version < next);
		Assert.assertEquals(version, next - 1);

		final long latest = Sequence.forLatestSequence();
		next = Sequence.nextSequence(latest);

		Assert.assertEquals(latest, next);
	}
}
