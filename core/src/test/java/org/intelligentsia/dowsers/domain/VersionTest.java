/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * VersionTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class VersionTest {

	private final Version a = Version.forSpecificVersion(3);

	@Test
	public void shouldStoreVersion() {
		assertEquals(3, a.toLong());
	}

	@Test
	public void testForLatestVersion() {
		final Version version = Version.forLatestVersion();
		assertEquals(Version.LATEST_VERSION, version.toLong());
		assertNotSame(Version.INITIAL_VERSION, version.toLong());
		assertTrue(!version.isForInitialVersion());
		assertTrue(version.isForLatestVersion());
		assertTrue(!version.isForSpecificVersion());
	}

	@Test
	public void testForInitialVersion() {
		final Version version = Version.forInitialVersion();
		assertNotSame(Version.LATEST_VERSION, version.toLong());
		assertEquals(Version.INITIAL_VERSION, version.toLong());
		assertTrue(version.isForInitialVersion());
		assertTrue(!version.isForLatestVersion());
		assertTrue(version.isForSpecificVersion());
	}

	@Test
	public void testForSpecificVersion() {
		final Version version = Version.forSpecificVersion(69);
		assertNotSame(Version.LATEST_VERSION, version.toLong());
		assertNotSame(Version.INITIAL_VERSION, version.toLong());
		assertTrue(!version.isForInitialVersion());
		assertTrue(!version.isForLatestVersion());
		assertTrue(version.isForSpecificVersion());
	}

	@Test
	public void testToString() {
		assertEquals("3", a.toString());
	}

	@Test
	public void testParseVersionFromLong() {
		// for specific
		Version version = Version.parseVersion(Version.forSpecificVersion(1L).toLong());
		assertNotNull(version);
		assertEquals(1L, version.toLong());
		// invalid
		try {
			Version.parseVersion(-1L);
			fail("expected IllegalArgumentException");
		} catch (final IllegalArgumentException e) {
		}
		// initial
		assertNotNull(Version.parseVersion(Version.forInitialVersion().toLong()));
		// latest
		assertNotNull(Version.parseVersion(Version.forLatestVersion().toLong()));
	}

	@Test
	public void testCompareVersion() {
		final Version initial = Version.forInitialVersion();
		final Version latest = Version.forLatestVersion();
		final Version otherLatest = Version.forLatestVersion();
		final Version specific = Version.forSpecificVersion(69);

		assertTrue(initial.compareTo(initial) == 0);
		assertTrue(initial.compareTo(specific) < 0);
		assertTrue(initial.compareTo(latest) < 0);

		assertTrue(specific.compareTo(initial) > 0);
		assertTrue(specific.compareTo(specific) == 0);
		assertTrue(specific.compareTo(latest) < 0);

		assertTrue(latest.compareTo(initial) > 0);
		assertTrue(latest.compareTo(specific) > 0);
		assertTrue(latest.compareTo(latest) == 0);

		assertTrue(latest.compareTo(otherLatest) == 0);
	}

	@Test
	public void testNextVersion() {
		final Version version = Version.forInitialVersion();
		Version next = version.nextVersion();
 
		assertTrue(version.toLong() < next.toLong());
		assertEquals(version.toLong(), next.toLong() - 1);

		final Version latest = Version.forLatestVersion();
		next = latest.nextVersion();
 
		assertEquals(latest.toLong(), next.toLong());
	}
}
