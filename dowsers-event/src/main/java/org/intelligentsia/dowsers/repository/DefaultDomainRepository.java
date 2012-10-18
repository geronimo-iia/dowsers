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

import org.intelligentsia.dowsers.container.DomainEntityFactory;
import org.intelligentsia.dowsers.domain.AbstractDomainRepository;
import org.intelligentsia.dowsers.domain.ConcurrencyException;
import org.intelligentsia.dowsers.domain.DomainEntity;
import org.intelligentsia.dowsers.domain.DomainEntityNotFoundException;

/**
 * DefaultRepository implements DomainRepository using session.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DefaultDomainRepository extends AbstractDomainRepository {

	/**
	 * thread local variable session.
	 */
	private final ThreadLocal<Session> sessions = new ThreadLocal<Session>() {
		@Override
		protected Session initialValue() {
			return new Session(domainEntityFactory, null);
		}
	};

	/**
	 * Build a new instance of DefaultDomainRepository.
	 * 
	 * @param domainEntityFactory
	 * @throws NullPointerException
	 */
	public DefaultDomainRepository(final DomainEntityFactory domainEntityFactory) throws NullPointerException {
		super(domainEntityFactory);
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.DomainRepository#find(java.lang.Class,
	 *      java.lang.String)
	 */
	@Override
	public <T extends DomainEntity> T find(final Class<T> expectedType, final String identity) throws DomainEntityNotFoundException, NullPointerException {
		return sessions.get().find(expectedType, identity);
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.DomainRepository#store(org.intelligentsia.dowsers.domain.DomainEntity)
	 */
	@Override
	public <T extends DomainEntity> void store(final T domainEntity) throws NullPointerException, ConcurrencyException {
		sessions.get().store(domainEntity);
	}

}
