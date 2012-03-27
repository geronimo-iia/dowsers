/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import java.util.UUID;

import org.intelligentsia.dowsers.annotation.Note;
import org.intelligentsia.dowsers.annotation.TODO;

import com.google.common.base.Preconditions;

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
	 * Event ordinal value.
	 */
	private long ordinal;
	/**
	 * Entity identity instance.
	 */
	private final UUID entityIdentity;

	@Note("This member could simplify event storage, and provide more control about event provider.")
	@TODO("status on this ")
	protected final long version = 0l;
	
	/**
	 * Build a new instance of DomainEvent.
	 * 
	 * @param entity
	 *            entity which event carries on
	 * @throws NullPointerException
	 *             if entity is null
	 */
	public DomainEvent(final Entity entity) throws NullPointerException {
		this(UUID.randomUUID(), Preconditions.checkNotNull(entity).getIdentifier().getIdentity());
	}

	/**
	 * Build a new instance of DomainEvent.
	 * 
	 * @param eventIdentity
	 *            identity of this event
	 * @param entityIdentity
	 *            identity of entity which event carries on
	 */
	protected DomainEvent(final UUID eventIdentity, final UUID entityIdentity) {
		super();
		this.eventIdentity = eventIdentity;
		this.entityIdentity = entityIdentity;
		ordinal = 0;
	}

	/**
	 * @return the entityIdentity
	 */
	public UUID getEntityIdentity() {
		return entityIdentity;
	}

	/**
	 * @return the eventIdentity
	 */
	public UUID getEventIdentity() {
		return eventIdentity;
	}

	public long getOrdinal() {
		return ordinal;
	}

	/**
	 * Set ordinal value.
	 * 
	 * @param ordinal
	 */
	void setOrdinal(final long ordinal) {
		this.ordinal = ordinal;
	}

}
