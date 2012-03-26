/**
 * 
 */
package org.intelligentsia.dowsers.events;

import java.util.UUID;

/**
 * “Captures all changes to an application state as a sequence of events.”
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface DomainEventStorage {

	Iterable<DomainEvent> GetAllEvents(UUID eventProviderId);

	void Save(EventProvider eventProvider);

	Iterable<DomainEvent> GetEventsSinceLastSnapShot(UUID eventProviderId);

	int GetEventCountSinceLastSnapShot(UUID eventProviderId);

}
