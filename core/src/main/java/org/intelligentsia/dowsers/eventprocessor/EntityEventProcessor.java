/**
 * 
 */
package org.intelligentsia.dowsers.eventprocessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.intelligentsia.dowsers.annotation.DomainEventHandler;
import org.intelligentsia.dowsers.domain.DomainEvent;
import org.intelligentsia.dowsers.domain.Entity;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;

/**
 * EntityEventProcessor implements a EventProcessor dedicated to a single
 * entity.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class EntityEventProcessor implements EventProcessor {
	/**
	 * Map of DomainEvent type and methods handler.
	 */
	private final Map<Class<?>, Method> handlers;
	/**
	 * Flag to quiet mode when register entity.
	 */
	private final Boolean quietMode;
	/**
	 * Managed entity type.
	 */
	private Class<? extends Entity> managedEntityType;

	/**
	 * Build a new instance of EntityEventProcessor. All methods with an
	 * annotation @DomainEventHandler and bad signature will raise exception.
	 */
	public EntityEventProcessor() {
		this(Boolean.FALSE);
	}

	/**
	 * Build a new instance of EntityEventProcessor.
	 * 
	 * @param quietMode
	 *            if true all methods with an annotation @DomainEventHandler
	 *            with bad signature will be ignored (quiet mode), if false then
	 *            raise exception
	 */
	public EntityEventProcessor(final Boolean quietMode) {
		super();
		handlers = Maps.newHashMap();
		this.quietMode = quietMode;
		managedEntityType = null;
	}

	/**
	 * @see org.intelligentsia.dowsers.eventprocessor.EventProcessor#apply(org.intelligentsia.dowsers.domain.Entity,
	 *      org.intelligentsia.dowsers.domain.DomainEvent)
	 */
	@Override
	public void apply(final Entity entity, final DomainEvent domainEvent) {
		final Method method = handlers.get(domainEvent.getClass());
		// if handled
		if (method != null) {
			handleEvent(method, entity, domainEvent);
		}
	}

	/**
	 * @see org.intelligentsia.dowsers.eventprocessor.EventProcessor#register(java.lang.Class)
	 */
	@Override
	public <T extends Entity> void register(final Class<T> entityType) throws IllegalStateException {
		if (isInitialized()) {
			if (!quietMode) {
				throw new IllegalStateException("EntityEventProcessor is ever initialized");
			}
			return;
		}
		// try register all methods
		for (final Method method : entityType.getDeclaredMethods()) {
			if (quietMode) {
				// in quiet mode,we check if signature is good
				if (hasEventSignature(method)) {
					register(entityType, method);
				}
			} else if (method.getAnnotation(DomainEventHandler.class) != null) {
				register(entityType, method);
			}
		}
		// set managed entity type
		managedEntityType = entityType;
	}

	/**
	 * @return true if this processor is initialized
	 */
	protected boolean isInitialized() {
		return managedEntityType != null;
	}

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
	Boolean hasEventSignature(final Method method) {
		return method.getAnnotation(DomainEventHandler.class) != null && method.getParameterTypes().length == 1 && Void.TYPE == method.getReturnType() && DomainEvent.class.isAssignableFrom(method.getParameterTypes()[0]);
	}

	/**
	 * 
	 * @param managedEntityType
	 * @param method
	 * @return
	 * @throws IllegalStateException
	 */
	@VisibleForTesting
	<T extends Entity> Method register(final Class<T> entityType, final Method method) throws IllegalStateException {
		// check signature
		if (Void.TYPE != method.getReturnType()) {
			throw new IllegalStateException("Method " + method.getName() + " must have void return type");
		}
		if (method.getParameterTypes().length != 1) {
			throw new IllegalStateException("Method " + method.getName() + " must have one parameter");
		}
		if (!DomainEvent.class.isAssignableFrom(method.getParameterTypes()[0])) {
			throw new IllegalStateException("Method " + method.getName() + " must have parameter type of DomainEvent");
		}
		// visibility
		if (!method.isAccessible()) {
			method.setAccessible(Boolean.TRUE);
		}
		// save method.getParameterTypes()[0] ==> method
		if (handlers.put(method.getParameterTypes()[0], method) != null) {
			throw new IllegalStateException("Duplicate domaine event handler for " + method.getParameterTypes()[0].getName());
		}
		return method;
	}

	/**
	 * Invokes the wrapped handler method to handle {@code event}.
	 * 
	 * @param event
	 *            event to handle
	 * @throws IllegalStateException
	 *             if the wrapped method throws any {@link Throwable} that is
	 *             not an {@link Error} ({@code Error}s are propagated as-is).
	 */
	private void handleEvent(final Method method, final Object target, final Object event) throws IllegalStateException {
		try {
			method.invoke(target, new Object[] { event });
		} catch (final IllegalArgumentException e) {
			throw new Error("Method rejected target/argument: " + event, e);
		} catch (final IllegalAccessException e) {
			throw new Error("Method became inaccessible: " + event, e);
		} catch (final InvocationTargetException e) {
			if (e.getCause() instanceof Error) {
				throw (Error) e.getCause();
			}
			throw new IllegalStateException(e);
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("managedEntityType", managedEntityType.getName()).toString();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(managedEntityType.getName().hashCode());
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final EntityEventProcessor other = (EntityEventProcessor) obj;
		return Objects.equal(other.managedEntityType.getName(), managedEntityType.getName());
	}

}
