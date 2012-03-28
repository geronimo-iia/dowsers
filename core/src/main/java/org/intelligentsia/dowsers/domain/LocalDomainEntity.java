/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import java.util.UUID;

/**
 * LocalDomainEntity is a domain entity which can be used only with a domain
 * entity (also named root entity).
 * 
 * @author jgt
 * 
 */
public class LocalDomainEntity extends Entity {

	/**
	 * Build a new instance of LocalDomainEntity.
	 */
	public LocalDomainEntity() {
		super(Identifier.random());
	}

	/**
	 * Build a new instance of LocalDomainEntity.
	 * 
	 * @param identity
	 * @throws NullPointerException
	 */
	public LocalDomainEntity(UUID identity) throws NullPointerException {
		super(Identifier.forInitialVersion(identity));
	}

	/**
	 * @throws IllegalStateException
	 *             always
	 */
	@Override
	void nextVersion() throws IllegalStateException {
		throw new IllegalStateException("Entity#nextVersion must be only called on a DomainEntity instance.");
	}

	/**
	 * @throws IllegalStateException
	 *             always
	 */
	@Override
	void setVersion(long version) throws IllegalStateException {
		throw new IllegalStateException("Entity#setVersion must be only called on a DomainEntity instance.");
	}
}
