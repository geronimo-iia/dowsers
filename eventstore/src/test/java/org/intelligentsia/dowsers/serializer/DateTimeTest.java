/**
 * 
 */
package org.intelligentsia.dowsers.serializer;

import hirondelle.date4j.DateTime;

import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;

/**
 * DateTimeTest. simple library wit nano second precision.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class DateTimeTest {

	@Test
	public void testDayTimeFormatAndParse() {
		final DateTime time = DateTime.now(TimeZone.getTimeZone("GMT"));
		final String stamp = time.format("YYYY-MM-DD hh:mm:ss.fffffffff");
		try {
			Thread.sleep(1000);
		} catch (final InterruptedException e) {
		}
		final DateTime time2 = new DateTime(stamp);
		Assert.assertEquals(time, time2);
	}

	@Test
	public void testDayTimeGMT() {
		final DateTime gmt = DateTime.now(TimeZone.getTimeZone("GMT"));
		DateTime current = DateTime.now(TimeZone.getDefault());

		Assert.assertNotSame(gmt.format("YYYY-MM-DD hh:mm:ss.f"), current.format("YYYY-MM-DD hh:mm:ss.f"));

		current = current.changeTimeZone(TimeZone.getDefault(), TimeZone.getTimeZone("GMT"));
		Assert.assertEquals(gmt.format("YYYY-MM-DD hh:mm:ss.f"), current.format("YYYY-MM-DD hh:mm:ss.f"));
	}

}
