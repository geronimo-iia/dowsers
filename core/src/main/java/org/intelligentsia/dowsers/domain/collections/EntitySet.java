/**
 * 
 */
package org.intelligentsia.dowsers.domain.collections;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.intelligentsia.dowsers.domain.DomainEntity;
import org.intelligentsia.dowsers.domain.Entity;
import org.intelligentsia.dowsers.domain.RegisterEntity;


/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntitySet<T extends Entity> implements Iterable<T> {
	private final RegisterEntity registerEntity;

	private final Set<T> entities;

	public EntitySet(DomainEntity domainEntity) {
		this(domainEntity, new HashSet<T>());
	}

	/**
	 * Build a new instance of <code>EntitySet</code>
	 * 
	 * @param domainEntity
	 * @param entities
	 */
	public EntitySet(DomainEntity domainEntity, Set<T> entities) {
		super();
		this.registerEntity = domainEntity;
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
