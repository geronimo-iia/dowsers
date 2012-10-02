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
package org.intelligentsia.dowsers.domain.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.intelligentsia.dowsers.core.Registry;
import org.intelligentsia.dowsers.domain.LocalDomainEntity;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class LocalDomainEntityMap<K, T extends LocalDomainEntity> extends AbstractLocalDomainEntityCollection<T> implements Map<K, T> {

	private final Map<K, T> entities;

	public LocalDomainEntityMap(final Registry<LocalDomainEntity> domainEntity) {
		this(domainEntity, new HashMap<K, T>());
	}

	public LocalDomainEntityMap(final Registry<LocalDomainEntity> domainEntity, final Map<K, T> entities) {
		super(domainEntity);
		this.entities = entities;
	}

	/**
	 * @return
	 * @see java.util.Map#size()
	 */
	@Override
	public int size() {
		return entities.size();
	}

	/**
	 * @return
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return entities.isEmpty();
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(final Object key) {
		return entities.containsKey(key);
	}

	/**
	 * @param value
	 * @return
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(final Object value) {
		return entities.containsValue(value);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public T get(final Object key) {
		return entities.get(key);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public T put(final K key, final T value) {
		register(value);
		return entities.put(key, value);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public T remove(final Object key) {
		return entities.remove(key);
	}

	/**
	 * @param m
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(final Map<? extends K, ? extends T> m) {
		register(m.values());
		entities.putAll(m);
	}

	/**
	 * 
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {
		entities.clear();
	}

	/**
	 * @return
	 * @see java.util.Map#keySet()
	 */
	@Override
	public Set<K> keySet() {
		return entities.keySet();
	}

	/**
	 * @return
	 * @see java.util.Map#values()
	 */
	@Override
	public Collection<T> values() {
		return entities.values();
	}

	/**
	 * @return
	 * @see java.util.Map#entrySet()
	 */
	@Override
	public Set<java.util.Map.Entry<K, T>> entrySet() {
		return entities.entrySet();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.Map#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		return entities.equals(o);
	}

	/**
	 * @return
	 * @see java.util.Map#hashCode()
	 */
	@Override
	public int hashCode() {
		return entities.hashCode();
	}

}