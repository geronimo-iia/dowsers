/**
 * 
 */
package com.iia.cqrs.events.processor;

import java.util.concurrent.ExecutionException;

import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.iia.cqrs.Entity;
import com.iia.cqrs.events.DomainEvent;

/**
 * CacheEventProcessorProvider manage a set of EventProcessor.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class CacheEventProcessorProvider implements EventProcessorProvider, EventProcessor {

	private LoadingCache<Class<? extends Entity>, EntityEventProcessor> processors;

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
	 * @see com.iia.cqrs.events.processor.EventProcessorProvider#get(java.lang.Class)
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

	public <T extends Entity> void unregister(final Class<T> entityType) {
		processors.invalidate(entityType);
	}

	/**
	 * @see com.iia.cqrs.events.processor.EventProcessor#apply(com.iia.cqrs.Entity,
	 *      com.iia.cqrs.events.DomainEvent)
	 */
	@Override
	public void apply(final Entity entity, final DomainEvent domainEvent) {
		get(entity.getClass()).apply(entity, domainEvent);
	}

	/**
	 * @see com.iia.cqrs.events.processor.EventProcessor#register(java.lang.Class)
	 */
	@Override
	public <T extends Entity> void register(final Class<T> entityType) throws IllegalStateException {
		get(entityType);
	}

}
