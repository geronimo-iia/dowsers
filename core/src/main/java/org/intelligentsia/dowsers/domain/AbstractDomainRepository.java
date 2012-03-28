package org.intelligentsia.dowsers.domain;

import com.google.common.base.Preconditions;

/**
 * AbstractDomainRepository provides access on DomainEventProvider interace associated with DomainEntity.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public abstract class AbstractDomainRepository implements DomainRepository {
	/**
	 * DomainEntityFactory instance.
	 */
	protected final DomainEntityFactory domainEntityFactory;

	/**
	 * Build a new instance of AbstractDomainRepository.
	 * 
	 * @param domainEntityFactory
	 *            domain entity factory instance
	 * @throws NullPointerException
	 *             if domainEntityFactory is null
	 */
	public AbstractDomainRepository(DomainEntityFactory domainEntityFactory) throws NullPointerException {
		super();
		this.domainEntityFactory = Preconditions.checkNotNull(domainEntityFactory);
	}

	/**
	 * @param domainEntity
	 *            domain entity
	 * @return DomainEventProvider instance associated with specified domain
	 *         entity.
	 */
	protected <T extends DomainEntity> DomainEventProvider getDomainEventProvider(T domainEntity) {
		return domainEntity.getDomainEventProvider();
	}
}
