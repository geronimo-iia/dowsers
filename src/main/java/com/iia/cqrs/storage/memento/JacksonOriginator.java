/**
 * 
 */
package com.iia.cqrs.storage.memento;

/**
 * JacksonOriginator. 
 *
 * @author jgt
 *
 */
public class JacksonOriginator implements Originator {

	/**
	 * @see com.iia.cqrs.storage.memento.Originator#createMemento()
	 */
	@Override
	public Memento createMemento() {
		return null;
	}

	/**
	 * @see com.iia.cqrs.storage.memento.Originator#setMemento(com.iia.cqrs.storage.memento.Memento)
	 */
	@Override
	public void setMemento(Memento memento) {
	}

}
