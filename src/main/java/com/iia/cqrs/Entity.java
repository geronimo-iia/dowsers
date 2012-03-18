/**
 * 
 */
package com.iia.cqrs;

import java.util.UUID;

import com.google.common.base.Objects;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public abstract class Entity {

	private Identifier identifier;

	/**
	 * Build a new instance of <code>Entity</code>
	 */
	public Entity() {
		super();
	}

	/**
	 * Build a new instance of <code>Entity</code>
	 * 
	 * @param identifier
	 */
	public Entity(final Identifier identifier) {
		super();
		this.identifier = identifier;
	}

	public final UUID getIdentity() {
		return this.identifier.getIdentity();
	}

	public final long getVersion() {
		return this.identifier.getVersion();
	}

	@Override
	public final int hashCode() {
		return Objects.hashCode(getIdentity());
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		return Objects.equal(other.getIdentity(), getIdentity());
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("identifier", identifier).toString();
	}
}
