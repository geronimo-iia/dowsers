/**
 * 
 */
package com.iia.cqrs;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.iia.cqrs.annotation.Note;
import com.iia.cqrs.annotation.TODO;
import com.iia.cqrs.events.DomainEvent;
import com.iia.cqrs.events.processor.EventProcessor;

/**
 * Entity
 * 
 * An entity:
 * <ul>
 * <li>Something with a unique identity</li>
 * <li>Identity does not change when any of its attributes change</li>
 * <li>Examples: Customer, Order, ...</li>
 * </ul>
 * 
 * Entities compare by identity, not by attributes. With an event sourcing
 * pattern: An action against entity, should made some validation, and generate
 * event (which be stored in aggregate context). The receiver entity should
 * handle (implements logic of) those events.
 * 
 * 
 * At this opposite, a value object, as described in the DDD book have:
 * <ul>
 * <li>No conceptual identity,</li>
 * <li>Describe characteristic of a thing,</li>
 * <li>Usually immutable,</li>
 * <li>Examples: Address, Money, ...</li>
 * </ul>
 * Not for later: Value object should:
 * <ul>
 * <li>have all attributes with final keyword</li>
 * <li>have a constructor with attributes</li>
 * <li>implements 'equal' methods. Value objects compare by the values of their
 * attributes, they don't have an identity.</li>
 * <li>implements 'hashCode' methods</li>
 * <li>implements 'toString' methods</li>
 * </ul>
 * 
 * 
 * DDD pattern:
 * 
 * A collection of objects that are bound together by a root entity, otherwise
 * known as an aggregate root. The aggregate root guarantees the consistency of
 * changes being made within the aggregate by forbidding external objects from
 * holding references to its members.
 * 
 * Other reference:
 * 
 * @see DDD: {@link http://en.wikipedia.org/wiki/Domain-driven_design}
 * @see Nacked Object: {@link http://en.wikipedia.org/wiki/Naked_objects}
 * @see CQRS: {@link http://en.wikipedia.org/wiki/Command-query_separation}
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public abstract class Entity {

	/**
	 * Entity identifier. 
	 */
	private Identifier identifier;

	/**
	 * EventProcessor instance.
	 */
	private EventProcessor eventProcessor;

	public Entity() {
		this(null, Identifier.random());
	}

	public Entity(final Identifier identifier) {
		this(null, identifier);
	}

	/**
	 * Build a new instance of <code>Entity</code>
	 */
	public Entity(final EventProcessor domainEventBusInvoker) {
		this(domainEventBusInvoker, Identifier.random());
	}

	/**
	 * Build a new instance of <code>Entity</code>
	 * 
	 * @param identifier
	 *            specified entity
	 * @throws NullPointerException
	 *             if identifier is null
	 */
	protected Entity(final EventProcessor eventProcessor, final Identifier identifier) throws NullPointerException {
		super();
		this.identifier = Preconditions.checkNotNull(identifier);
		this.eventProcessor = eventProcessor;
	}

	/**
	 * Apply specified domain event.
	 * 
	 * @param domainEvent
	 *            domain Event apply
	 * @throws IllegalStateException
	 *             if no event processor instance is set on this entity.
	 */
	protected <T extends DomainEvent> void apply(T domainEvent) throws IllegalStateException {
		// check if domain event bus invoker is set
		if (eventProcessor == null) {
			new IllegalStateException("No event processor instance");
		}
		eventProcessor.apply(this, domainEvent);
	}

	/**
	 * @return identifier value
	 */
	public final Identifier getIdentifier() {
		return this.identifier;
	}

	/**
	 * hashCode based on identity.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		return Objects.hashCode(getIdentifier().getIdentity());
	}

	/**
	 * Entities compare by identity, not by attributes.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		return Objects.equal(other.getIdentifier().getIdentity(), getIdentifier().getIdentity());
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("identifier", identifier).toString();
	}
	
	
	/**
	 * @param identifier the identifier to set
	 * @throws IllegalArgumentException if identity of this entity isn't the same.
	 * @throws IllegalArgumentException if identity is null
	 */
	@Note("Package visibility")
	@TODO("May we can find a better way to deal with that ?")
	void setIdentifier(Identifier identifier) throws IllegalArgumentException, NullPointerException{
		Preconditions.checkArgument(Preconditions.checkNotNull(identifier).hasSameIdentity(getIdentifier())); 
		this.identifier = identifier;
	}
}
