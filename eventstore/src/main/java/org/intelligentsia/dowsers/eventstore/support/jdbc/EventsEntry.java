/**
 * 
 */
package org.intelligentsia.dowsers.eventstore.support.jdbc;

import hirondelle.date4j.DateTime;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EventsEntry {
	private final String identity;
	private final String timeStamp;
	private final long version;
	private final byte[] data;

	/**
	 * Build a new instance of EventsEntry.
	 * 
	 * @param identity
	 * @param timeStamp
	 * @param version
	 * @param data
	 */
	public EventsEntry(String identity, String timeStamp, long version, byte[] data) {
		super();
		this.identity = identity;
		this.timeStamp = timeStamp;
		this.version = version;
		this.data = data;
	}

	/**
	 * @return the identity
	 */
	public String getIdentity() {
		return identity;
	}

	/**
	 * @return the timeStamp
	 */
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @return the version
	 */
	public long getVersion() {
		return version;
	}

	/**
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

}
