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
package org.intelligentsia.dowsers.domain;

import org.intelligentsia.dowsers.core.IdentifierFactoryProvider;

/**
 * LocalDomainEntity is a domain entity which can be used only with a domain
 * entity (also named root entity).
 * 
 * @author jgt
 * 
 */
public class LocalDomainEntity extends Entity {

	/**
	 * Build a new instance of LocalDomainEntity.
	 */
	public LocalDomainEntity() {
		super(IdentifierFactoryProvider.generateNewIdentifier());
	}

	/**
	 * Build a new instance of LocalDomainEntity.
	 * 
	 * @param identity
	 * @throws NullPointerException
	 *             if identity is null
	 */
	public LocalDomainEntity(final String identity) throws NullPointerException {
		super(identity);
	}

	/**
	 * On register method call back.
	 */
	protected void onRegister(final DomainEntity domainEntity) {
		// do nothing
	}

}
