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
package com.intelligentsia.dowsers.entity.model;

import java.io.Serializable;
import java.net.URI;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.intelligentsia.dowsers.entity.Reference;

/**
 * EntityCollection.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class EntityCollection implements Iterable<URI>, Serializable {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -6158983976858817330L;

	/**
	 * {@link Collection} of {@link URI} instance.
	 */
	@JsonProperty
	private final Collection<URI> entities;

	/**
	 * Build a new instance of EntityCollection. with a {@link LinkedList}
	 * instance.
	 */
	public EntityCollection() {
		this(new LinkedList<URI>());
	}

	/**
	 * Build a new instance of EntityCollection.java.
	 * 
	 * @param entities
	 * @throws NullPointerException
	 *             if entities is null
	 */
	public EntityCollection(final Collection<URI> entities) throws NullPointerException {
		super();
		this.entities = Preconditions.checkNotNull(entities);
	}

	/**
	 * Add a {@link Reference} on any object which is an entity Representation
	 * 
	 * @param any
	 * @return this instance
	 * @throws NullPointerException
	 *             if any is null
	 * @throws {@link IllegalArgumentException} if any is not an entity
	 *         representation
	 */
	public EntityCollection add(Object any) throws NullPointerException, IllegalArgumentException {
		this.entities.add(Reference.newEntityReference(Preconditions.checkNotNull(any)));
		return this;
	}

	@Override
	public Iterator<URI> iterator() {
		return entities.iterator();
	}

	@JsonIgnore
	public boolean isEmpty() {
		return entities.isEmpty();
	}

	public Collection<URI> entities() {
		return entities;
	}

}
