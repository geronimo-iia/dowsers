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
package org.intelligentsia.dowsers.repository.memento;

import org.intelligentsia.dowsers.core.Serializer;

/**
 * SerializeOriginator implement a Originator with memento as byte array using
 * Serializer.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class SerializeOriginator implements Originator {

	private Serializer<?> serializer;

	/**
	 * Build a new instance of SerializeOriginator.
	 */
	public SerializeOriginator(Serializer<?> serializer) {
		this.serializer = serializer;
	}

	/**
	 * @see org.intelligentsia.dowsers.repository.memento.Originator#createMemento(java.lang.Object)
	 */
	@Override
	public <T> Memento createMemento(T entity) {
		return 	new ByteArrayMemento(serializer.serialize(entity));
	}

	/**
	 * @see org.intelligentsia.dowsers.repository.memento.Originator#setMemento(java.lang.Object,
	 *      org.intelligentsia.dowsers.repository.memento.Memento)
	 */
	@Override
	public <T> void setMemento(T entity, Memento memento) throws IllegalStateException {
	}

	/**
	 * @see org.intelligentsia.dowsers.repository.memento.Originator#support(java.lang.Class)
	 */
	@Override
	public <T> boolean support(Class<T> entity) {
		return false;
	}

}
