/**
 * 
 */
package org.intelligentsia.dowsers.domain.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.intelligentsia.dowsers.domain.DomainEntity;
import org.intelligentsia.dowsers.domain.Entity;
import org.intelligentsia.dowsers.domain.EntityRegistry;

import com.google.common.base.Preconditions;

/**
 * EntityList.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityList<T extends Entity> implements Iterable<T> {

	private final EntityRegistry registerEntity;

	private final List<T> entities;

	/**
	 * 
	 * Build a new instance of <code>EntityList</code>.
	 * 
	 * @param domainEntity
	 * @throws NullPointerException
	 *             if domainEntity is null
	 */
	public EntityList(final DomainEntity domainEntity) throws NullPointerException {
		this(domainEntity, new ArrayList<T>());
	}

	/**
	 * 
	 * Build a new instance of <code>EntityList</code>.
	 * 
	 * @param domainEntity
	 * @param entities
	 * @throws NullPointerException
	 *             if domainEntity or entities is null
	 */
	public EntityList(final DomainEntity domainEntity, final List<T> entities) throws NullPointerException {
		super();
		this.registerEntity = Preconditions.checkNotNull(domainEntity);
		this.entities = Preconditions.checkNotNull(entities);
	}

	public void add(final T entity) {
		registerEntity.register(entity);
		entities.add(entity);
	}

	public boolean contains(T entity) {
		return entities.contains(entity);
	}

	/**
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return entities.iterator();
	}

	public boolean contains(final T entity) {
		return entities.contains(entity);
	}
}
