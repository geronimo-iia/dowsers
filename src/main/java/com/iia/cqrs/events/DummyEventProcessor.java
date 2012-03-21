/**
 * 
 */
package com.iia.cqrs.events;

import com.iia.cqrs.Entity;

/**
 * DummyEventProcessor.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class DummyEventProcessor implements EventProcessor {

	/**
	 * @see com.iia.cqrs.events.EventProcessor#apply(com.iia.cqrs.Entity,
	 *      com.iia.cqrs.events.DomainEvent)
	 */
	@Override
	public <T extends DomainEvent> void apply(Entity source, T domainEvent) {
	}

	/**
	 * @see com.iia.cqrs.events.EventProcessor#register(java.lang.Class)
	 */
	@Override
	public <T extends Entity> void register(Class<T> entityType) {
	}

}
