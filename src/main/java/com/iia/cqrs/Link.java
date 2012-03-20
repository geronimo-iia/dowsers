/**
 * 
 */
package com.iia.cqrs;

import java.io.Serializable;
import java.util.UUID;

import com.google.common.base.Objects;

/**
 * A link on a specified entity.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Link<T extends Entity> implements Serializable {

	private static final long serialVersionUID = 3222008517917213917L;
	/**
	 * target identity.
	 */
	private final UUID identity;

	/**
	 * Build a new instance of Link.
	 * 
	 * @param target
	 *            link target
	 */
	public Link(T target) {
		super();
		this.identity = target.getIdentifier().getIdentity();
	}

	/**
	 * @return target identity
	 */
	public UUID getIdentity() {
		return this.identity;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(identity);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		Link other = (Link) obj;
		return Objects.equal(other.getIdentity(), getIdentity());
	}

}
