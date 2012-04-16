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
package org.intelligentsia.dowsers.domain.collection;

import java.util.Collection;

import org.intelligentsia.dowsers.domain.LocalDomainEntity;
import org.intelligentsia.dowsers.util.Registry;

import com.google.common.base.Preconditions;

/**
 * AbstractLocalDomainEntityCollection.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public abstract class AbstractLocalDomainEntityCollection<T extends LocalDomainEntity> {

	private final Registry<LocalDomainEntity> registerEntity;

	/**
	 * Build a new instance of AbstractLocalDomainEntityCollection.
	 * 
	 * @param registerEntity
	 * @throws NullPointerException
	 *             if registerEntity is null
	 */
	public AbstractLocalDomainEntityCollection(Registry<LocalDomainEntity> registerEntity) throws NullPointerException {
		super();
		this.registerEntity = Preconditions.checkNotNull(registerEntity);
	}

	protected void register(T entity) {
		registerEntity.register(entity);
	}

	protected void register(Collection<? extends T> collection) {
		for (T entity : collection) {
			registerEntity.register(entity);
		}
	}
}
