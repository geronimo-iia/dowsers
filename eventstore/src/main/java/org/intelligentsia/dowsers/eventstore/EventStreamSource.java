package org.intelligentsia.dowsers.eventstore;

import java.util.Collection;

/**
 * EventStreamSource.
 * 
 * 
 * <EventType> base type of event.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface EventStreamSource<EventType> {

	public long getVersion();

	public Collection<EventType> getEvents();
	
	public String  getClassName();
}
