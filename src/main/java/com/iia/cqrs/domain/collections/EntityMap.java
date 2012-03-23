/**
 * 
 */
package com.iia.cqrs.domain.collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.iia.cqrs.domain.DomainEntity;
import com.iia.cqrs.domain.Entity;
import com.iia.cqrs.domain.RegisterEntity;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityMap<K, T extends Entity> implements Iterable<T> {

	private final RegisterEntity registerEntity;

	private final Map<K, T> entities;

	public EntityMap(DomainEntity domainEntity) {
		this(domainEntity, new HashMap<K, T>());
	}

	public EntityMap(DomainEntity domainEntity, Map<K, T> entities) {
		super();
		this.registerEntity = domainEntity;
		this.entities = entities;
	}

	public void put(K key, T entity) {
		registerEntity.register(entity);
		entities.put(key, entity);
	}

	/**
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return entities.values().iterator();
	}
}
