/**
 * 
 */
package org.intelligentsia.cqrs.repository;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import org.intelligentsia.cqrs.domain.DomainEvent;
import org.intelligentsia.cqrs.domain.RootEntity;
import org.intelligentsia.cqrs.domain.VersionedIdentifier;
import org.intelligentsia.cqrs.eventstore.EventSink;

/**
 * RootEntitySink implements an EventSink of DomainEvent.
 * 
 * @author JGT
 * 
 */
public class RootEntitySink<T extends RootEntity> implements EventSink<DomainEvent> {

	/**
	 * Expected type.
	 */
	private final Class<T> expectedType;
	/**
	 * identity instance.
	 */
	private final UUID identity;
	/**
	 * Actual type resolved.
	 */
	private Class<? extends T> actualType;
	/**
	 * Actual version.
	 */
	private long actualVersion;
	/**
	 * Root entity instance.
	 */
	private T rootEntity;

	/**
	 * Build a new instance of RootEntitySink.
	 * 
	 * @param expectedType
	 *            expected Type
	 * @param identity
	 *            identity
	 */
	public RootEntitySink(final Class<T> expectedType, final UUID identity) {
		this.expectedType = expectedType;
		this.identity = identity;
	}

	/**
	 * @see org.intelligentsia.cqrs.eventstore.EventSink#setType(java.lang.String)
	 */
	@Override
	public void setType(final String type) {
		try {
			actualType = Class.forName(type).asSubclass(expectedType);
		} catch (final ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Store loaded entity in the next version.
	 * 
	 * @see org.intelligentsia.cqrs.eventstore.EventSink#setVersion(long)
	 */
	@Override
	public void setVersion(final long version) {
		actualVersion = version + 1;
	}

	/**
	 * @see org.intelligentsia.cqrs.eventstore.EventSink#setTimestamp(long)
	 */
	@Override
	public void setTimestamp(final long timestamp) {
		// unused
	}

	/**
	 * @see org.intelligentsia.cqrs.eventstore.EventSink#setEvents(java.lang.Iterable)
	 */
	@Override
	public void setEvents(final Iterable<? extends DomainEvent> events) {
		instantiateRootEntity();
		rootEntity.loadFromHistory(events);
	}

	/**
	 * @return root entity instance.
	 */
	public T getRootEntity() {
		return rootEntity;
	}

	/**
	 * instantiate a RootEntity with type specified by calling
	 * {@link #setType(String)}.
	 * 
	 * @throws RuntimeException
	 *             if an error occurs.
	 */
	private void instantiateRootEntity() throws RuntimeException {
		try {
			final Constructor<? extends T> constructor = actualType.getConstructor(VersionedIdentifier.class);
			rootEntity = constructor.newInstance(VersionedIdentifier.forSpecificVersion(identity, actualVersion));
		} catch (final SecurityException e) {
			throw new RuntimeException(e);
		} catch (final NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (final IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (final InstantiationException e) {
			throw new RuntimeException(e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (final InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

}
