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
package org.intelligentsia.dowsers.entity;

import com.google.common.base.Preconditions;

/**
 * EntityDecorator. 
 *
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityDecorator implements Entity {

	private final Entity entity;

	public EntityDecorator(Entity entity) {
		super();
		this.entity = Preconditions.checkNotNull(entity);
	}

	public String getIdentity() {
		return entity.getIdentity();
	}

//	public <T> T getProperty(String name, Class<T> className) throws NullPointerException, IllegalArgumentException, ClassCastException {
//		return entity.getProperty(name, className);
//	}

	public <T> T getProperty(String name) throws NullPointerException, IllegalArgumentException {
		return entity.getProperty(name);
	}

	public <T> void setProperty(String name, T value) throws NullPointerException, IllegalArgumentException, ClassCastException, IllegalStateException {
		entity.setProperty(name, value);
	}

}
