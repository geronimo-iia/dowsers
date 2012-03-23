/**
 * 
 */
package com.iia.cqrs.collections;

import java.util.Iterator;
import java.util.List;

import com.iia.cqrs.domain.Entity;
import com.iia.cqrs.domain.RegisterEntity;

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
