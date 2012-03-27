/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import java.lang.reflect.Constructor;
import java.util.concurrent.TimeUnit;

import org.intelligentsia.dowsers.annotation.TODO;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * GenericDomainEntityFactory implements DomainEntityFactory .
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class GenericDomainEntityFactory implements DomainEntityFactory {

	/**
	 * AggregateFactory instance.
	 */
	private final AggregateFactory aggregateFactory;

	private LoadingCache<Class<?>, Constructor<?>> constructors;

	/**
	 * Build a new instance of DefaultDomainEntityFactory.
	 * 
	 * @param aggregateFactory
	 * 
	 * @throws NullPointerException
	 *             if aggregateFactory is null
	 */
	public GenericDomainEntityFactory(final AggregateFactory aggregateFactory) throws NullPointerException {
		super();
		this.aggregateFactory = Preconditions.checkNotNull(aggregateFactory);
		constructors = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(1, TimeUnit.DAYS).build(new CacheLoader<Class<?>, Constructor<?>>() {
			/**
			 * @see com.google.common.cache.CacheLoader#load(java.lang.Object)
			 */
			@Override
			public Constructor<?> load(Class<?> exceptedType) throws Exception {
				Constructor<?> constructor = exceptedType.getConstructor(AggregateFactory.class);
				if (!constructor.isAccessible()) {
					constructor.setAccessible(Boolean.TRUE);
				}
				return constructor;
			}

		});
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.DomainEntityFactory#create(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TODO("Add a better implementation")
	public <T extends DomainEntity> T create(final Class<T> exceptedType) throws RuntimeException {
		try {
			return (T) constructors.get(exceptedType).newInstance(aggregateFactory);
		} catch (final Throwable e) {
			throw Throwables.propagate(e);
		}
	}

}
