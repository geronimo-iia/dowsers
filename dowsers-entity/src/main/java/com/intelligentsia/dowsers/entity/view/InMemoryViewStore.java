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

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.Maps;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.view.processor.Item;

/**
 * InMemoryViewStore implements {@link ViewStore} in memory.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class InMemoryViewStore implements ViewStore, Iterable<Item> {

	private final Map<Reference, Item> items = Maps.newHashMap();

	/**
	 * Build a new instance of InMemoryViewStore.
	 */
	public InMemoryViewStore() {
		super();
	}

	@Override
	public void update(Reference reference, Item item) {
		if (item == null) {
			items.remove(reference);
		} else {
			items.put(reference, item);
		}
	}

	@Override
	public void drop() {
		items.clear();
	}

	@Override
	public Iterator<Item> iterator() {
		return items.values().iterator();
	}

	public Collection<Item> items() {
		return items.values();
	}
}
