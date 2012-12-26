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
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.view.processor.Item;

/**
 * InMemoryViewStore implements {@link ViewStore} in memory.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class InMemoryViewStore implements ViewStore, Iterable<Item> {

	private final Multimap<Reference, Item> store = ArrayListMultimap.create();

	/**
	 * Build a new instance of InMemoryViewStore.
	 */
	public InMemoryViewStore() {
		super();
	}

	@Override
	public void update(final Reference reference, final Item item) {
		if (item == null) {
			remove(reference);
		} else {
			store.put(reference, item);
		}
	}

	@Override
	public void update(final Reference reference, final List<Item> items) {
		if (items == null) {
			remove(reference);
		} else {
			store.putAll(reference, items);
		}
	}

	@Override
	public void drop() {
		store.clear();
	}

	@Override
	public Iterator<Item> iterator() {
		return store.values().iterator();
	}

	public Collection<Item> items() {
		return store.values();
	}

	@Override
	public void remove(final Reference identity) {
		store.removeAll(identity);
	}
}
