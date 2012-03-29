/**
 * 
 */
package org.intelligentsia.dowsers.domain;

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
		super(IdentifierFactoryProvider.generateNewIdentifier());
	}

	/**
	 * Build a new instance of LocalDomainEntity.
	 * 
	 * @param identity
	 * @throws NullPointerException
	 *             if identity is null
	 */
	public LocalDomainEntity(final String identity) throws NullPointerException {
		super(identity);
	}

	/**
	 * On register method call back.
	 */
	protected void onRegister(final DomainEntity domainEntity) {
		// do nothing
	}

}
