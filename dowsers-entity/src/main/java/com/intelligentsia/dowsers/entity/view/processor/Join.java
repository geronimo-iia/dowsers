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

import com.google.common.base.Preconditions;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.manager.EntityManager;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.store.EntityNotFoundException;

/**
 * Join an Item with another {@link Entity}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class Join extends ProcessorUnit {

	private final EntityManager entityManager;

	private final String joinAttribute;

	private final Class<? extends Entity> expectedType;

	private final Processor source;

	/**
	 * Build a new instance of Join.
	 * 
	 * @param processor
	 *            {@link Processor} instance
	 * @param entityManager
	 *            {@link EntityManager} instance
	 * @param joinAttribute
	 *            join attribute ('take care of alias')
	 * @param expectedType
	 *            expected type of joined {@link Entity}
	 * @param source
	 *            {@link Source} instance which will be apply on joined entity
	 * @throws NullPointerException
	 *             if one of parameters is null
	 */
	public Join(final Processor processor, final EntityManager entityManager, final String joinAttribute, final Class<? extends Entity> expectedType, final Processor source) throws NullPointerException {
		super(processor);
		this.entityManager = Preconditions.checkNotNull(entityManager);
		this.joinAttribute = Preconditions.checkNotNull(joinAttribute);
		this.expectedType = Preconditions.checkNotNull(expectedType);
		this.source = Preconditions.checkNotNull(source);
	}

	@Override
	public Item apply(final Entity input) {
		final Item item = super.apply(input);
		if (item != null) {
			final Reference reference = (Reference) item.get(joinAttribute);
			if (reference != null) {
				try {
					final Entity entity = entityManager.find(expectedType, reference);
					if (entity != null) {
						final Item item2 = source.apply(entity);
						item.putAll(item2.getAttributes());
					}
				} catch (final EntityNotFoundException entityNotFoundException) {

				}
			}
		}
		return item;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("(join ").append(super.toString()).append(" ").append(joinAttribute);
		return builder.append(" ").append(source.toString()).append(")").toString();
	}
}
