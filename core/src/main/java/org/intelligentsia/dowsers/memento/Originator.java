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
package org.intelligentsia.dowsers.memento;

import org.intelligentsia.dowsers.domain.DomainEntity;

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

	public <T extends DomainEntity> Memento createMemento(T entity);

	public <T extends DomainEntity> void setMemento(T entity, Memento memento);
	
	public <T extends DomainEntity> boolean support(Class<T> entityType);
}
