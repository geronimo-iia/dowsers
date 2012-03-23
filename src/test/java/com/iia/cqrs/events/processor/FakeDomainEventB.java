/**
 * 
 */
package com.iia.cqrs.events.processor;

import com.iia.cqrs.domain.Entity;
import com.iia.cqrs.events.DomainEvent;

/**
 * FakeDomainEventB.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class FakeDomainEventB extends DomainEvent {

	/**
	 * Build a new instance of FakeDomainEventB.
	 * 
	 * @param entity
	 */
	public FakeDomainEventB(Entity entity) {
		super(entity);
	}

}
