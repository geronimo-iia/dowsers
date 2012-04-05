/**
 * 
 */
package org.intelligentsia.dowsers.eventstore.jdbc;

import net.sf.persist.annotations.Table;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
@Table(name = "EventStreamEntry")
public class EventStreamEntry {
	private final String identity;
	private final String name;
	private String timeStamp;
	private long currentVersion;

	/**
	 * Build a new instance of <code>EventStreamEntry</code>
	 * 
	 * @param identity
	 * @param name
	 * @param timeStamp
	 * @param currentVersion
	 */
	public EventStreamEntry(String identity, String name, String timeStamp, long currentVersion) {
		super();
		this.identity = identity;
		this.name = name;
		this.timeStamp = timeStamp;
		this.currentVersion = currentVersion;
	}

	/**
	 * @return the timeStamp
	 */
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp
	 *            the timeStamp to set
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * @return the currentVersion
	 */
	public long getCurrentVersion() {
		return currentVersion;
	}

	/**
	 * @param currentVersion
	 *            the currentVersion to set
	 */
	public void setCurrentVersion(long currentVersion) {
		this.currentVersion = currentVersion;
	}

	/**
	 * @return the identity
	 */
	public String getIdentity() {
		return identity;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
