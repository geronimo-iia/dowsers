/**
 * 
 */
package com.iia.cqrs.events.processor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.iia.cqrs.Entity;
import com.iia.cqrs.annotation.DomainEventHandler;
import com.iia.cqrs.events.DomainEvent;

/**
 * EntityEventProcessor implements a EventProcessor dedicated to a single entity.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class EntityEventProcessor implements EventProcessor {

	private Map<Class<?>, Method> handlers;

	/**
	 * Build a new instance of <code>EntityEventProcessor</code>
	 */
	public EntityEventProcessor() {
		super();
		handlers = Maps.newHashMap();
	}

	@Override
	public void apply(Entity entity, DomainEvent domainEvent) {
		Method method = handlers.get(domainEvent.getClass());
		if (method != null) {
			handleEvent(method, entity, domainEvent);
		}
	}

	public <T extends Entity> void register(Class<T> entityType) {
		for (Method method : entityType.getMethods()) {
			if (hasEventSignature(method)) {
				register(entityType, method);
			}
		}
	}

	// /**
	// * Lookup for a specific event.
	// *
	// * @param <T>
	// * type of Entity
	// * @param <D>
	// * type of DomainEvent
	// * @param entityType
	// * entity Type
	// * @param domainEventType
	// * domain Event type
	// * @return
	// */
	// protected <D extends DomainEvent> Method lookup(Class<T> entityType, Class<D> domainEventType) {
	// for (Method method : entityType.getMethods()) {
	// if (hasEventSignature(method)) {
	// if (domainEventType.isAssignableFrom(method.getParameterTypes()[0])) {
	// return register(entityType, method);
	// }
	// }
	// }
	// return null;
	// }

	/**
	 * Method has an event signature when:
	 * <ul>
	 * <li>declare annotation @DomainEventHandler</li>
	 * <li>have a return type void</li>
	 * <li>have one parameter</li>
	 * <li>the parameter extends DomainEvent</li>
	 * </ul>
	 * 
	 * @param method
	 *            method to check
	 * @return True if the specified method has an event signature.
	 */
	@VisibleForTesting
	Boolean hasEventSignature(Method method) {
		return ((method.getAnnotation(DomainEventHandler.class) != null) && (method.getParameterTypes().length == 1) && (method
				.getReturnType().equals(Void.class)))
				&& (DomainEvent.class.isAssignableFrom(method.getParameterTypes()[1]));
	}

	/**
	 * 
	 * @param entityType
	 * @param method
	 * @return
	 * @throws IllegalStateException
	 */
	@VisibleForTesting
	<T extends Entity>	Method register(Class<T> entityType, Method method) throws IllegalStateException {
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
		handlers.put(method.getParameterTypes()[0], method);
		return method;
	}

	/**
	 * Invokes the wrapped handler method to handle {@code event}.
	 * 
	 * @param event
	 *            event to handle
	 * @throws IllegalStateException
	 *             if the wrapped method throws any {@link Throwable} that is not an {@link Error} ({@code Error}s are
	 *             propagated as-is).
	 */
	private void handleEvent(Method method, Object target, Object event) throws IllegalStateException {
		try {
			method.invoke(target, new Object[] { event });
		} catch (IllegalArgumentException e) {
			throw new Error("Method rejected target/argument: " + event, e);
		} catch (IllegalAccessException e) {
			throw new Error("Method became inaccessible: " + event, e);
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof Error) {
				throw (Error) e.getCause();
			}
			throw new IllegalStateException(e);
		}
	}
}
