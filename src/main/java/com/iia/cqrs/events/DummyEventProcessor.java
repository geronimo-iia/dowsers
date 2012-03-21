/**
 * 
 */
package com.iia.cqrs.events;

import java.lang.reflect.Method;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.iia.cqrs.Entity;
import com.iia.cqrs.annotation.DomainEventHandler;

/**
 * DummyEventProcessor.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class DummyEventProcessor implements EventProcessor {

	private LoadingCache<String, Method> definitions;

	/**
	 * Build a new instance of <code>DummyEventProcessor</code>
	 */
	public DummyEventProcessor() {
		super();

	}

	/**
	 * @see com.iia.cqrs.events.EventProcessor#apply(com.iia.cqrs.Entity, com.iia.cqrs.events.DomainEvent)
	 */
	@Override
	public <T extends DomainEvent> void apply(Entity source, T domainEvent) {

	}

	/**
	 * @see com.iia.cqrs.events.EventProcessor#register(java.lang.Class)
	 */
	@Override
	public <T extends Entity> void register(Class<T> entityType) {
		for (Method method : entityType.getMethods()) {
			// find annotation
			if ((method.getAnnotation(DomainEventHandler.class) != null)) {
				register(entityType, method);
			} else {
				// name convention apply + DomainEvent.class.getSimpleName()
				if (method.getName().startsWith("apply") && (method.getName().length() > "apply".length())) {
					if (method.getReturnType().equals(Void.class) && method.getParameterTypes().length == 1) {
						if (DomainEvent.class.isAssignableFrom(method.getParameterTypes()[1])) {
							// let's go my friend
							register(entityType, method);
						}
					}
				}
			}
		}
	}

	protected <T extends Entity, D extends DomainEvent> Method lookup(Class<T> entityType, D domainEvent) {
		for (Method method : entityType.getMethods()) {
			if (method.getParameterTypes().length == 1 && method.getReturnType().equals(Void.class)) {
				if (DomainEvent.class.isAssignableFrom(method.getParameterTypes()[1])) {
					boolean declared = (method.getAnnotation(DomainEventHandler.class) != null)
							|| method.getName().startsWith("apply") && (method.getName().length() > "apply".length());
					if (declared) {
						return method;
					}
				}
			}
		}
		return null;
	}

	protected <T extends Entity> void register(Class<T> entityType, Method method) throws IllegalStateException {
		if (!method.getReturnType().equals(Void.class)) {
			throw new IllegalStateException("Method " + method.getName() + " must have void return type");
		}
		if (method.getParameterTypes().length != 1) {
			throw new IllegalStateException("Method " + method.getName() + " must have one parameter");
		}
		if (!DomainEvent.class.isAssignableFrom(method.getParameterTypes()[1])) {
			throw new IllegalStateException("Method " + method.getName() + " must have parameter type of DomainEvent");
		}
		// save entityType + method.getParameterTypes()[1] ==> method
		if (!method.isAccessible()) {
			method.setAccessible(Boolean.TRUE);
		}

	}
}
