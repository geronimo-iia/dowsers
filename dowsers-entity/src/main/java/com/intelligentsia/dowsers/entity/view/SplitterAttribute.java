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

import org.intelligentsia.keystone.kernel.api.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.intelligentsia.dowsers.entity.view.processor.Item;

/**
 * SplitterAttribute implement an attribute based splitter. The specified
 * attribute should be {@link Iterable}. If attribute value is null, list will
 * be empty (depends on empty flag).
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class SplitterAttribute implements Splitter {

	private final String attributeName;

	private final boolean ignoreEmpty;

	/**
	 * Build a new instance of SplitterAttribute. if attribute value is empty
	 * then the result list will be empty.
	 * 
	 * @param attributeName
	 *            attribute Name
	 */
	public SplitterAttribute(final String attributeName) {
		this(attributeName, true);
	}

	/**
	 * Build a new instance of SplitterAttribute.
	 * 
	 * @param attributeName
	 *            attribute Name
	 * @param ignoreEmpty
	 *            if true and attribute value is empty then the result list will
	 *            be empty. Either, a list with a single item will be returned.
	 */
	public SplitterAttribute(final String attributeName, final boolean ignoreEmpty) {
		this.attributeName = Preconditions.checkNotNull(attributeName);
		this.ignoreEmpty = ignoreEmpty;
	}

	@Override
	public List<Item> apply(final Item input) {
		final List<Item> items = Lists.newArrayList();

		final Object object = input.get(attributeName);
		if (object != null) {
			if (!Iterable.class.isAssignableFrom(object.getClass())) {
				throw new IllegalStateException(StringUtils.format("Attribute %s did not implements Iterable interface", attributeName));
			}
			final Iterable<?> iterable = (Iterable<?>) object;
			for (final Object thing : iterable) {
				final Item item = input.dump();
				item.put(attributeName, thing);
				items.add(item);
			}
		} else {
			if (!ignoreEmpty) {
				items.add(input);
			}
		}
		return items;
	}

}
