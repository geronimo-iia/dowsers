/**
 * 
 */
package org.intelligentsia.dowsers.eventstore;

import java.util.UUID;

import org.intelligentsia.dowsers.domain.DomainEvent;
import org.intelligentsia.dowsers.domain.DomainEventProvider;

/**
 * “Captures all changes to an application state as a sequence of events.”
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface DomainEventStorage {

	Iterable<DomainEvent> GetAllEvents(UUID eventProviderId);

	void Save(DomainEventProvider eventProvider);

	Iterable<DomainEvent> GetEventsSinceLastSnapShot(UUID eventProviderId);

	int GetEventCountSinceLastSnapShot(UUID eventProviderId);

}
