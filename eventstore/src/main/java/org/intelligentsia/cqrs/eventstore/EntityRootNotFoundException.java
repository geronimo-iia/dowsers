/**
 * 
 */
package org.intelligentsia.cqrs.eventstore;

import java.util.UUID;

/**
 * EntityRootNotFoundException raised by Repository.
 * 
 * @author JGT
 * 
 */
public class EntityRootNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String type;

	private final UUID identity;

	/**
	 * 
	 * Build a new instance of EntityRootNotFoundException.
	 * 
	 * @param type
	 *            entity type
	 * @param identity
	 *            identity of wished entity
	 * @param root
	 *            exception root
	 */
	public EntityRootNotFoundException(final String type, final UUID identity, final Throwable root) {
		super("Entity root " + type + " with identity " + identity, root);
		this.type = type;
		this.identity = identity;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the identity
	 */
	public UUID getIdentity() {
		return identity;
	}

}
