/**
 * 
 */
package org.intelligentsia.dowsers.memento;

import org.intelligentsia.dowsers.domain.DomainEntity;

/**
 * Originator: the object that knows how to save itself.
 * 
 * The Originator interface is for the snapshot functionality which is an
 * optimization technique for speeding up loading aggregate roots from the Event
 * Store.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public interface Originator {

	public <T extends DomainEntity> Memento createMemento(T entity);

	public <T extends DomainEntity> void setMemento(T entity, Memento memento);
	
	public <T extends DomainEntity> boolean support(Class<T> entityType);
}
