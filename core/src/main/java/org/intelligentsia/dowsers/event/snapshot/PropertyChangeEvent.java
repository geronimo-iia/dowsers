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
package org.intelligentsia.dowsers.event.snapshot;

import org.intelligentsia.dowsers.domain.Entity;
import org.intelligentsia.dowsers.event.DomainEvent;

/**
 * PropertyChangeEvent class event.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class PropertyChangeEvent extends DomainEvent {

	private static final long serialVersionUID = -4750111435218989970L;

	private final String name;
	private final Object value;

	/**
	 * Build a new instance of PropertyChangeEvent.
	 * 
	 * @param entity
	 * @param name
	 * @param value
	 * @throws NullPointerException
	 */
	public PropertyChangeEvent(Entity entity, String name, Object value) throws NullPointerException {
		super(entity);
		this.name = name;
		this.value = value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

}
