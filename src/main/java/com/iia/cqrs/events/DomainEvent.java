/**
 * 
 */
package com.iia.cqrs.events;

import java.util.UUID;

import com.iia.cqrs.Identifier;
import com.iia.cqrs.annotation.TODO;

/**
 * DomainEvent are internal events with the purpose to capturing intent.
 * 
 * 
 * We separate the domain behavior from the state changes resulting from this
 * domain behavior including the triggering of external behavior.<br />
 * 
 * 
 * Instead of writing these state changes directly to the internal variables you
 * create an event and fire it internally.<br />
 * This event as well as the method name of the behavior should be descriptive
 * in the Ubiquitous Language of the domain.<br />
 * Then the event will be handled inside the domain Aggregate Root which will
 * set the internal state to the correct values.<br />
 * Remember that the event handler should not be doing any logic other then
 * setting the state, the logic should be in the domain method.
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public abstract class DomainEvent {

	/**
	 * Event identity instance.
	 */
	private final UUID eventIdentity;

	/**
	 * Entity identifier instance.
	 */
	@TODO("may we expose this member as two attribut ")
	private Identifier entityIdentifier;

	/**
	 * Build a new instance of DomainEvent.
	 * 
	 * @param entityIdentifier
	 *            identifier of entity which event carries on
	 */
	public DomainEvent(Identifier entityIdentifier) {
		this(UUID.randomUUID(), entityIdentifier);
	}

	/**
	 * Build a new instance of DomainEvent.
	 * 
	 * @param eventIdentity
	 *            identity of this event
	 * @param entityIdentifier
	 *            identifier of entity which event carries on
	 */
	public DomainEvent(UUID eventIdentity, Identifier entityIdentifier) {
		super();
		this.eventIdentity = eventIdentity;
		this.entityIdentifier = entityIdentifier;
	}

	/**
	 * @return the entityIdentifier
	 */
	public Identifier getEntityIdentifier() {
		return entityIdentifier;
	}

	/**
	 * @param entityIdentifier
	 *            the entityIdentifier to set
	 */
	public void setEntityIdentifier(Identifier entityIdentifier) {
		this.entityIdentifier = entityIdentifier;
	}

	/**
	 * @return the eventIdentity
	 */
	public UUID getEventIdentity() {
		return eventIdentity;
	}

}
