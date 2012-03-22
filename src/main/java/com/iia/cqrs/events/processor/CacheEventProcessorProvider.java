/**
 * 
 */
package com.iia.cqrs.events.processor;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
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
	public CacheEventProcessorProvider(CacheBuilder<Class<? extends Entity>, EntityEventProcessor> cacheBuilder) {
		super();
		/**
		 * Add specific cache loader.
		 */
		processors = cacheBuilder.build(new CacheLoader<Class<? extends Entity>, EntityEventProcessor>() {
			@Override
			public EntityEventProcessor load(Class<? extends Entity> key) throws Exception {
				EntityEventProcessor processor = new EntityEventProcessor();
				processor.register(key);
				return processor;
			}
		});
	}

	/**
	 * @see com.iia.cqrs.events.processor.EventProcessorProvider#get(java.lang.Class)
	 */
	@Override
	public <T extends Entity> EventProcessor get(Class<T> entityType) throws IllegalStateException {
		try {
			return processors.get(entityType);
		} catch (ExecutionException e) {
			throw new IllegalStateException(e.getCause());
		}
	}

	public <T extends Entity> void unregister(Class<T> entityType) {
		processors.invalidate(entityType);
	}

	/**
	 * @see com.iia.cqrs.events.processor.EventProcessor#apply(com.iia.cqrs.Entity, com.iia.cqrs.events.DomainEvent)
	 */
	@Override
	public void apply(Entity entity, DomainEvent domainEvent) {
		get(entity.getClass()).apply(entity, domainEvent);
	}

	/**
	 * @see com.iia.cqrs.events.processor.EventProcessor#register(java.lang.Class)
	 */
	@Override
	public <T extends Entity> void register(Class<T> entityType) {
		get(entityType);
	}

}
