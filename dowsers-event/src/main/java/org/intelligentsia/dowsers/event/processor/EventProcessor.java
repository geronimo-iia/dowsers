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
package org.intelligentsia.dowsers.event.processor;

import org.intelligentsia.dowsers.domain.Entity;
import org.intelligentsia.dowsers.event.DomainEvent;

/**
 * EventProcessor manage event processing.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface EventProcessor {

	/**
	 * Process specified domain event on source entity.
	 * 
	 * @param entity
	 *            entity which raise event and listen
	 * @param domainEvent
	 *            domain Event to process.
	 */
	public void apply(Entity entity, DomainEvent domainEvent);

	/**
	 * Register handler of specified entity type.
	 * 
	 * @param <T>
	 * @param entityType
	 *            entity Type
	 * @throws IllegalStateException
	 *             if entity type cannot be registered
	 */
	public <T extends Entity> void register(Class<T> entityType) throws IllegalStateException;
}