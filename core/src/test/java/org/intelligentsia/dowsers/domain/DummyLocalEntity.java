/**
 * 
 */
package org.intelligentsia.dowsers.domain;

/**
 * DummyLocalEntity.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class DummyLocalEntity extends LocalDomainEntity {

	private boolean onRegisterCalled = Boolean.FALSE;

	/**
	 * Build a new instance of DummyLocalEntity.
	 */
	public DummyLocalEntity() {
		super();
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.LocalDomainEntity#onRegister(org.intelligentsia.dowsers.domain.DomainEntity)
	 */
	@Override
	protected void onRegister(DomainEntity domainEntity) {
		super.onRegister(domainEntity);
		onRegisterCalled = Boolean.TRUE;
	}

	/**
	 * @return the onRegisterCalled
	 */
	public boolean isOnRegisterCalled() {
		return onRegisterCalled;
	}
}
