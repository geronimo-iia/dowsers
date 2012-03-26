/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import org.intelligentsia.dowsers.annotation.TODO;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

/**
 * DefaultDomainEntityFactory implements DomainEntityFactory .
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DefaultDomainEntityFactory implements DomainEntityFactory {

	/**
	 * AggregateFactory instance.
	 */
	private final AggregateFactory aggregateFactory;

	/**
	 * Build a new instance of DefaultDomainEntityFactory.
	 * 
	 * @param aggregateFactory
	 * 
	 * @throws NullPointerException
	 *             if aggregateFactory is null
	 */
	public DefaultDomainEntityFactory(final AggregateFactory aggregateFactory) throws NullPointerException {
		super();
		this.aggregateFactory = Preconditions.checkNotNull(aggregateFactory);
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.DomainEntityFactory#create(java.lang.Class)
	 */
	@Override
	@TODO("Add a better implementation")
	public <T extends DomainEntity> T create(final Class<T> exceptedType) throws RuntimeException {
		try {
			return exceptedType.getConstructor(AggregateFactory.class).newInstance(aggregateFactory);
		} catch (final Throwable e) {
			throw Throwables.propagate(e);
		}
	}

}
