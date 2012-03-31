/**
 * 
 */
package org.intelligentsia.dowsers.event.processor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.intelligentsia.dowsers.domain.Entity;
import org.intelligentsia.dowsers.event.DomainEvent;

import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;

/**
 * CacheEventProcessorProvider manage a set of EventProcessor.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class CacheEventProcessorProvider implements EventProcessorProvider, EventProcessor {

	private LoadingCache<Class<? extends Entity>, EntityEventProcessor> processors;

	/**
	 * Build a new instance of CacheEventProcessorProvider with default cache:
	 * <ul>
	 * <li>Maximum size : 1000 elements</li>
	 * <li>expire after access : 1 days</li>
	 * </ul>
	 */
	public CacheEventProcessorProvider() {
		this(CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(1, TimeUnit.DAYS));
	}

	/**
	 * Build a new instance of <code>CacheEventProcessorProvider</code>
	 * 
	 * @param cacheBuilder
	 */
	public CacheEventProcessorProvider(final CacheBuilder<Object, Object> cacheBuilder) {
		super();
		/**
		 * Add specific cache loader.
		 */
		processors = cacheBuilder.build(new CacheLoader<Class<? extends Entity>, EntityEventProcessor>() {
			@Override
			public EntityEventProcessor load(final Class<? extends Entity> key) throws Exception {
				final EntityEventProcessor processor = new EntityEventProcessor();
				processor.register(key);
				return processor;
			}
		});
	}

	/**
	 * @see org.intelligentsia.dowsers.event.processor.EventProcessorProvider#get(java.lang.Class)
	 */
	@Override
	public <T extends Entity> EventProcessor get(final Class<T> entityType) throws RuntimeException {
		try {
			return processors.get(entityType);
		} catch (final ExecutionException e) {
			throw Throwables.propagate(Throwables.getRootCause(e));
		} catch (final UncheckedExecutionException e) {
			throw Throwables.propagate(Throwables.getRootCause(e));
		}
	}

	/**
	 * Unregister specified entity type.
	 * 
	 * @param entityType
	 *            entity type to remove
	 */
	public <T extends Entity> void unregister(final Class<T> entityType) {
		processors.invalidate(entityType);
	}

	/**
	 * @see org.intelligentsia.dowsers.event.processor.EventProcessor#apply(org.intelligentsia.dowsers.domain.Entity,
	 *      org.intelligentsia.dowsers.event.DomainEvent)
	 */
	@Override
	public void apply(final Entity entity, final DomainEvent domainEvent) {
		get(entity.getClass()).apply(entity, domainEvent);
	}

	/**
	 * @see org.intelligentsia.dowsers.event.processor.EventProcessor#register(java.lang.Class)
	 */
	@Override
	public <T extends Entity> void register(final Class<T> entityType) throws IllegalStateException {
		get(entityType);
	}

}
