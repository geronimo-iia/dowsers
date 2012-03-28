package org.intelligentsia.dowsers.domain;

import com.google.common.base.Preconditions;

/**
 * AbstractDomainRepository.
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
	 */
	public AbstractDomainRepository(DomainEntityFactory domainEntityFactory) {
		super();
		this.domainEntityFactory = Preconditions.checkNotNull(domainEntityFactory);
	}

	/**
	 * 
	 * @param domainEntity
	 * @return
	 */
	protected <T extends DomainEntity> DomainEventProvider getDomainEventProvider(T domainEntity) {
		return domainEntity.getDomainEventProvider();
	}
}
