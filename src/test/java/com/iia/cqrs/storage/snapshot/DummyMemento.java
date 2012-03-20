/**
 * 
 */
package com.iia.cqrs.storage.snapshot;

import com.iia.cqrs.storage.Memento;

/**
 * DummyMemento.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class DummyMemento implements Memento {

	private static final long serialVersionUID = 3514645948366096698L;
	private String value;

	/**
	 * Build a new instance of DummyMemento.
	 */
	public DummyMemento() {
		super();
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
