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
import com.google.common.base.Predicate;
import com.intelligentsia.dowsers.entity.Entity;

/**
 * Filter apple a {@link Predicate} on specified item and let process continue
 * if true.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public final class Filter extends ProcessorUnit {

	private final Predicate<Item> predicate;

	/**
	 * Build a new instance of Filter.
	 * 
	 * @param processor
	 *            {@link Processor} instance
	 * @param predicate
	 *            {@link Predicate} instance
	 * @throws NullPointerException
	 *             if one of parameters is null
	 */
	public Filter(final Processor processor, final Predicate<Item> predicate) throws NullPointerException {
		super(processor);
		this.predicate = Preconditions.checkNotNull(predicate);
	}

	@Override
	public Item apply(final Entity input) {
		final Item item = super.apply(input);
		return predicate.apply(item) ? item : null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("(filter ").append(super.toString()).append(" ");
		return builder.append(predicate).append(")").toString();
	}

}
