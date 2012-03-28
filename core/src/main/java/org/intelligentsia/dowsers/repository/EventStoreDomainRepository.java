/**
 * 
 */
package org.intelligentsia.dowsers.repository;

import java.util.UUID;

import org.intelligentsia.dowsers.domain.ConcurrencyException;
import org.intelligentsia.dowsers.domain.DomainEntity;
import org.intelligentsia.dowsers.domain.DomainEntityFactory;
import org.intelligentsia.dowsers.domain.DomainEntityNotFoundException;
import org.intelligentsia.dowsers.domain.DomainEventProvider;

import com.google.common.base.Preconditions;

/**
 * EventStoreDomainRepository.
 * 
 * @author jgt
 * 
 */
public class EventStoreDomainRepository extends AbstractDomainRepository {

	private DomainEventStorage domainEventStorage;
	private DomainEntityFactory domainEntityFactory;

	/**
	 * Build a new instance of EventStoreDomainRepository.
	 * 
	 * @param domainEventStorage
	 */
	public EventStoreDomainRepository(DomainEventStorage domainEventStorage) {
		super();
		this.domainEventStorage = domainEventStorage;
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.DomainRepository#find(java.lang.Class,
	 *      java.util.UUID)
	 */
	@Override
	public <T extends DomainEntity> T find(Class<T> expectedType, UUID identity) throws DomainEntityNotFoundException, NullPointerException {
		Preconditions.checkNotNull(identity);
		// instaciate a new domain entity
		T entity = domainEntityFactory.create(Preconditions.checkNotNull(expectedType));
		// retreive DomainEventProvider
		DomainEventProvider domainEventProvider = getDomainEventProvider(entity);
		// load from history
		/*
		 * its not good to separate : findAllDomainEvents from getCurrentVersion, because version can change while time elapsed between two call. 
		 */
		domainEventProvider.loadFromHistory(domainEventStorage.findAllDomainEvents(identity), domainEventStorage.getCurrentVersion(identity));
		return entity;
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.DomainRepository#store(org.intelligentsia.dowsers.domain.DomainEntity)
	 */
	@Override
	public <T extends DomainEntity> void store(T domainEntity) throws NullPointerException, ConcurrencyException {
		// retreive DomainEventProvider.
		DomainEventProvider domainEventProvider = getDomainEventProvider(Preconditions.checkNotNull(domainEntity));
		// store
		domainEventStorage.store(domainEventProvider);
		// what about incrementVersion ????????????????????????????
		// mark change as committed
		domainEventProvider.markChangesCommitted();
	}

}
