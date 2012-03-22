/**
 * 
 */
package com.iia.cqrs;

import java.util.TimeZone;

import static org.junit.Assert.*;

import hirondelle.date4j.DateTime;

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
		DateTime time = DateTime.now(TimeZone.getTimeZone("GMT"));
		String stamp = time.format("YYYY-MM-DD hh:mm:ss.fffffffff");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		DateTime time2 = new DateTime(stamp);
		assertEquals(time, time2);
	}

	@Test
	public void testDayTimeGMT() {
		DateTime gmt = DateTime.now(TimeZone.getTimeZone("GMT"));
		DateTime current = DateTime.now(TimeZone.getDefault());

		assertNotSame(gmt.format("YYYY-MM-DD hh:mm:ss.f"), current.format("YYYY-MM-DD hh:mm:ss.f"));

		current = current.changeTimeZone(TimeZone.getDefault(), TimeZone.getTimeZone("GMT"));
		assertEquals(gmt.format("YYYY-MM-DD hh:mm:ss.f"), current.format("YYYY-MM-DD hh:mm:ss.f"));
	}
 
}
