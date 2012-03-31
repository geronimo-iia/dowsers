/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import org.intelligentsia.dowsers.event.DomainEvent;
import org.intelligentsia.dowsers.event.DomainEventInvoker;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

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
	 * Entity identity.
	 */
	private final String identity;

	/**
	 * DomainEventInvoker instance.
	 */
	private DomainEventInvoker domainEventInvoker;

	/**
	 * Build a new instance of Entity. All change in this entity will be bounded
	 * by root entity of the specific aggregate.
	 * 
	 * @param identity
	 * 
	 * @throws NullPointerException
	 *             if identifier is null
	 */
	public Entity(final String identity) throws NullPointerException {
		super();
		this.identity = Preconditions.checkNotNull(identity);
	}

	/**
	 * Apply specified domain event.
	 * 
	 * @param domainEvent
	 *            domain Event apply
	 * @throws IllegalStateException
	 *             if no event processor instance is set on this entity.
	 */
	protected <T extends DomainEvent> void apply(final T domainEvent) throws IllegalStateException {
		// check if domain event bus invoker is set
		if (domainEventInvoker == null) {
			new IllegalStateException("No domain event invoker instance");
		}
		domainEventInvoker.apply(domainEvent);
	}

	/**
	 * @return identity value
	 */
	public final String getIdentity() {
		return identity;
	}

	/**
	 * hashCode based on identity.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		return Objects.hashCode(identity);
	}

	/**
	 * Entities compare by identity, not by attributes.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Entity other = (Entity) obj;
		return Objects.equal(other.getIdentity(), identity);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("identity", identity).toString();
	}

	/**
	 * Set DomainEventInvoker instance for this entity. This methode has a
	 * package visibility in order to deal with aggregate interface.
	 * 
	 * @param domainEventInvoker
	 *            the domainEventInvoker to set
	 * @throws NullPointerException
	 *             if domainEventInvoker is null
	 */
	final void setDomainEventInvoker(final DomainEventInvoker domainEventInvoker) throws NullPointerException {
		this.domainEventInvoker = Preconditions.checkNotNull(domainEventInvoker);
	}
}
