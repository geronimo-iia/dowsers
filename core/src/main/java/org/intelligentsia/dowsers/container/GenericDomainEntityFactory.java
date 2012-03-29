/**
 * 
 */
package org.intelligentsia.dowsers.container;

import java.lang.reflect.Constructor;
import java.util.concurrent.TimeUnit;

import org.intelligentsia.dowsers.domain.AggregateFactory;
import org.intelligentsia.dowsers.domain.DomainEntity;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * GenericDomainEntityFactory implements a generic DomainEntityFactory by using
 * reflection.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class GenericDomainEntityFactory implements DomainEntityFactory {

	/**
	 * AggregateFactory instance.
	 */
	private final AggregateFactory aggregateFactory;

	/**
	 * LoadingCache instance of [entity type, constructor].
	 */
	private LoadingCache<Class<?>, Constructor<?>> constructors;

	/**
	 * Build a new instance of DefaultDomainEntityFactory with a default cache:
	 * <ul>
	 * <li>1000 elements maximum</li>
	 * <li>expire after one day access</li>
	 * </ul>
	 * 
	 * @param aggregateFactory
	 *            aggregateFactory instance
	 * 
	 * @throws NullPointerException
	 *             if aggregateFactory is null
	 */
	public GenericDomainEntityFactory(final AggregateFactory aggregateFactory) throws NullPointerException {
		this(aggregateFactory, CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(1, TimeUnit.DAYS));
	}

	/**
	 * Build a new instance of GenericDomainEntityFactory.
	 * 
	 * @param aggregateFactory
	 *            aggregateFactory instance
	 * @param cacheBuilder
	 *            cache builder instance
	 * @throws NullPointerException
	 */
	public GenericDomainEntityFactory(final AggregateFactory aggregateFactory, final CacheBuilder<Object, Object> cacheBuilder) throws NullPointerException {
		super();
		this.aggregateFactory = Preconditions.checkNotNull(aggregateFactory);
		constructors = cacheBuilder.build(new CacheLoader<Class<?>, Constructor<?>>() {
			/**
			 * @see com.google.common.cache.CacheLoader#load(java.lang.Object)
			 */
			@Override
			public Constructor<?> load(final Class<?> exceptedType) throws Exception {
				final Constructor<?> constructor = exceptedType.getConstructor(AggregateFactory.class);
				if (!constructor.isAccessible()) {
					constructor.setAccessible(Boolean.TRUE);
				}
				return constructor;
			}
		});
	}

	/**
	 * @see org.intelligentsia.dowsers.container.DomainEntityFactory#create(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends DomainEntity> T create(final Class<T> exceptedType) throws RuntimeException {
		try {
			return (T) constructors.get(Preconditions.checkNotNull(exceptedType)).newInstance(aggregateFactory);
		} catch (final Throwable e) {
			throw Throwables.propagate(e);
		}
	}

}
