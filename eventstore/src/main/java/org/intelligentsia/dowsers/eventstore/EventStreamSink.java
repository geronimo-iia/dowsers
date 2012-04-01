package org.intelligentsia.dowsers.eventstore;

import java.util.Collection;

/**
 * EventStreamSink.
 * 
 * 
 * <EventType> base type of event.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface EventStreamSink<EventType> {

	public void setVersion(long version);

	public void setEvents(Collection<EventType> events);
	
	public void setClassName(String classname);
}
