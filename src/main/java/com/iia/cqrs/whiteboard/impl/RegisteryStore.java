/**
 * 
 */
package com.iia.cqrs.whiteboard.impl;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import com.iia.cqrs.whiteboard.Registry;

/**
 *
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class RegisteryStore implements Registry{

	private Multimap<String, WeakReference<?>> map;
	
 
	public RegisteryStore() {
		map = new HashMap<String, WeakReference<?>>();
	}
	
 
	@Override
	public <T> void register(Class<T> type, T instance) {
		map.put(type.getName(), new WeakReference<T>(instance));
	}

 
	@Override
	public <T> Iterable<T> find(Class<T> type) {
		
		return null;
	}

	
	
}
