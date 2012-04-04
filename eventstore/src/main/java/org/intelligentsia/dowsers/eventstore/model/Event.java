/**
 * 
 */
package org.intelligentsia.dowsers.eventstore.model;

import hirondelle.date4j.DateTime;

/**
 *
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Event {
	/**
	 * Provider identity.
	 */
	private String providerIdentity;
	/**
	 * Version of this snapshot.
	 */
	private long version;
	/**
	 * Time stamp of this snapshot.
	 */
	private DateTime timestamp;
	/**
	 * MemoryEvents data.
	 */
	private byte[] events;
}
