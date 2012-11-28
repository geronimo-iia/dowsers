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
package com.intelligentsia.dowsers.entity.view.processor;

import java.util.Arrays;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.manager.EntityManager;

/**
 * AbstractProcessorBuilder implements abstract Builder on {@link Processor}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public abstract class AbstractProcessorBuilder<T> {

	protected Processor processor;

	protected EntityManager entityManager;

	/**
	 * Build a new instance of AbstractProcessorBuilder.
	 * 
	 * @param alias
	 *            alias used to qualify entity source.
	 */
	public AbstractProcessorBuilder(final Class<? extends Entity> entityType, final String alias) {
		processor = new Source(entityType, alias);
	}

	/**
	 * Build a new instance of AbstractProcessorBuilder.
	 * 
	 * @param alias
	 *            alias used to qualify entity source.
	 */
	public AbstractProcessorBuilder(final Class<? extends Entity> entityType, final String alias, final ImmutableSet<String> attributeNames) {
		processor = new Source(entityType, alias, attributeNames);
	}

	/**
	 * Build a new instance of AbstractProcessorBuilder.
	 * 
	 * @param alias
	 *            alias used to qualify entity source.
	 */
	public AbstractProcessorBuilder(final Class<? extends Entity> entityType, final String alias, final String... names) {
		final ImmutableSet.Builder<String> attributeNames = ImmutableSet.builder();
		attributeNames.addAll(Arrays.asList(names));
		processor = new Source(entityType, alias, attributeNames.build());
	}

	public AbstractProcessorBuilder<T> projection(final ImmutableSet<String> attributeNames) throws NullPointerException, IllegalStateException {
		processor = new Projection(processor, attributeNames);
		return this;
	}

	public AbstractProcessorBuilder<T> projection(final String... names) throws NullPointerException, IllegalStateException {
		final ImmutableSet.Builder<String> attributeNames = ImmutableSet.builder();
		attributeNames.addAll(Arrays.asList(names));
		return projection(attributeNames.build());
	}

	public AbstractProcessorBuilder<T> filter(final Predicate<Item> predicate) throws NullPointerException {
		processor = new Filter(processor, predicate);
		return this;
	}

	public AbstractProcessorBuilder<T> join(final EntityManager entityManager, final String joinAttribute, final Class<? extends Entity> expectedType, final Processor source) throws NullPointerException {
		processor = new Join(processor, entityManager, joinAttribute, expectedType, source);
		return this;
	}

	public AbstractProcessorBuilder<T> join(final String joinAttribute, final Class<? extends Entity> expectedType, final Processor source) throws NullPointerException {
		processor = new Join(processor, entityManager, joinAttribute, expectedType, source);
		return this;
	}

	/**
	 * Set default {@link EntityManager} instance.
	 * 
	 * @param entityManager
	 * @return this {@link ProcessBuilder}.
	 */
	public AbstractProcessorBuilder<T> setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
		return this;
	}

	public  abstract T build();
}
