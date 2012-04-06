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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.intelligentsia.dowsers.domain.LocalDomainEntity;
import org.intelligentsia.dowsers.util.Registry;

import com.google.common.base.Preconditions;

/**
 * LocalDomainEntityList.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class LocalDomainEntityList<T extends LocalDomainEntity> extends AbstractLocalDomainEntityCollection<T> implements List<T> {

	private final List<T> entities;

	/**
	 * 
	 * Build a new instance of <code>LocalDomainEntityList</code>.
	 * 
	 * @param domainEntity
	 * @throws NullPointerException
	 *             if domainEntity is null
	 */
	public LocalDomainEntityList(final Registry<LocalDomainEntity> domainEntity) throws NullPointerException {
		this(domainEntity, new ArrayList<T>());
	}

	/**
	 * 
	 * Build a new instance of <code>LocalDomainEntityList</code>.
	 * 
	 * @param domainEntity
	 * @param entities
	 * @throws NullPointerException
	 *             if domainEntity or entities is null
	 */
	public LocalDomainEntityList(final Registry<LocalDomainEntity> domainEntity, final List<T> entities) throws NullPointerException {
		super(domainEntity);
		this.entities = Preconditions.checkNotNull(entities);
	}

	/**
	 * @return
	 * @see java.util.List#size()
	 */
	public int size() {
		return entities.size();
	}

	/**
	 * @return
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty() {
		return entities.isEmpty();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		return entities.contains(o);
	}

	/**
	 * @return
	 * @see java.util.List#iterator()
	 */
	public Iterator<T> iterator() {
		return entities.iterator();
	}

	/**
	 * @return
	 * @see java.util.List#toArray()
	 */
	public Object[] toArray() {
		return entities.toArray();
	}

	/**
	 * @param a
	 * @return
	 * @see java.util.List#toArray(T[])
	 */
	public <Y> Y[] toArray(Y[] a) {
		return entities.toArray(a);
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(T e) {
		register(e);
		return entities.add(e);
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		return entities.remove(o);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> c) {
		return entities.containsAll(c);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends T> c) {
		register(c);
		return entities.addAll(c);
	}

	/**
	 * @param index
	 * @param c
	 * @return
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public boolean addAll(int index, Collection<? extends T> c) {
		register(c);
		return entities.addAll(index, c);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c) {
		return entities.removeAll(c);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> c) {
		return entities.retainAll(c);
	}

	/**
	 * 
	 * @see java.util.List#clear()
	 */
	public void clear() {
		entities.clear();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		return entities.equals(o);
	}

	/**
	 * @return
	 * @see java.util.List#hashCode()
	 */
	public int hashCode() {
		return entities.hashCode();
	}

	/**
	 * @param index
	 * @return
	 * @see java.util.List#get(int)
	 */
	public T get(int index) {
		return entities.get(index);
	}

	/**
	 * @param index
	 * @param element
	 * @return
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public T set(int index, T element) {
		return entities.set(index, element);
	}

	/**
	 * @param index
	 * @param element
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	public void add(int index, T element) {
		entities.add(index, element);
	}

	/**
	 * @param index
	 * @return
	 * @see java.util.List#remove(int)
	 */
	public T remove(int index) {
		return entities.remove(index);
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOf(Object o) {
		return entities.indexOf(o);
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	public int lastIndexOf(Object o) {
		return entities.lastIndexOf(o);
	}

	/**
	 * @return
	 * @see java.util.List#listIterator()
	 */
	public ListIterator<T> listIterator() {
		return entities.listIterator();
	}

	/**
	 * @param index
	 * @return
	 * @see java.util.List#listIterator(int)
	 */
	public ListIterator<T> listIterator(int index) {
		return entities.listIterator(index);
	}

	/**
	 * @param fromIndex
	 * @param toIndex
	 * @return
	 * @see java.util.List#subList(int, int)
	 */
	public List<T> subList(int fromIndex, int toIndex) {
		return entities.subList(fromIndex, toIndex);
	}

}
