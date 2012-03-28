/**
 * 
 */
package org.intelligentsia.dowsers.domain;


/**
 * DomainEventProvider declares methods.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface DomainEventProvider {

	/**
	 * @return identifier of root entity
	 */
	public Identifier getIdentifier();

	/**
	 * Returns the current version number of the aggregate, or <code>null</code>
	 * if the aggregate is newly created. This version must reflect the version
	 * number of the aggregate on which changes are applied.
	 * <p/>
	 * Each time the aggregate is <em>modified and stored</em> in a repository,
	 * the version number must be increased by at least 1. This version number
	 * can be used by optimistic locking strategies and detection of conflicting
	 * concurrent modification.
	 * <p/>
	 * Typically the sequence number of the last committed event on this
	 * aggregate is used as version number.
	 * 
	 * @return the current version number of this aggregate, or
	 *         <code>null</code> if no events were ever committed
	 */
	public long getVersion();

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
	 * Returns an Iterable<DomainEvent> to the events in the aggregate that have
	 * been raised since creation or the last commit.
	 * 
	 * @return an iterable instance of of uncommitted changes.
	 */
	public Iterable<DomainEvent> getUncommittedChanges();

	/**
	 * @return the number of uncommitted changes currently available in the
	 *         aggregate.
	 */
	public int getUncommittedChangesCount();

	/**
	 * Mark all changes as committed: Clears the events currently marked as
	 * "uncommitted".
	 */
	public void markChangesCommitted();
}
