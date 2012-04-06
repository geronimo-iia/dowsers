/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at
 
         http://www.apache.org/licenses/LICENSE-2.0
 
       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */
package org.intelligentsia.dowsers.domain;

/**
 * DomainRepository interfaces declare methods for retrieving domain objects
 * should delegate to a specialized Repository object such that alternative
 * storage implementations may be easily interchanged.
 * 
 * 
 * Domain is completely write only, so the repository only has to be able to Get
 * an Entity by its Id and it must be able to save it.<br />
 * DomainRepository completely get rid of any impedance mismatch between the
 * domain and the persistence layer.
 * 
 * From "DDD using naked object":
 * 
 * A repository provides a mechanism to acquire references to existing objects.
 * It decouples the rest of the model from the persistence mechanism of such
 * objects.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface DomainRepository {

	/**
	 * Find entity with the specified identity.
	 * 
	 * @param expectedType
	 *            expected type entity
	 * @param identity
	 *            identity what we looking for
	 * @return an entity instance of the expected type and identity
	 * 
	 * @throws DomainEntityNotFoundException
	 *             if no entity with specifed identity and type exists.
	 * @throws NullPointerException
	 *             if expectedType or identity is null
	 */
	public <T extends DomainEntity> T find(Class<T> expectedType, String identity) throws DomainEntityNotFoundException, NullPointerException;

	// /**
	// * Find entity with the specified identity, expecting the version of the
	// * aggregate to be equal to the given expectedVersion.
	// *
	// * @param expectedType
	// * expected type entity
	// * @param identity
	// * identity what we looking for
	// * @param expectedVersion
	// * expected Version value
	// * @return an entity instance of the expected type, identity an version
	// * @throws DomainEntityNotFoundException
	// * if no entity with specifed parameters did not exists.
	// * @throws NullPointerException
	// * if expectedType or identity is null
	// * @throws IllegalStateException
	// * if expectedVersion < 0
	// * @throws ConcurrencyException
	// * if the <code>expectedVersion</code> did not match the
	// * entity's actual version in current uncommited session
	// *
	// */
	// public <T> T findByIdentifier(Class<T> expectedType, UUID identity, long
	// expectedVersion) throws DomainEntityNotFoundException,
	// NullPointerException, IllegalStateException, ConcurrencyException;

	public <T extends DomainEntity> void store(T domainEntity) throws NullPointerException, ConcurrencyException;
	// /**
	// * Add specific entity to the domain repository.
	// *
	// * @param entity
	// * entity to add
	// * @throws NullPointerException
	// * if entity is null
	// */
	// @TODOs({ @TODO("Specify concurrent runtime exception?"),
	// @TODO("Raise something if we add a deleted entity ?") })
	// @Note("acquire references to existing objects ==> ONLY EXISTING ?")
	// public <T> void add(T entity) throws NullPointerException;
	//
	// /**
	// * Remove entity
	// *
	// * @param expectedType
	// * expected type entity
	// * @param identity
	// * identity of entity to remove
	// * @throws NullPointerException
	// * if expectedType or identity is null
	// */
	// @Note("acquire references to existing objects ==> ONLY EXISTING ?")
	// public <T> void remove(Class<T> expectedType, UUID identity) throws
	// NullPointerException;

}
