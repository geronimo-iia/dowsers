/**
 * 
 */
package org.intelligentsia.dowsers.repository.snapshot;

import hirondelle.date4j.DateTime;

import java.io.Serializable;
import java.util.TimeZone;

import org.intelligentsia.dowsers.memento.Memento;

import com.google.common.base.Objects;

/**
 * SnapShot class.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class SnapShot implements Serializable {
	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = 1;
	/**
	 * Identity of snapshot and entity.
	 */
	private String identity;
	/**
	 * Version of entity that this snapshot represents.
	 */
	private long version;
	/**
	 * Memento instance of this entity.
	 */
	private final Memento memento;

	/**
	 * Time stamp of this snapshot.
	 */
	private final DateTime timestamp;

	/**
	 * Build a new instance of SnapShot.
	 * 
	 * @param identity
	 * @param version
	 * @param memento
	 */
	public SnapShot(final String identity, final long version, final Memento memento) {
		this(identity, version, DateTime.now(TimeZone.getTimeZone("GMT")), memento);
	}

	/**
	 * Build a new instance of SnapShot.
	 * 
	 * @param identity
	 * @param version
	 * @param timestamp
	 * @param memento
	 */
	public SnapShot(final String identity, final long version, final DateTime timestamp, final Memento memento) {
		super();
		this.identity = identity;
		this.version = version;
		this.timestamp = timestamp;
		this.memento = memento;
	}

	/**
	 * @return the identity
	 */
	public String getIdentity() {
		return identity;
	}

	/**
	 * @param identity
	 *            the identity to set
	 */
	public void setIdentity(final String identity) {
		this.identity = identity;
	}

	/**
	 * @return the version
	 */
	public long getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(final long version) {
		this.version = version;
	}

	/**
	 * @return the memento
	 */
	public Memento getMemento() {
		return memento;
	}

	/**
	 * @return the timestamp
	 */
	public DateTime getTimestamp() {
		return timestamp;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("identity", identity).add("version", version).add("timestamp", timestamp).toString();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(identity, memento, timestamp);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final SnapShot other = (SnapShot) obj;
		return Objects.equal(other.getIdentity(), getIdentity()) && Objects.equal(other.getVersion(), getVersion()) && Objects.equal(other.getTimestamp(), getTimestamp());
	}

}
