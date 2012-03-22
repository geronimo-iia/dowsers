/**
 * 
 */
package com.iia.cqrs;

import java.util.Iterator;
import java.util.List;

/**
 * EntityList.
 * 
 * @author jgt
 * 
 */
public class EntityList<T extends Entity> implements Iterable<T> {

	private final RegisterEntity registerEntity;
	private final List<T> entities;

	public EntityList(RegisterEntity registerEntity, List<T> entities) {
		super();
		this.registerEntity = registerEntity;
		this.entities = entities;
	}

	public void add(T entity) {
		registerEntity.register(entity);
		entities.add(entity);
	}

	/**
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return entities.iterator();
	}

}
