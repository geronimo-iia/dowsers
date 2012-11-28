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
package com.intelligentsia.dowsers.entity.view;

import java.util.List;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.view.processor.Item;
import com.intelligentsia.dowsers.entity.view.processor.Processor;

/**
 * View is definition of a specific view on an aggregate of entities and not a
 * way to iterate over each items.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public final class View {

	private final String name;

	private final Processor processor;

	private final ImmutableList<Reference> entities;

	private final ViewStore viewStore;

	/**
	 * Build a new instance of View.java.
	 * 
	 * @param name
	 * @param processor
	 * @param entities
	 * @throws NullPointerException
	 *             if one of parameters is null
	 * @throws IllegalArgumentException
	 *             if entities is empty
	 */
	public View(final String name, final Processor processor, final ImmutableList<Reference> entities, final ViewStore viewStore) throws NullPointerException, IllegalArgumentException {
		super();
		this.name = Preconditions.checkNotNull(name);
		this.processor = Preconditions.checkNotNull(processor);
		this.entities = Preconditions.checkNotNull(entities);
		Preconditions.checkArgument(!entities.isEmpty());
		this.viewStore = Preconditions.checkNotNull(viewStore);
	}

	/**
	 * Compute view for specified entity.
	 * 
	 * @param entity
	 */
	public void compute(final Entity entity) {
		// build item
		final Item item = processor.apply(entity);
		// update store
		viewStore.update(entity.identity(), item);
	}

	/**
	 * Remove specified entry.
	 * 
	 * @param identity
	 */
	public void remove(final Reference identity) {
		viewStore.update(identity, null);
	}

	/**
	 * @return view name
	 */
	public String name() {
		return name;
	}

	/**
	 * @return {@link Processor} instance to build each items.
	 */
	public Processor processor() {
		return processor;
	}

	/**
	 * @return {@link ViewStore} instance.
	 */
	public ViewStore viewStore() {
		return viewStore;
	}

	/**
	 * @return a {@link List} of entities class {@link Reference} which this
	 *         view depends. First reference, is the origin of this view.
	 */
	public ImmutableList<Reference> entities() {
		return entities;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name);
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
		final View other = (View) obj;
		return Objects.equal(other.name(), name());
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(getClass()).add("name", name).add("processor", processor).add("entities", entities).toString();
	}

}
