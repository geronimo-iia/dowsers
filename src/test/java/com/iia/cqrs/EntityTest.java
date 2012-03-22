/**
 * 
 */
package com.iia.cqrs;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * EntityTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityTest {

	@Test
	public void testIdentitySetter() {
		DummyEntity entity = new DummyEntity();

		try {
			entity.setIdentifier(null);
			fail("expected NullPointerException");
		} catch (NullPointerException e) {
		}

		// next version identifier
		Identifier identifier = entity.getIdentifier().nextVersion();
		assertTrue(identifier.hasSameIdentity(entity.getIdentifier()));

		// set a next version
		entity.setIdentifier(identifier);

		identifier = Identifier.random();
		assertNotSame(identifier.getIdentity(), entity.getIdentifier().getIdentity());
		try {
			entity.setIdentifier(identifier);
			fail("expected IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}

	}

	/**
	 * 
	 * DummyEntity.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 * 
	 */
	private class DummyEntity extends Entity {

		/**
		 * Build a new instance of DummyEntity.
		 */
		public DummyEntity() {
			super();
		}

	}
}
