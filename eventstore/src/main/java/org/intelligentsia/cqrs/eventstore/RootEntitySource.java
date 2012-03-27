/**
 * 
 */
package org.intelligentsia.cqrs.eventstore;

import java.util.List;

import org.intelligentsia.cqrs.domain.DomainEvent;
import org.intelligentsia.cqrs.domain.RootEntity;
import org.intelligentsia.cqrs.eventstore.EventSource;

import com.google.common.base.Preconditions;

/**
 * RootEntitySource implements EventSource of DomainEvent.
 * 
 * @author JGT
 * 
 */
public class RootEntitySource implements EventSource<DomainEvent> {

	/**
	 * root entity source.
	 */
	private final RootEntity rootEntity;

	/**
	 * Build a new instance of RootEntitySource with specified root entity.
	 * 
	 * @param rootEntity
	 *            root Entity
	 * @throws NullPointerException
	 *             if {@link RootEntity} is null
	 */
	public RootEntitySource(final RootEntity rootEntity) throws NullPointerException {
		this.rootEntity = Preconditions.checkNotNull(rootEntity);
	}

	/**
	 * @see org.intelligentsia.cqrs.eventstore.EventSource#getType()
	 */
	@Override
	public String getType() {
		return rootEntity.getClass().getName();
	}

	/**
	 * @see org.intelligentsia.cqrs.eventstore.EventSource#getVersion()
	 */
	@Override
	public long getVersion() {
		return rootEntity.getVersionedIdentifier().getVersion();
	}

	/**
	 * Implemented as call on {@link System#currentTimeMillis()}.
	 * 
	 * @see org.intelligentsia.cqrs.eventstore.EventSource#getTimestamp()
	 */
	@Override
	public long getTimestamp() {
		return System.currentTimeMillis();
	}

	/**
	 * @see org.intelligentsia.cqrs.eventstore.EventSource#getEvents()
	 */
	@Override
	public List<? extends DomainEvent> getEvents() {
		return rootEntity.getUncommittedChanges();
	}

}
