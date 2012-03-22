/**
 * 
 */
package com.iia.cqrs.events.processor;

import java.util.UUID;

import com.iia.cqrs.Identifier;
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
	 * @param entityIdentifier
	 */
	public FakeDomainEventA(final Identifier entityIdentifier) {
		super(entityIdentifier);
	}

	/**
	 * Build a new instance of FakeDomainEventA.
	 * 
	 * @param eventIdentity
	 * @param entityIdentifier
	 */
	public FakeDomainEventA(final UUID eventIdentity, final Identifier entityIdentifier) {
		super(eventIdentity, entityIdentifier);
	}

}
