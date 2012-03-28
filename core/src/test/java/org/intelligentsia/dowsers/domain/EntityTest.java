/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import org.intelligentsia.dowsers.domain.Entity;
import org.intelligentsia.dowsers.domain.Identifier;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * EntityTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityTest {

	@Test
	public void testNextVersion() {
		final DummyEntity entity = new DummyEntity();
		Identifier identifier = entity.getIdentifier();
		// increment version
		entity.nextVersion();
		Identifier nextVersion = entity.getIdentifier();
		// check
		assertNotSame(identifier, nextVersion);
		assertTrue(identifier.hasSameIdentity(nextVersion));
		assertTrue(identifier.compareTo(nextVersion) < 0);
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
			super(Identifier.random());
		}

	}
}
