/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import org.intelligentsia.dowsers.domain.Entity;
import org.intelligentsia.dowsers.domain.Identifier;
import org.junit.Assert;
import org.junit.Test;


/**
 * EntityTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityTest {

	@Test
	public void testIdentitySetter() {
		final DummyEntity entity = new DummyEntity();

		try {
			entity.setIdentifier(null);
			Assert.fail("expected NullPointerException");
		} catch (final NullPointerException e) {
		}

		// next version identifier
		Identifier identifier = entity.getIdentifier().nextVersion();
		Assert.assertTrue(identifier.hasSameIdentity(entity.getIdentifier()));

		// set a next version
		entity.setIdentifier(identifier);

		identifier = Identifier.random();
		Assert.assertNotSame(identifier.getIdentity(), entity.getIdentifier().getIdentity());
		try {
			entity.setIdentifier(identifier);
			Assert.fail("expected IllegalArgumentException");
		} catch (final IllegalArgumentException e) {
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
			super(Identifier.random());
		}

	}
}
