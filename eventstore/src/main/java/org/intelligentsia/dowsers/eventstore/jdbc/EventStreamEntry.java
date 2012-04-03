/**
 * 
 */
package org.intelligentsia.dowsers.eventstore.jdbc;

import hirondelle.date4j.DateTime;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EventStreamEntry {
	private final String identity;
	private final String name;
	private DateTime timeStamp;
	private long currentVersion;

	/**
	 * Build a new instance of <code>EventStreamEntry</code>
	 * 
	 * @param identity
	 * @param name
	 * @param timeStamp
	 * @param currentVersion
	 */
	public EventStreamEntry(String identity, String name, DateTime timeStamp, long currentVersion) {
		super();
		this.identity = identity;
		this.name = name;
		this.timeStamp = timeStamp;
		this.currentVersion = currentVersion;
	}

}
