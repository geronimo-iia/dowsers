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

	Identifier getIdentifier();

	void loadFromHistory(Iterable<DomainEvent> domainEvents);

	void updateVersion(int version);

	Iterable<DomainEvent> uncommitedChanges();

	void clear();
}
