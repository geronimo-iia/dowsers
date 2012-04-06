/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at
 
         http://www.apache.org/licenses/LICENSE-2.0
 
       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */
package org.intelligentsia.dowsers.domain.collection;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.intelligentsia.dowsers.domain.LocalDomainEntity;
import org.intelligentsia.dowsers.util.Registry;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class LocalDomainEntitySet<T extends LocalDomainEntity> extends AbstractLocalDomainEntityCollection<T> implements Set<T> {

	private final Set<T> entities;

	public LocalDomainEntitySet(final Registry<LocalDomainEntity> registry) {
		this(registry, new HashSet<T>());
	}

	/**
	 * Build a new instance of <code>LocalDomainEntitySet</code>
	 * 
	 * @param domainEntity
	 * @param entities
	 */
	public LocalDomainEntitySet(final Registry<LocalDomainEntity> registry, final Set<T> entities) {
		super(registry);
		this.entities = entities;
	}

	/**
	 * @return
	 * @see java.util.Set#size()
	 */
	public int size() {
		return entities.size();
	}

	/**
	 * @return
	 * @see java.util.Set#isEmpty()
	 */
	public boolean isEmpty() {
		return entities.isEmpty();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.Set#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		return entities.contains(o);
	}

	/**
	 * @return
	 * @see java.util.Set#iterator()
	 */
	public Iterator<T> iterator() {
		return entities.iterator();
	}

	/**
	 * @return
	 * @see java.util.Set#toArray()
	 */
	public Object[] toArray() {
		return entities.toArray();
	}

	/**
	 * @param a
	 * @return
	 * @see java.util.Set#toArray(T[])
	 */
	public <Y> Y[] toArray(Y[] a) {
		return entities.toArray(a);
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.Set#add(java.lang.Object)
	 */
	public boolean add(T e) {
		register(e);
		return entities.add(e);
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.Set#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		return entities.remove(o);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.Set#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> c) {
		return entities.containsAll(c);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.Set#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends T> c) {
		register(c);
		return entities.addAll(c);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.Set#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> c) {
		return entities.retainAll(c);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.Set#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c) {
		return entities.removeAll(c);
	}

	/**
	 * 
	 * @see java.util.Set#clear()
	 */
	public void clear() {
		entities.clear();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.Set#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		return entities.equals(o);
	}

	/**
	 * @return
	 * @see java.util.Set#hashCode()
	 */
	public int hashCode() {
		return entities.hashCode();
	}

}
