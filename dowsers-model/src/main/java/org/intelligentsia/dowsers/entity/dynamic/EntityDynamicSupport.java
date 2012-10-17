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
package org.intelligentsia.dowsers.entity.dynamic;

import java.util.LinkedHashMap;
import java.util.Map;

import org.intelligentsia.dowsers.entity.Entity;
import org.intelligentsia.dowsers.entity.EntitySupport;
import org.intelligentsia.dowsers.entity.meta.MetaEntityContext;

import com.google.common.base.Preconditions;

/**
 * EntityDynamicSupport extends {@link EntitySupport} and implements an
 * {@link Entity} with a dynamic set of Attribute.
 * 
 * * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityDynamicSupport extends EntitySupport {

	/**
	 * serialVersionUID:long.
	 */
	private static final long serialVersionUID = -4553930262812842405L;
	/**
	 * Map of attributes.
	 */
	private final Map<String, Object> attributes;

	/**
	 * Build a new instance of {@link EntityDynamicSupport} with an empty
	 * attribute's collection.
	 * 
	 * @param identity
	 *            entity's identity.
	 * @param metaEntityContext
	 *            {@link MetaEntityContext} associated with this instance.
	 * @throws NullPointerException
	 *             if one of parameter is null
	 * @throws IllegalArgumentException
	 *             if identifier is empty
	 */
	public EntityDynamicSupport(final String identity, final MetaEntityContext metaEntityContext) throws NullPointerException, IllegalArgumentException {
		this(identity, metaEntityContext, new LinkedHashMap<String, Object>());
	}

	/**
	 * Build a new instance of {@link EntityDynamicSupport}.
	 * 
	 * @param identity
	 *            entity's identity.
	 * @param metaEntityContext
	 *            {@link MetaEntityContext} associated with this instance.
	 * @param attributes
	 *            Map of attributes. Map is not copied.
	 * @throws NullPointerException
	 *             if one of parameter is null
	 * @throws IllegalArgumentException
	 *             if identifier is empty
	 */
	public EntityDynamicSupport(final String identity, final MetaEntityContext metaEntityContext, final Map<String, Object> attributes) throws NullPointerException, IllegalArgumentException {
		super(identity, metaEntityContext);
		this.attributes = Preconditions.checkNotNull(attributes);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <Value> Value attribute(final String name) throws NullPointerException {
		return (Value) attributes.get(Preconditions.checkNotNull(name));
	}

	@Override
	public <Value> Entity attribute(final String name, final Value value) throws NullPointerException {
		attributes.put(Preconditions.checkNotNull(name), value);
		return this;
	}
}
