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

import org.intelligentsia.dowsers.entity.meta.MetaAttribute;
import org.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import org.intelligentsia.keystone.api.StringUtils;


/**
 * MetaEntityValidator validate {@link Entity} against {@link MetaEntityContext}
 * .
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class MetaEntityValidator extends EntityDecorator {

	/**
	 * Build a new instance of <code>MetaEntityValidator</code>.
	 * 
	 * @param entity
	 *            decorated entity
	 * @throws NullPointerException
	 *             if entity is null
	 */
	public MetaEntityValidator(final Entity entity) throws NullPointerException {
		super(entity);
	}

	/**
	 * @throws IllegalArgumentException
	 *             if no attribute with specified name exists.
	 * @see org.intelligentsia.dowsers.entity.EntityDecorator#attribute(java.lang.String)
	 */
	@Override
	public <Value> Value attribute(final String name) throws NullPointerException, IllegalArgumentException {
		if (!metaEntityContext().contains(name)) {
			throw new IllegalArgumentException(StringUtils.format("Attribute %s did not exists for entity %s", name, metaEntityContext().name()));
		}
		return super.attribute(name);
	}

	/**
	 * @throws IllegalArgumentException
	 *             if no attribute with specified name exists or if value class
	 *             is invalid.
	 * @see org.intelligentsia.dowsers.entity.EntityDecorator#attribute(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public <Value> Entity attribute(final String name, final Value value) throws NullPointerException, IllegalArgumentException {
		final MetaAttribute metaAttribute = metaEntityContext().metaAttributes(name);
		if (metaAttribute == null) {
			throw new IllegalArgumentException(StringUtils.format("Attribute %s did not exists for entity %s", name, metaEntityContext().name()));
		}
		if ((value != null) && !metaAttribute.valueClass().isAssignableFrom(value.getClass())) {
			throw new IllegalArgumentException(StringUtils.format("Invalid value of attribute %s for entity %s. Expected %s.", name, metaEntityContext().name(), metaAttribute.valueClass()));
		}
		return super.attribute(name, value);
	}
}
