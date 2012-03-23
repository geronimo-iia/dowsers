/**
 * 
 */
package com.iia.cqrs.domain.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;
import com.iia.cqrs.domain.DomainEntity;
import com.iia.cqrs.domain.Entity;
import com.iia.cqrs.domain.RegisterEntity;

/**
 * EntityList.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityList<T extends Entity> implements Iterable<T> {

	private final RegisterEntity registerEntity;

	private final List<T> entities;

	/**
	 * 
	 * Build a new instance of <code>EntityList</code>.
	 * 
	 * @param domainEntity
	 * @throws NullPointerException
	 *             if domainEntity is null
	 */
	public EntityList(DomainEntity domainEntity) throws NullPointerException {
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
	public EntityList(DomainEntity domainEntity, List<T> entities) throws NullPointerException {
		super();
		this.registerEntity = Preconditions.checkNotNull(domainEntity);
		this.entities = Preconditions.checkNotNull(entities);
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
