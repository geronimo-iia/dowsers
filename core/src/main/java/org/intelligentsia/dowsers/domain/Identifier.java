/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import java.io.Serializable;
import java.util.StringTokenizer;
import java.util.UUID;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * Identifier manage pair of identity # version. Identifier have a natural order
 * based on their version value.
 * 
 * Reasons to expose UUID class rather than String:
 * <ul>
 * <li>in order to keep other way to generate UUID instance,</li>
 * <li>avoid deal with UUID casting to and from String,</li>
 * <li>keep that UUID is unique,</li>
 * </ul>
 * 
 * Note about version member.<br/>
 * The version is used to detect concurrency violations, meaning this is used to
 * prevent conflicts that occur because between the time the command was send
 * and the entity was saved an other user or process has updated the same
 * entity.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Identifier implements Serializable, Comparable<Identifier> {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Initial Version Value (0).
	 */
	@VisibleForTesting
	static final transient long INITIAL_VERSION = 0;
	/**
	 * Latest version value (Long.MAX_VALUE).
	 */
	@VisibleForTesting
	static final transient long LATEST_VERSION = Long.MAX_VALUE;
	/**
	 * Identity instance (UUID implementation).
	 */
	private final UUID identity;
	/**
	 * Version instance (long).
	 */
	private final long version;

	/**
	 * Build a new instance of Identifier.
	 * 
	 * @param identity
	 *            identity value
	 * @param version
	 *            version value (must be greater than or equal to
	 *            INITIAL_VERSION)
	 * @throws NullPointerException
	 *             if identity is null
	 * @throws IllegalArgumentException
	 *             if version < INITIAL_VERSION
	 */
	private Identifier(final UUID identity, final long version) throws NullPointerException, IllegalArgumentException {
		this.identity = Preconditions.checkNotNull(identity, "identity is required");
		Preconditions.checkArgument(version >= Identifier.INITIAL_VERSION, "version must be greater than or equal to INITIAL_VERSION");
		this.version = version;
	}

	/**
	 * Equivalent to call
	 * <code>Identifier.forInitialVersion(UUID.randomUUID())</code>
	 * 
	 * @return a randomized Identifier instance with an initial version.
	 */
	public static Identifier random() {
		return Identifier.forInitialVersion(UUID.randomUUID());
	}

	/**
	 * 
	 * @param identity
	 *            identity value
	 * @return an initial Identifier instance for specified identity
	 * @throws NullPointerException
	 *             if identity is null
	 */
	public static Identifier forInitialVersion(final UUID identity) throws NullPointerException {
		return Identifier.forSpecificVersion(identity, Identifier.INITIAL_VERSION);
	}

	/**
	 * 
	 * @param identity
	 *            identity value
	 * @return an latest Identifier instance for specified identity
	 * @throws NullPointerException
	 *             if identity is null
	 */
	public static Identifier forLatestVersion(final UUID identity) throws NullPointerException {
		return Identifier.forSpecificVersion(identity, Identifier.LATEST_VERSION);
	}

	/**
	 * 
	 * @param identity
	 *            identity value
	 * @param version
	 *            version value
	 * @return an Identifier instance for specified identity and version
	 * @throws NullPointerException
	 *             if identity is null
	 * @throws IllegalArgumentException
	 *             if version < INITIAL_VERSION
	 */
	public static Identifier forSpecificVersion(final UUID identity, final long version) throws NullPointerException, IllegalArgumentException {
		return new Identifier(identity, version);
	}

	/**
	 * @return identity value
	 */
	public UUID getIdentity() {
		return identity;
	}

	/**
	 * @return version value
	 */
	public long getVersion() {
		return version;
	}

	/**
	 * @return Boolean.TRUE if this version is an initial version.
	 */
	public boolean isForInitialVersion() {
		return version == Identifier.INITIAL_VERSION;
	}

	/**
	 * @return Boolean.TRUE if this version is a latest version.
	 */
	public boolean isForLatestVersion() {
		return version == Identifier.LATEST_VERSION;
	}

	/**
	 * @return Boolean.TRUE if this version is specific (not latest version)
	 */
	public boolean isForSpecificVersion() {
		return !isForLatestVersion();
	}

	/**
	 * @param version
	 *            version value
	 * @return an Identifier instance for specified version with the same
	 *         identity as this instance.
	 * @throws IllegalArgumentException
	 *             if version < INITIAL_VERSION
	 */
	public Identifier withVersion(final long version) throws IllegalArgumentException {
		return Identifier.forSpecificVersion(identity, version);
	}

	/**
	 * @return next version of Identifier.
	 */
	public Identifier nextVersion() {
		if (isForLatestVersion()) {
			return this;
		} else {
			return withVersion(version + 1);
		}
	}

	/**
	 * Compare this Identifier instance with the other ignoring version.
	 * 
	 * @param other
	 * @return Boolean.TRUE if identity of both are the same.
	 */
	public boolean hasSameIdentity(final Identifier other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		return identity.equals(other.identity);
	}

	/**
	 * @param other
	 *            other version identifier
	 * @return Boolean.TRUE if other instance is compatible with this instance.
	 * @throws IllegalArgumentException
	 *             if other version identifier is not a specific version.
	 */
	public boolean isCompatible(final Identifier other) throws IllegalArgumentException {
		Preconditions.checkArgument(other.isForSpecificVersion(), "cannot check for compatibility with non-specific version");
		if (isForLatestVersion()) {
			return hasSameIdentity(other);
		} else {
			return equals(other);
		}
	}

	/**
	 * String representation follow this pattern:
	 * <ul>
	 * <li>for all no latest version; ${identity}#${version}</li>
	 * <li>for latest version: ${identity}</li>
	 * </ul>
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(identity.toString());
		if (!isForLatestVersion()) {
			builder.append("#").append(version);
		}
		return builder.toString();
	}

	/**
	 * Parse specified string as an Identifier @see {@link #toString()} for
	 * pattern. Accept AZZZ#1, etc..
	 * 
	 * @param value
	 * @return an instance of Identifier
	 * @throws IllegalArgumentException
	 *             if value is not an Identifier, not conform to @see
	 *             {@link #toString()} call
	 * @throws NullPointerException
	 *             if value is null
	 */
	public static Identifier parseIdentifier(final String value) throws IllegalArgumentException, NullPointerException {
		final StringTokenizer tokenizer = new StringTokenizer(Preconditions.checkNotNull(value), "#");
		// parse UUID
		final UUID uuid = UUID.fromString(tokenizer.nextToken());
		// parse version
		final long version = tokenizer.hasMoreTokens() ? Long.parseLong(tokenizer.nextToken()) : Identifier.LATEST_VERSION;
		return new Identifier(uuid, version);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		return Objects.hashCode(identity, version);
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
		final Identifier other = (Identifier) obj;
		return Objects.equal(other.getIdentity(), getIdentity()) && Objects.equal(other.getVersion(), getVersion());
	}

	/**
	 * Returns a negative integer, zero, or a positive integer as this object
	 * version is less than, equal to, or greater than the specified object
	 * version.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final Identifier o) {
		if (!hasSameIdentity(o)) {
			throw new ClassCastException("Objects haven't same identity");
		}
		return Long.valueOf(version).compareTo(Long.valueOf(o.version));
	}
}
