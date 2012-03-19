/**
 * 
 */
package com.iia.cqrs;

import java.util.UUID;

import static org.junit.Assert.*;
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
	
	private final Identifier a = Identifier.forSpecificVersion(FOO, 3);

	@Test
	public void shouldStoreIdAndVersion() {
		assertEquals(FOO, a.getIdentity());
		assertEquals(3, a.getVersion());
	}

	@Test
	public void testEqualsIgnoreVersion() {
		assertTrue(a.hasSameIdentity(Identifier.forSpecificVersion(IdentifierTest.FOO, 1)));
		assertFalse(a.hasSameIdentity(null));
		assertFalse(a.hasSameIdentity(Identifier.forSpecificVersion(IdentifierTest.BAR, 3)));
	}

	@Test
	public void testCompatibility() {
		assertTrue(Identifier.forLatestVersion(IdentifierTest.FOO).isCompatible(a));
		assertFalse(Identifier.forLatestVersion(IdentifierTest.BAR).isCompatible(a));
		assertTrue(a.isCompatible(Identifier.forSpecificVersion(IdentifierTest.FOO, 3)));
		assertFalse(a.isCompatible(Identifier.forSpecificVersion(IdentifierTest.FOO, 2)));
		assertFalse(a.isCompatible(Identifier.forSpecificVersion(IdentifierTest.BAR, 3)));
	}

	@Test
	public void testToString() {
		assertEquals(IdentifierTest.FOO.toString() + "#3", a.toString());
		assertEquals(IdentifierTest.FOO.toString(), Identifier.forLatestVersion(IdentifierTest.FOO).toString());
	}

	@Test
	public void testParseIdentifier() {
		
		Identifier identifier = Identifier.parseIdentifier(ID);
		assertNotNull(identifier);
		assertTrue(identifier.isForLatestVersion());
		assertEquals(ID, identifier.getIdentity().toString());
		assertEquals(Identifier.LATEST_VERSION, identifier.getVersion());
		
		Identifier identifierWithVersion = Identifier.parseIdentifier(ID + "#6");
		assertNotNull(identifierWithVersion);
		assertEquals(ID, identifierWithVersion.getIdentity().toString());
		assertEquals(6, identifierWithVersion.getVersion());
		assertTrue(identifierWithVersion.isForSpecificVersion());
		
		try {
			Identifier.parseIdentifier("not an identifier");
			fail("expected IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
	}
	
	@Test
	public void testForLatestVersion() {
		Identifier identifier = Identifier.forLatestVersion(IdentifierTest.FOO);
		assertEquals(Identifier.LATEST_VERSION, identifier.getVersion());
		assertNotSame(Identifier.INITIAL_VERSION, identifier.getVersion());
		assertTrue(!identifier.isForInitialVersion());
		assertTrue(identifier.isForLatestVersion());
		assertTrue(!identifier.isForSpecificVersion());
	}

	@Test
	public void testForInitialVersion() {
		Identifier identifier = Identifier.forInitialVersion(IdentifierTest.FOO);
		assertNotSame(Identifier.LATEST_VERSION, identifier.getVersion());
		assertEquals(Identifier.INITIAL_VERSION, identifier.getVersion());
		assertTrue(identifier.isForInitialVersion());
		assertTrue(!identifier.isForLatestVersion());
		assertTrue(identifier.isForSpecificVersion());
	}

	@Test
	public void testForSpecificVersion() {
		Identifier identifier = Identifier.forSpecificVersion(IdentifierTest.FOO, 69);
		assertNotSame(Identifier.LATEST_VERSION, identifier.getVersion());
		assertNotSame(Identifier.INITIAL_VERSION, identifier.getVersion());
		assertTrue(!identifier.isForInitialVersion());
		assertTrue(!identifier.isForLatestVersion());
		assertTrue(identifier.isForSpecificVersion());
	}

}
