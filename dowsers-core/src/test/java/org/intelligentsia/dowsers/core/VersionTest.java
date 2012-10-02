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
 * VersionTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class VersionTest {

	private final long a = Version.forSpecificVersion(3);

	@Test
	public void shouldStoreVersion() {
		Assert.assertEquals(3, a);
	}

	@Test
	public void testForLatestVersion() {
		final long version = Version.forLatestVersion();
		Assert.assertEquals(Version.LATEST.toLong(), version);
		Assert.assertNotSame(Version.INITIAL.toLong(), version);
		Assert.assertTrue(!Version.isForInitialVersion(version));
		Assert.assertTrue(Version.isForLatestVersion(version));
		Assert.assertTrue(!Version.isForSpecificVersion(version));
	}

	@Test
	public void testForInitialVersion() {
		final long version = Version.forInitialVersion();
		Assert.assertNotSame(Version.LATEST.toLong(), version);
		Assert.assertEquals(Version.INITIAL.toLong(), version);
		Assert.assertTrue(Version.isForInitialVersion(version));
		Assert.assertTrue(!Version.isForLatestVersion(version));
		Assert.assertTrue(Version.isForSpecificVersion(version));
	}

	@Test
	public void testForSpecificVersion() {
		final long version = Version.forSpecificVersion(69);
		Assert.assertNotSame(Version.LATEST.toLong(), version);
		Assert.assertNotSame(Version.INITIAL.toLong(), version);
		Assert.assertTrue(!Version.isForInitialVersion(version));
		Assert.assertTrue(!Version.isForLatestVersion(version));
		Assert.assertTrue(Version.isForSpecificVersion(version));
	}

	@Test
	public void testNextVersion() {
		final long version = Version.forInitialVersion();
		long next = Version.nextVersion(version);

		Assert.assertTrue(version < next);
		Assert.assertEquals(version, next - 1);

		final long latest = Version.forLatestVersion();
		next = Version.nextVersion(latest);

		Assert.assertEquals(latest, next);
	}
}