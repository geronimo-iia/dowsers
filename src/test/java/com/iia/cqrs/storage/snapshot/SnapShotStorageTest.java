/**
 * 
 */
package com.iia.cqrs.storage.snapshot;

import hirondelle.date4j.DateTime;

import java.util.TimeZone;

import org.junit.Test;

import junit.framework.Assert;

import com.iia.cqrs.Identifier;

/**
 * SnapShotStorageTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class SnapShotStorageTest {

	@Test
	public void testDefaultDayTimeOfSnapshot() {
		DateTime dateTime = DateTime.now(TimeZone.getTimeZone("GMT"));
		SnapShot snapShot = new SnapShot(Identifier.random(), new DummyMemento());
		Assert.assertNotNull(snapShot.getTimestamp());
		Assert.assertEquals(dateTime.format("YYYY-MM-DD hh:mm:ss"), snapShot.getTimestamp().format("YYYY-MM-DD hh:mm:ss"));
	}
}
