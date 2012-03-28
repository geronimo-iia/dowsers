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

	Iterable<DomainEvent> findAllDomainEvents(UUID identity);

	Iterable<DomainEvent> findDomainEventsSinceLastSnapShot(UUID identity);
	
	void store(DomainEventProvider eventProvider);

	int getDomainEventCountSinceLastSnapShot(UUID identity);

}
