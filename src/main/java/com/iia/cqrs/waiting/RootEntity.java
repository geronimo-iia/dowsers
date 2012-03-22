/**
 * 
 */
package com.iia.cqrs.waiting;

import com.iia.cqrs.Entity;
import com.iia.cqrs.events.processor.EventProcessor;

/**
 * RootEntity. Its a special entity related to a context.
 * 
 * A root entity group entities and value objects. All access to the objects
 * inside go through this root entity. That's important point.
 * 
 * A root entity create a new Aggregate which act as a context.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class RootEntity extends Entity {

	/**
	 * Build a new instance of RootEntity.
	 * 
	 * @param domainEventBusInvoker
	 *            domain event bus invoker instance
	 * @throws NullPointerException
	 *             if domainEventBusInvoker is null
	 */
	public RootEntity() throws NullPointerException {

	}

	/**
	 * Build a new instance of RootEntity.
	 * 
	 * @param aggregate
	 * @param random
	 */
	public RootEntity(EventProcessor aggregate) {
		super(aggregate, null);
	}

}
