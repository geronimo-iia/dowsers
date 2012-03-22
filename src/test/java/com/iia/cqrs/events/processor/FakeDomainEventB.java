/**
 * 
 */
package com.iia.cqrs.events.processor;

import java.util.UUID;

import com.iia.cqrs.Identifier;
import com.iia.cqrs.events.DomainEvent;

/**
 * FakeDomainEventB. 
 *
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class FakeDomainEventB extends DomainEvent {

	/**
	 * Build a new instance of FakeDomainEventB.
	 * @param entityIdentifier
	 */
	public FakeDomainEventB(Identifier entityIdentifier) {
		super(entityIdentifier);
	}

	/**
	 * Build a new instance of FakeDomainEventB.
	 * @param eventIdentity
	 * @param entityIdentifier
	 */
	public FakeDomainEventB(UUID eventIdentity, Identifier entityIdentifier) {
		super(eventIdentity, entityIdentifier);
	}

}
