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
package org.intelligentsia.dowsers.core.memento;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.intelligentsia.dowsers.event.DomainEvent;

import com.google.common.collect.Lists;

/**
 * DomainEventMemento.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DomainEventMemento implements Memento, Iterable<DomainEvent> {

	private List<DomainEvent> domainEvents;

	/**
	 * Build a new instance of DomainEventMemento.
	 */
	public DomainEventMemento() {
		super();
		domainEvents = Lists.newArrayList();
	}

	/**
	 * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(domainEvents.size());
		for (DomainEvent event : domainEvents) {
			out.writeObject(event);
		}
	}

	/**
	 * @see java.io.Externalizable#readExternal(java.io.ObjectInput)
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		int size = in.readInt();
		domainEvents = new ArrayList<DomainEvent>(size);
		for (int i = 0; i < size; i++) {
			domainEvents.add((DomainEvent) in.readObject());
		}
	}

	/**
	 * @return
	 * @see java.util.List#size()
	 */
	public int size() {
		return domainEvents.size();
	}

	/**
	 * @return
	 * @see java.util.List#iterator()
	 */
	public Iterator<DomainEvent> iterator() {
		return domainEvents.iterator();
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(DomainEvent e) {
		return domainEvents.add(e);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends DomainEvent> c) {
		return domainEvents.addAll(c);
	}

	/**
	 * 
	 * @see java.util.List#clear()
	 */
	public void clear() {
		domainEvents.clear();
	}

	/**
	 * @param index
	 * @return
	 * @see java.util.List#get(int)
	 */
	public DomainEvent get(int index) {
		return domainEvents.get(index);
	}
}
