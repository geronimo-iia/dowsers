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
 * ProcessorBuilder implements Builder on {@link Processor}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public final class ProcessorBuilder {

	private Processor processor;

	private EntityManager entityManager;

	/**
	 * @return a {@link ProcessBuilder}.
	 */
	public static ProcessorBuilder builder(final Class<? extends Entity> entityType, final String alias) {
		return new ProcessorBuilder(entityType, alias);
	}

	/**
	 * @return a {@link ProcessBuilder}.
	 */
	public static ProcessorBuilder builder(final Class<? extends Entity> entityType, final String alias, final ImmutableSet<String> attributeNames) {
		return new ProcessorBuilder(entityType, alias, attributeNames);
	}

	/**
	 * @return a {@link ProcessBuilder}.
	 */
	public static ProcessorBuilder builder(final Class<? extends Entity> entityType, final String alias, final String... names) {
		return new ProcessorBuilder(entityType, alias, names);
	}

	/**
	 * Build a new instance of ProcessorBuilder.
	 * 
	 * @param alias
	 *            alias used to qualify entity source.
	 */
	public ProcessorBuilder(final Class<? extends Entity> entityType, final String alias) {
		processor = new Source(entityType, alias);
	}

	/**
	 * Build a new instance of ProcessorBuilder.
	 * 
	 * @param alias
	 *            alias used to qualify entity source.
	 */
	public ProcessorBuilder(final Class<? extends Entity> entityType, final String alias, final ImmutableSet<String> attributeNames) {
		processor = new Source(entityType, alias, attributeNames);
	}

	/**
	 * Build a new instance of ProcessorBuilder.
	 * 
	 * @param alias
	 *            alias used to qualify entity source.
	 */
	public ProcessorBuilder(final Class<? extends Entity> entityType, final String alias, final String... names) {
		final ImmutableSet.Builder<String> attributeNames = ImmutableSet.builder();
		attributeNames.addAll(Arrays.asList(names));
		processor = new Source(entityType, alias, attributeNames.build());
	}

	public Processor build() {
		return processor;
	}

	public ProcessorBuilder projection(final ImmutableSet<String> attributeNames) throws NullPointerException, IllegalStateException {
		processor = new Projection(processor, attributeNames);
		return this;
	}

	public ProcessorBuilder projection(final String... names) throws NullPointerException, IllegalStateException {
		final ImmutableSet.Builder<String> attributeNames = ImmutableSet.builder();
		attributeNames.addAll(Arrays.asList(names));
		return projection(attributeNames.build());
	}

	public ProcessorBuilder filter(final Predicate<Item> predicate) throws NullPointerException {
		processor = new Filter(processor, predicate);
		return this;
	}

	public ProcessorBuilder join(final EntityManager entityManager, final String joinAttribute, final Class<? extends Entity> expectedType, final Processor source) throws NullPointerException {
		processor = new Join(processor, entityManager, joinAttribute, expectedType, source);
		return this;
	}

	public ProcessorBuilder join(final String joinAttribute, final Class<? extends Entity> expectedType, final Processor source) throws NullPointerException {
		processor = new Join(processor, entityManager, joinAttribute, expectedType, source);
		return this;
	}

	/**
	 * Set default {@link EntityManager} instance.
	 * 
	 * @param entityManager
	 * @return this {@link ProcessBuilder}.
	 */
	public ProcessorBuilder setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
		return this;
	}
}
