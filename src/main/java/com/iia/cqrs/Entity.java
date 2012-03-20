/**
 * 
 */
package com.iia.cqrs;

import com.google.common.base.Objects;

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
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public abstract class Entity {

	/**
	 * Entity identifier.
	 */
	private Identifier identifier;

	/**
	 * Build a new instance of <code>Entity</code>
	 */
	public Entity() {
		this(Identifier.random());
	}

	/**
	 * Build a new instance of <code>Entity</code>
	 * 
	 * @param identifier
	 *            specified entity
	 */
	public Entity(final Identifier identifier) {
		super();
		this.identifier = identifier;
	}

	/**
	 * @return identifier value
	 */
	public final Identifier getIdentifier() {
		return this.identifier;
	}

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
}
