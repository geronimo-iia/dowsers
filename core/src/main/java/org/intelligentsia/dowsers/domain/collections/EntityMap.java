/**
 * 
 */
package org.intelligentsia.dowsers.domain.collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.intelligentsia.dowsers.domain.LocalDomainEntity;
import org.intelligentsia.dowsers.domain.LocalDomainEntityRegistry;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityMap<K, T extends LocalDomainEntity> implements Iterable<T> {

	private final LocalDomainEntityRegistry registerEntity;

	private final Map<K, T> entities;

	public EntityMap(final LocalDomainEntityRegistry domainEntity) {
		this(domainEntity, new HashMap<K, T>());
	}

	public EntityMap(final LocalDomainEntityRegistry domainEntity, final Map<K, T> entities) {
		super();
		this.registerEntity = domainEntity;
		this.entities = entities;
	}

	public void put(final K key, final T entity) {
		registerEntity.register(entity);
		entities.put(key, entity);
	}

	public boolean contains(final K key) {
		return entities.containsKey(key);
	}

	/**
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return entities.values().iterator();
	}
}
