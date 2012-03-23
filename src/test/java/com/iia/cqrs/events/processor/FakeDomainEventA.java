/**
 * 
 */
package com.iia.cqrs.events.processor;

import com.iia.cqrs.domain.Entity;
import com.iia.cqrs.events.DomainEvent;

/**
 * FakeDomainEventA.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class FakeDomainEventA extends DomainEvent {

	/**
	 * Build a new instance of FakeDomainEventA.
	 * 
	 * @param entity
	 */
	public FakeDomainEventA(Entity entity) {
		super(entity);
	}

}
