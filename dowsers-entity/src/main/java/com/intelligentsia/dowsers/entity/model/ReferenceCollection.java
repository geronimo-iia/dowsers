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
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.reference.References;

/**
 * ReferenceCollection.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class ReferenceCollection implements Iterable<Reference>, Serializable {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -6158983976858817330L;

	/**
	 * {@link Collection} of {@link Reference} instance.
	 */
	@JsonProperty
	private final Collection<Reference> references;

	/**
	 * Build a new instance of ReferenceCollection. with a {@link LinkedList}
	 * instance.
	 */
	public ReferenceCollection() {
		this(new LinkedList<Reference>());
	}

	/**
	 * Build a new instance of ReferenceCollection.java.
	 * 
	 * @param references
	 * @throws NullPointerException
	 *             if references is null
	 */
	public ReferenceCollection(final Collection<Reference> references) throws NullPointerException {
		super();
		this.references = Preconditions.checkNotNull(references);
	}

	/**
	 * Add a {@link References} on any object which is an entity Representation
	 * 
	 * @param any
	 * @return this instance
	 * @throws NullPointerException
	 *             if any is null
	 * @throws {@link IllegalArgumentException} if any is not an entity
	 *         representation
	 */
	public ReferenceCollection add(final Object any) throws NullPointerException, IllegalArgumentException {
		this.references.add(References.identify(Preconditions.checkNotNull(any)));
		return this;
	}

	@Override
	public Iterator<Reference> iterator() {
		return references.iterator();
	}

	@JsonIgnore
	public boolean isEmpty() {
		return references.isEmpty();
	}

	public Collection<Reference> entities() {
		return references;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((references == null) ? 0 : references.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ReferenceCollection other = (ReferenceCollection) obj;
		if (references == null) {
			if (other.references != null) {
				return false;
			}
		} else if (!references.equals(other.references)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ReferenceCollection [references=" + references + "]";
	}

}
