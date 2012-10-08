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
package org.intelligentsia.dowsers.model;

import org.intelligentsia.dowsers.core.IdentifierFactoryProvider;
import org.intelligentsia.dowsers.model.factory.EntityFactory;
import org.intelligentsia.dowsers.model.resolver.EntityNameResolver;

import com.google.common.base.Preconditions;

/**
 * EntityManagerUnit.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityManagerUnit implements EntityManager {

	/**
	 * {@link EntityFactory} instance.
	 */
	private final EntityFactory entityFactory;

	/**
	 * {@link EntityNameResolver} instance.
	 */
	private EntityNameResolver entityNameResolver;

	/**
	 * Build a new instance of EntityManagerUnit.java.
	 * 
	 * @param entityFactory
	 * @throws NullPointerException
	 */
	public EntityManagerUnit(EntityFactory entityFactory) throws NullPointerException {
		super();
		this.entityFactory = Preconditions.checkNotNull(entityFactory);
	}

	@Override
	public <T> T newInstance(Class<T> interfaceName) throws NullPointerException {
		return newInstance(interfaceName, IdentifierFactoryProvider.generateNewIdentifier());
	}

	@Override
	public <T> T newInstance(Class<T> interfaceName, String identity) throws NullPointerException {
		return entityFactory.newInstance(interfaceName, identity);
	}

}
