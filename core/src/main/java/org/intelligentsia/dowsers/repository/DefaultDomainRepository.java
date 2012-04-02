/**
 * 
 */
package org.intelligentsia.dowsers.repository;

import org.intelligentsia.dowsers.container.DomainEntityFactory;
import org.intelligentsia.dowsers.domain.AbstractDomainRepository;
import org.intelligentsia.dowsers.domain.ConcurrencyException;
import org.intelligentsia.dowsers.domain.DomainEntity;
import org.intelligentsia.dowsers.domain.DomainEntityNotFoundException;

/**
 * DefaultRepository implements DomainRepository using session.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DefaultDomainRepository extends AbstractDomainRepository {

	/**
	 * thread local variable session.
	 */
	private final ThreadLocal<Session> sessions = new ThreadLocal<Session>() {
		@Override
		protected Session initialValue() {
			return new Session(domainEntityFactory, null);
		}
	};

	/**
	 * Build a new instance of DefaultDomainRepository.
	 * @param domainEntityFactory
	 * @throws NullPointerException
	 */
	public DefaultDomainRepository(DomainEntityFactory domainEntityFactory) throws NullPointerException {
		super(domainEntityFactory);
	}

	 

	/**
	 * @see org.intelligentsia.dowsers.domain.DomainRepository#find(java.lang.Class,
	 *      java.lang.String)
	 */
	@Override
	public <T extends DomainEntity> T find(Class<T> expectedType, String identity) throws DomainEntityNotFoundException, NullPointerException {
		return sessions.get().find(expectedType, identity);
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.DomainRepository#store(org.intelligentsia.dowsers.domain.DomainEntity)
	 */
	@Override
	public <T extends DomainEntity> void store(T domainEntity) throws NullPointerException, ConcurrencyException {
		sessions.get().store(domainEntity);
	}

}
