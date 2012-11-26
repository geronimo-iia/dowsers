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

import java.util.Iterator;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.view.Processor;

/**
 * Projection.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Projection extends ProcessorUnit {

	private final ImmutableSet<String> attributeNames;

	/**
	 * Build a new instance of Projection.
	 * 
	 * @param processor
	 *            {@link Processor} instance
	 * @param attributeNames
	 *            set of attribute name
	 * @throws NullPointerException
	 *             if one of parameters is null
	 */
	public Projection(final Processor processor, final ImmutableSet<String> attributeNames) throws NullPointerException {
		super(processor);
		this.attributeNames = Preconditions.checkNotNull(attributeNames);
	}

	@Override
	public Item apply(final Entity input) {
		final Item item = super.apply(input);
		if (item != null) {
			// must we do a local copy of this set ?
			final Iterator<String> iterator = Sets.difference(item.keySet(), attributeNames).iterator();
			while (iterator.hasNext()) {
				item.remove(iterator.next());
			}
		}
		return item;
	}

}
