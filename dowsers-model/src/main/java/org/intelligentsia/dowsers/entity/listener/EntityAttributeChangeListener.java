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
package org.intelligentsia.dowsers.entity.listener;

import org.intelligentsia.dowsers.entity.Entity;
import org.intelligentsia.dowsers.entity.EntityDecorator;

import com.google.common.base.Preconditions;

/**
 * EntityAttributeChangeListener manage {@link AttributeChangeListener}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityAttributeChangeListener extends EntityDecorator {

	private final AttributeChangeListener attributeChangeListener;

	/**
	 * Build a new instance of EntityAttributeChangeListener.java.
	 * 
	 * @param entity
	 *            decorated entity
	 * @param attributeChangeListener
	 *            attribute change Listener instance
	 * @throws NullPointerException
	 *             if one of parameters is null
	 */
	public EntityAttributeChangeListener(Entity entity, AttributeChangeListener attributeChangeListener) throws NullPointerException {
		super(entity);
		this.attributeChangeListener = Preconditions.checkNotNull(attributeChangeListener);
	}

	@Override
	public <Value> Entity attribute(String name, Value value) throws NullPointerException, IllegalArgumentException {
		attributeChangeListener.notify(entity.getClass(), entity.identity(), name, value);
		return super.attribute(name, value);
	}
}
