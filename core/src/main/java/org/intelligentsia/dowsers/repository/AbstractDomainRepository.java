/**
 * 
 */
package org.intelligentsia.dowsers.repository;

import java.util.UUID;

import org.intelligentsia.dowsers.domain.ConcurrencyException;
import org.intelligentsia.dowsers.domain.DomainEntity;
import org.intelligentsia.dowsers.domain.DomainEntityNotFoundException;
import org.intelligentsia.dowsers.domain.DomainEventProvider;
import org.intelligentsia.dowsers.domain.DomainRepository;

import com.google.common.base.Preconditions;

/**
 * AbstractDomainRepository. 
 *
 * @author jgt
 *
 */
public abstract class AbstractDomainRepository implements DomainRepository {

	/**
	 * Build a new instance of AbstractDomainRepository.
	 */
	public AbstractDomainRepository() {
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.DomainRepository#find(java.lang.Class, java.util.UUID)
	 */
	@Override
	public <T extends DomainEntity> T find(Class<T> expectedType, UUID identity) throws DomainEntityNotFoundException, NullPointerException {
		Preconditions.checkNotNull(expectedType);
		Preconditions.checkNotNull(identity);
		return null;
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.DomainRepository#store(org.intelligentsia.dowsers.domain.DomainEntity)
	 */
	@Override
	public <T extends DomainEntity> void store(T domainEntity) throws NullPointerException, ConcurrencyException {
		Preconditions.checkNotNull(domainEntity);
	}
 
	
	protected <T extends DomainEntity> DomainEventProvider getDomainEventProvider(T domainEntity) {
		return null;
	}
}
