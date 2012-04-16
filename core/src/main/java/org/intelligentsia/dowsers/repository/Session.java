/**
 *        Licensed to the Apache Software Foundation (ASF) under one
 *        or more contributor license agreements.  See the NOTICE file
 *        distributed with this work for additional information
 *        regarding copyright ownership.  The ASF licenses this file
 *        to you under the Apache License, Version 2.0 (the
 *        "License"); you may not use this file except in compliance
 *        with the License.  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing,
 *        software distributed under the License is distributed on an
 *        "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *        KIND, either express or implied.  See the License for the
 *        specific language governing permissions and limitations
 *        under the License.
 *
 */
package org.intelligentsia.dowsers.repository;

import java.util.HashMap;
import java.util.Map;

import org.intelligentsia.dowsers.container.DomainEntityFactory;
import org.intelligentsia.dowsers.domain.AbstractDomainRepository;
import org.intelligentsia.dowsers.domain.ConcurrencyException;
import org.intelligentsia.dowsers.domain.DomainEntity;
import org.intelligentsia.dowsers.domain.DomainEntityNotFoundException;
import org.intelligentsia.dowsers.domain.DomainRepository;
import org.intelligentsia.dowsers.event.DomainAggregate;

/**
 * Session.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Session extends AbstractDomainRepository {

	private final DomainRepository domainRepository;

	private final Map<String, DomainEntity> entities = new HashMap<String, DomainEntity>();

	/**
	 * Build a new instance of Session.
	 * 
	 * @param domainEntityFactory
	 * @param domainRepository
	 * @throws NullPointerException
	 */
	public Session(DomainEntityFactory domainEntityFactory, DomainRepository domainRepository) throws NullPointerException {
		super(domainEntityFactory);
		this.domainRepository = domainRepository;
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.DomainRepository#find(java.lang.Class,
	 *      java.lang.String)
	 */
	@Override
	public <T extends DomainEntity> T find(Class<T> expectedType, String identity) throws DomainEntityNotFoundException, NullPointerException {
		return domainRepository.find(expectedType, identity);
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.DomainRepository#store(org.intelligentsia.dowsers.domain.DomainEntity)
	 */
	@Override
	public <T extends DomainEntity> void store(T domainEntity) throws NullPointerException, ConcurrencyException {
		if (((DomainAggregate) getAggregate(domainEntity)).hasUncommittedChanges()) {
			addToSession(domainEntity);
		}
	}

	/**
	 * Add specified entity instance to current session.
	 * 
	 * @param entity
	 *            instance to add
	 * @throws ConcurrencyException
	 *             if different version of the specified entity is ever in this
	 *             session
	 */
	private <T extends DomainEntity> void addToSession(final T entity) throws ConcurrencyException {
		final DomainEntity previous = entities.put(entity.getIdentity(), entity);
		if (previous != null && previous != entity) {
			throw new ConcurrencyException(entity.getIdentity(), "multiple instances with same id " + entity.getIdentity());
		}
	}

	/**
	 * Commit all session change.
	 */
	public void commitChanges() throws NullPointerException, ConcurrencyException {
		for (final DomainEntity entity : entities.values()) {
			DomainAggregate aggregate = getAggregate(entity);
			if (aggregate.hasUncommittedChanges()) {
				save(entity);
			}
		}
		// should be done just before transaction commit...
		entities.clear();
	}

	/**
	 * @param entity
	 */
	private void save(DomainEntity entity) throws NullPointerException, ConcurrencyException {
		domainRepository.store(entity);
	}

}
