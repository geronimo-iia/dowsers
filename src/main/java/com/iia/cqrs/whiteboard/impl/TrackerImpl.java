/**
 * 
 */
package com.iia.cqrs.whiteboard.impl;

import java.util.ArrayList;
import java.util.Iterator;

import com.iia.cqrs.whiteboard.Tracker;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class TrackerImpl<T> implements Tracker<T> {

	private final ArrayList<T> items;

	/**
	 * Build a new instance of <code>TrackerImpl</code>
	 * 
	 * @param items
	 */
	public TrackerImpl(ArrayList<T> items) {
		super();
		this.items = items;
	}

	/**
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return items.iterator();
	}

}
