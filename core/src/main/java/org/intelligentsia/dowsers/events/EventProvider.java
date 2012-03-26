/**
 * 
 */
package org.intelligentsia.dowsers.events;

import org.intelligentsia.dowsers.domain.DomainEvent;
import org.intelligentsia.dowsers.domain.Identifier;

/**
 * EventProvider declares methods.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface EventProvider {

	/**
	 * @return identifier of root entity
	 */
	public Identifier getIdentifier();

	/**
	 * Loading historical domain events.It is basically apply events of the
	 * given aggregate.
	 * 
	 * @param history
	 *            list of DomainEvent
	 * 
	 * @throws IllegalStateException
	 *             if error occurs when load history
	 */
	public void loadFromHistory(final Iterable<? extends DomainEvent> history) throws IllegalStateException;

	/**
	 * Increment version of this root entity.
	 */
	public void incrementVersion();

	/**
	 * @return an iterable instance of of uncommitted changes.
	 */
	public Iterable<DomainEvent> getUncommittedChanges();

	/**
	 * Mark all changes as committed.
	 */
	public void markChangesCommitted();
}
