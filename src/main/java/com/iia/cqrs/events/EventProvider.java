/**
 * 
 */
package com.iia.cqrs.events;

import com.iia.cqrs.Identifier;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface EventProvider {

	/**
	 * @return identifier of entity (must have an Id and a Version).
	 */
	public Identifier getIdentifier();

	/**
	 * 
	 * @param domainEvents
	 */
	public void loadFromHistory(Iterable<DomainEvent> domainEvents);

	public void updateVersion(int version);

	public Iterable<DomainEvent> uncommitedChanges();

	public void clear();
}
