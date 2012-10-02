/**
 *        Licensed to the Apache Software Foundation (ASF) under one
 *        or more contributor license agreements.  See the NOTICE file
 *        distributed with this work for additional information
 *        regarding copyright ownership.  The ASF licenses this file
 *        to you under the Apache License, Version 2.0 (the
 *        "License"); you may not use this file except in compliance
 *        with the License.  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing,
 *        software distributed under the License is distributed on an
 *        "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *        KIND, either express or implied.  See the License for the
 *        specific language governing permissions and limitations
 *        under the License.
 *
 */
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
