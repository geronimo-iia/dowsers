/**
 * 
 */
package org.intelligentsia.dowsers.eventstore.jdbc;

import hirondelle.date4j.DateTime;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EventsEntry {
	private String identity;
	private DateTime timeStamp;
	private long version;
	private byte[] data;
}
