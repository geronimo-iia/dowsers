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
 * EntityAttributeAccessListener manage {@link AttributeAccessListener}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityAttributeAccessListener extends EntityDecorator {

	private final AttributeAccessListener attributeAccessListener;

	/**
	 * Build a new instance of EntityAttributeAccessListener.java.
	 * 
	 * @param entity
	 *            decorated entity
	 * @param attributeAccessListener
	 *            attribute Access Listener instance
	 * @throws NullPointerException
	 *             if one of parameters is null
	 */
	public EntityAttributeAccessListener(final Entity entity, final AttributeAccessListener attributeAccessListener) throws NullPointerException {
		super(entity);
		this.attributeAccessListener = Preconditions.checkNotNull(attributeAccessListener);
	}

	@Override
	public <Value> Value attribute(final String name) throws NullPointerException, IllegalArgumentException {
		final Value result = super.attribute(name);
		if (result != null) {
			attributeAccessListener.notify(entity, name);
		}
		return result;
	}

}
