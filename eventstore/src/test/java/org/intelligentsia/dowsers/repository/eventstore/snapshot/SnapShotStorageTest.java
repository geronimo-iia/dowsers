/**
 * 
 */
package org.intelligentsia.dowsers.repository.eventstore.snapshot;

import junit.framework.Assert;

import org.intelligentsia.dowsers.core.IdentifierFactoryProvider;
import org.intelligentsia.dowsers.repository.eventsource.snapshot.SnapShot;

import org.junit.Test;

/**
 * SnapShotStorageTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class SnapShotStorageTest {

	@Test
	public void testDefaultDayTimeOfSnapshot() {
		final SnapShot snapShot = new SnapShot(IdentifierFactoryProvider.generateNewIdentifier(), 1, new DummyMemento());
		Assert.assertNotNull(snapShot.getTimestamp());
	}
}
