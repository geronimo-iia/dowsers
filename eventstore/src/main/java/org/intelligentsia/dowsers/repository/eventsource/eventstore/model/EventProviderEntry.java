/**
 * 
 */
package org.intelligentsia.dowsers.repository.eventsource.eventstore.model;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EventProviderEntry {

	private String providerIdentity;

	private String className;
	
	private long version = -1l;
	
	private long eventCountSinceLastSnapshot=0l;
}
