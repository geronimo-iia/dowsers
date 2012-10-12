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
package org.intelligentsia.dowsers.entity.meta;

import java.io.Serializable;

import org.intelligentsia.dowsers.entity.Entity;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public class MetaAttributeDefinition implements MetaAttribute {
	/**
	 * attribute name.
	 */
	private final String name;

	/**
	 * attribute value class.
	 */
	private final Class<?> valueClass;

	/**
	 * attribute default value.
	 */
	private final Object defaultValue;

	/**
	 * Build a new instance of MetaAttributeDefinition.java.
	 * 
	 * @param metaAttribute
	 * @throws NullPointerException
	 *             if metaAttribute is null
	 */
	public MetaAttributeDefinition(final MetaAttribute metaAttribute) throws NullPointerException {
		this.name = Preconditions.checkNotNull(metaAttribute).getName();
		this.valueClass = metaAttribute.getValueClass();
		this.defaultValue = metaAttribute.getDefaultValue();
	}

	/**
	 * Build a new instance of MetaAttributeDefinition.java.
	 * 
	 * @param name
	 *            attribute name
	 * @param valueClass
	 *            value class
	 * @param defaultValue
	 *            default value
	 * @throws NullPointerException
	 *             if name or valueClass is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 * @throws IllegalStateException
	 *             if value is not assignable to specified value class
	 */
	public MetaAttributeDefinition(final String name, final Class<?> valueClass, final Object defaultValue) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		super();
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		this.name = name;
		this.valueClass = Preconditions.checkNotNull(valueClass);
		this.defaultValue = defaultValue;
		if (defaultValue != null) {
			Preconditions.checkState(valueClass.isAssignableFrom(defaultValue.getClass()));
		}
	}

	@Override
	public String getIdentity() {
		return null;
	}

	@Override
	public <Value> Value attribute(final String name) throws NullPointerException, IllegalArgumentException {
		return null;
	}

	@Override
	public <Value> Entity attribute(final String name, final Value value) throws NullPointerException, IllegalArgumentException {
		return null;
	}

	@Override
	public MetaEntityContext getMetaEntityContext() {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class<?> getValueClass() {
		return valueClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <Value extends Serializable> Value getDefaultValue() {
		return (Value) defaultValue;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(getClass()).add("name", name).add("valueClass", valueClass).add("defaultValue", defaultValue).toString();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MetaAttribute other = (MetaAttribute) obj;
		if (name == null) {
			if (other.getName() != null) {
				return false;
			}
		} else if (!name.equals(other.getName())) {
			return false;
		}
		return true;
	}

}
