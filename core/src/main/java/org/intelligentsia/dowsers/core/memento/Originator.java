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

/**
 * Originator: the object that knows how to save itself.
 * 
 * The Originator interface is for the snapshot functionality which is an
 * optimization technique for speeding up loading aggregate roots from the Event
 * Store.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public interface Originator {

	/**
	 * Create a memento instance for specified entity.
	 * 
	 * @param entity
	 *            entity source
	 * @return a memento instance.
	 */
	public <T> Memento createMemento(T entity);

	/**
	 * Apply memento on specified entity.
	 * 
	 * @param entity
	 *            target entity
	 * @param memento
	 *            memento to apply
	 * @throws IllegalStateException
	 *             if entity did not support this memento
	 */
	public <T> void setMemento(T entity, Memento memento) throws IllegalStateException;

	/**
	 * @param entity
	 *            entity
	 * @return true if specified entity is supported by this instance of
	 *         Originator
	 */
	public <T> boolean support(Class<T> entity);
}
