/**
 * 
 */
package com.iia.cqrs.storage.memento;

import java.nio.ByteBuffer;

/**
 * ByteArrayMemento.
 * 
 * @author jgt
 * 
 */
public class ByteArrayMemento implements Memento {

	private ByteBuffer byteBuffer;

	/**
	 * Build a new instance of ByteArrayMemento.
	 */
	public ByteArrayMemento() {
		super();
 
	}
	
	
}
