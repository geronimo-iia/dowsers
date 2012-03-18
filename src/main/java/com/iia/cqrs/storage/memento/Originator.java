/**
 * 
 */
package com.iia.cqrs.storage.memento;

/**
 * Originator: the object that knows how to save itself.
 * 
 * The Originator interface is for the snapshot functionality which is an optimization technique for speeding up loading
 * aggregate roots from the Event Store.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
     
 */
public interface Originator {

	public Memento createMemento();

	public void setMemento(Memento memento);
}
