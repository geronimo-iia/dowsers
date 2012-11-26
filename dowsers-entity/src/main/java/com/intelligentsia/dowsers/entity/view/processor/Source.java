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
package com.intelligentsia.dowsers.entity.view.processor;

import java.util.Iterator;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * Source transform an Entity in an Item with aliasing possibility.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public final class Source implements Processor {

	/**
	 * {@link Class} of source {@link Entity}.
	 */
	private final Class<? extends Entity> entityType;

	/**
	 * Alias.
	 */
	private final String alias;

	/**
	 * {@link Set} of attribute to include.
	 */
	private final ImmutableSet<String> attributeNames;

	/**
	 * The string form of this {@link Source}.
	 * 
	 * @serial
	 */
	private final transient String value;

	/**
	 * Build a new instance of Source.
	 * 
	 * @param expectedType
	 *            expected type of {@link Entity}
	 * @param alias
	 *            alias to prefix all attribute name
	 */
	public Source(final Class<? extends Entity> entityType, final String alias) throws NullPointerException {
		this(entityType, alias, null);
	}

	/**
	 * Build a new instance of Source.
	 * 
	 * @param expectedType
	 *            expected type of {@link Entity}
	 * @param alias
	 *            alias to prefix all attribute name
	 * @param attributeNames
	 *            set of attribute name, null parameter meaning is "include all
	 *            attributes".
	 */
	public Source(final Class<? extends Entity> entityType, final String alias, final ImmutableSet<String> attributeNames) throws NullPointerException {
		super();
		this.entityType = Preconditions.checkNotNull(entityType);
		this.attributeNames = attributeNames;

		StringBuilder builder = new StringBuilder("(source ");

		if ((alias == null) || "".equals(alias)) {
			this.alias = "";
			builder.append(entityType.getName());
		} else {
			this.alias = alias + ".";
			builder.append("(").append(entityType.getName()).append(" ").append(alias).append(")");
		}
		if (attributeNames != null) {
			builder.append(" ");
			Iterator<String> iterator = attributeNames.iterator();
			while (iterator.hasNext()) {
				builder.append(iterator.next()).append(" ");
			}
		}
		value = builder.append(")").toString();
	}

	@Override
	public Item apply(final Entity entity) {
		final Item item = new Item();
		final Iterator<String> iterator = entity.attributeNames().iterator();
		while (iterator.hasNext()) {
			final String name = iterator.next();
			if (include(name)) {
				item.put(alias + name, entity.attribute(name));
			}
		}
		if (include(Reference.IDENTITY)) {
			item.put(alias + Reference.IDENTITY, entity.identity());
		}
		return item;
	}

	/**
	 * @param name
	 * @return true if we must include this attribute.
	 */
	protected boolean include(final String name) {
		return attributeNames == null || attributeNames.contains(name);
	}

	@Override
	public String toString() {
		return value;
	}

	public Class<? extends Entity> getEntityType() {
		return entityType;
	}
}
