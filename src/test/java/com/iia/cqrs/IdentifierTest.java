/**
 * 
 */
package com.iia.cqrs;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

/**
 * IdentifierTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class IdentifierTest {
	private final static UUID FOO = UUID.randomUUID();
	private final static UUID BAR = UUID.randomUUID();
	private final static String ID = "ae7e2185-46a3-4d5c-b939-3c441aa5e085";

	private final Identifier a = Identifier.forSpecificVersion(IdentifierTest.FOO, 3);

	@Test
	public void shouldStoreIdAndVersion() {
		Assert.assertEquals(IdentifierTest.FOO, a.getIdentity());
		Assert.assertEquals(3, a.getVersion());
	}

	@Test
	public void testEqualsIgnoreVersion() {
		Assert.assertTrue(a.hasSameIdentity(Identifier.forSpecificVersion(IdentifierTest.FOO, 1)));
		Assert.assertFalse(a.hasSameIdentity(null));
		Assert.assertFalse(a.hasSameIdentity(Identifier.forSpecificVersion(IdentifierTest.BAR, 3)));
	}

	@Test
	public void testCompatibility() {
		Assert.assertTrue(Identifier.forLatestVersion(IdentifierTest.FOO).isCompatible(a));
		Assert.assertFalse(Identifier.forLatestVersion(IdentifierTest.BAR).isCompatible(a));
		Assert.assertTrue(a.isCompatible(Identifier.forSpecificVersion(IdentifierTest.FOO, 3)));
		Assert.assertFalse(a.isCompatible(Identifier.forSpecificVersion(IdentifierTest.FOO, 2)));
		Assert.assertFalse(a.isCompatible(Identifier.forSpecificVersion(IdentifierTest.BAR, 3)));
	}

	@Test
	public void testToString() {
		Assert.assertEquals(IdentifierTest.FOO.toString() + "#3", a.toString());
		Assert.assertEquals(IdentifierTest.FOO.toString(), Identifier.forLatestVersion(IdentifierTest.FOO).toString());
	}

	@Test
	public void testParseIdentifier() {

		final Identifier identifier = Identifier.parseIdentifier(IdentifierTest.ID);
		Assert.assertNotNull(identifier);
		Assert.assertTrue(identifier.isForLatestVersion());
		Assert.assertEquals(IdentifierTest.ID, identifier.getIdentity().toString());
		Assert.assertEquals(Identifier.LATEST_VERSION, identifier.getVersion());

		final Identifier identifierWithVersion = Identifier.parseIdentifier(IdentifierTest.ID + "#6");
		Assert.assertNotNull(identifierWithVersion);
		Assert.assertEquals(IdentifierTest.ID, identifierWithVersion.getIdentity().toString());
		Assert.assertEquals(6, identifierWithVersion.getVersion());
		Assert.assertTrue(identifierWithVersion.isForSpecificVersion());

		try {
			Identifier.parseIdentifier("not an identifier");
			Assert.fail("expected IllegalArgumentException");
		} catch (final IllegalArgumentException e) {
		}
	}

	@Test
	public void testForLatestVersion() {
		final Identifier identifier = Identifier.forLatestVersion(IdentifierTest.FOO);
		Assert.assertEquals(Identifier.LATEST_VERSION, identifier.getVersion());
		Assert.assertNotSame(Identifier.INITIAL_VERSION, identifier.getVersion());
		Assert.assertTrue(!identifier.isForInitialVersion());
		Assert.assertTrue(identifier.isForLatestVersion());
		Assert.assertTrue(!identifier.isForSpecificVersion());
	}

	@Test
	public void testForInitialVersion() {
		final Identifier identifier = Identifier.forInitialVersion(IdentifierTest.FOO);
		Assert.assertNotSame(Identifier.LATEST_VERSION, identifier.getVersion());
		Assert.assertEquals(Identifier.INITIAL_VERSION, identifier.getVersion());
		Assert.assertTrue(identifier.isForInitialVersion());
		Assert.assertTrue(!identifier.isForLatestVersion());
		Assert.assertTrue(identifier.isForSpecificVersion());
	}

	@Test
	public void testForSpecificVersion() {
		final Identifier identifier = Identifier.forSpecificVersion(IdentifierTest.FOO, 69);
		Assert.assertNotSame(Identifier.LATEST_VERSION, identifier.getVersion());
		Assert.assertNotSame(Identifier.INITIAL_VERSION, identifier.getVersion());
		Assert.assertTrue(!identifier.isForInitialVersion());
		Assert.assertTrue(!identifier.isForLatestVersion());
		Assert.assertTrue(identifier.isForSpecificVersion());
	}

	@Test
	public void testCompareVersion() {
		final Identifier initial = Identifier.forInitialVersion(IdentifierTest.FOO);
		final Identifier latest = Identifier.forLatestVersion(IdentifierTest.FOO);
		final Identifier otherLatest = Identifier.forLatestVersion(IdentifierTest.BAR);
		final Identifier specific = Identifier.forSpecificVersion(IdentifierTest.FOO, 69);

		Assert.assertTrue(initial.compareTo(initial) == 0);
		Assert.assertTrue(initial.compareTo(specific) < 0);
		Assert.assertTrue(initial.compareTo(latest) < 0);

		Assert.assertTrue(specific.compareTo(initial) > 0);
		Assert.assertTrue(specific.compareTo(specific) == 0);
		Assert.assertTrue(specific.compareTo(latest) < 0);

		Assert.assertTrue(latest.compareTo(initial) > 0);
		Assert.assertTrue(latest.compareTo(specific) > 0);
		Assert.assertTrue(latest.compareTo(latest) == 0);

		try {
			latest.compareTo(otherLatest);
			Assert.fail("Attended ClassCastException");
		} catch (final ClassCastException e) {
		}

	}

	@Test
	public void testNextVersion() {
		final Identifier initial = Identifier.forInitialVersion(IdentifierTest.FOO);
		Identifier next = initial.nextVersion();

		Assert.assertTrue(initial.hasSameIdentity(next));
		Assert.assertTrue(initial.getVersion() < next.getVersion());
		Assert.assertEquals(initial.getVersion(), next.getVersion() - 1);

		final Identifier latest = Identifier.forLatestVersion(IdentifierTest.FOO);
		next = latest.nextVersion();

		Assert.assertTrue(latest.hasSameIdentity(next));
		Assert.assertEquals(latest.getVersion(), next.getVersion());
	}
}
