/**
 * 
 */
package org.intelligentsia.dowsers.eventstore.jdbc;

import hirondelle.date4j.DateTime;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DomainEventProviderEntry {

	private String id;
	private long version;
	private String typeName;
	private DateTime timeStamp;
	private int eventCount;
}
