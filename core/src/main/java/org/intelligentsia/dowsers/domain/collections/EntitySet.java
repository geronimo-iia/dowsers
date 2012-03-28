/**
 * 
 */
package org.intelligentsia.dowsers.domain.collections;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.intelligentsia.dowsers.domain.LocalDomainEntity;
import org.intelligentsia.dowsers.domain.LocalDomainEntityRegistry;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntitySet<T extends LocalDomainEntity> implements Iterable<T> {
	private final LocalDomainEntityRegistry registerEntity;

	private final Set<T> entities;

	public EntitySet(final LocalDomainEntityRegistry registry) {
		this(registry, new HashSet<T>());
	}

	/**
	 * Build a new instance of <code>EntitySet</code>
	 * 
	 * @param domainEntity
	 * @param entities
	 */
	public EntitySet(final LocalDomainEntityRegistry registry, final Set<T> entities) {
		super();
		this.registerEntity = registry;
		this.entities = entities;
	}

	public void add(final T entity) {
		registerEntity.register(entity);
		entities.add(entity);
	}

	public boolean contains(final T entity) {
		return entities.contains(entity);
	}

	/**
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return entities.iterator();
	}
}
