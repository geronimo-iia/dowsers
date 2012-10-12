package org.intelligentsia.dowsers.entity;

import org.intelligentsia.dowsers.entity.meta.MetaEntityContext;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * EntitySupport implements common methods of {@link Entity}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public abstract class EntitySupport implements Entity, Comparable<Entity> {
	/**
	 * Entity identity.
	 */
	protected final String identity;
	/**
	 * {@link MetaEntityContext} associated.
	 */
	protected final transient MetaEntityContext metaEntityContext;

	/**
	 * Build a new instance of Entity.
	 * 
	 * @param identity
	 *            entity's identity.
	 * @param metaEntityContext
	 *            {@link MetaEntityContext} associated with this instance.
	 * @throws NullPointerException
	 *             if identifier or metaEntityContext is null
	 */
	public EntitySupport(final String identity, final MetaEntityContext metaEntityContext) throws NullPointerException {
		super();
		this.identity = Preconditions.checkNotNull(identity);
		this.metaEntityContext = Preconditions.checkNotNull(metaEntityContext);
	}

	@Override
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

	@Override
	public int compareTo(final Entity o) {
		return identity.compareTo(o.getIdentity());
	}

	@Override
	public final MetaEntityContext getMetaEntityContext() {
		return metaEntityContext;
	}
}
