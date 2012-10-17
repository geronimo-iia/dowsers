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

import org.intelligentsia.dowsers.core.IdentifierFactoryProvider;
import org.intelligentsia.dowsers.core.reflection.ClassInformation;
import org.intelligentsia.dowsers.entity.Entity;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * <code>MetaAttributeDefinition</code> implements {@link MetaAttribute}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 * 
 */
public class MetaAttributeDefinition implements MetaAttribute {
	/**
	 * Identity.
	 */
	private final String identity;
	/**
	 * attribute name.
	 */
	private final String name;

	/**
	 * attribute value class.
	 */
	private final ClassInformation valueClass;

	/**
	 * attribute default value.
	 */
	private final Object defaultValue;

	/**
	 * Build a new instance of <code>MetaAttributeDefinition</code>.
	 * 
	 * @param metaAttribute
	 *            meta attribute instance to copy
	 * @throws NullPointerException
	 *             if metaAttribute is null
	 */
	public MetaAttributeDefinition(final MetaAttribute metaAttribute) throws NullPointerException {
		this(Preconditions.checkNotNull(metaAttribute).name(), metaAttribute.valueClass(), metaAttribute.defaultValue(), metaAttribute.identity());
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
	 * 
	 */
	public MetaAttributeDefinition(final String name, final Class<?> valueClass, final Object defaultValue) {
		this(name, new ClassInformation(valueClass), defaultValue);
	}

	/**
	 * Build a new instance of <code>MetaAttributeDefinition</code>.
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
	public MetaAttributeDefinition(final String name, final ClassInformation valueClass, final Object defaultValue) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		this(name, valueClass, defaultValue, new StringBuilder(name).append(':').append(IdentifierFactoryProvider.generateNewIdentifier()).toString());
	}

	/**
	 * Build a new instance of <code>MetaAttributeDefinition</code>.
	 * 
	 * @param name
	 *            attribute name
	 * @param valueClass
	 *            value class
	 * @param defaultValue
	 *            default value
	 * @param identity
	 *            MetaAttribute's identity
	 * @throws NullPointerException
	 *             if name or identity or valueClass is null
	 * @throws IllegalArgumentException
	 *             if name or identity is empty
	 * @throws IllegalStateException
	 *             if value is not assignable to specified value class
	 */
	public MetaAttributeDefinition(final String name, final ClassInformation valueClass, final Object defaultValue, final String identity) throws NullPointerException, IllegalArgumentException, IllegalStateException {

		super();
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(identity)));
		this.name = name;
		this.valueClass = Preconditions.checkNotNull(valueClass);
		this.defaultValue = defaultValue;
		if (defaultValue != null) {
			Preconditions.checkState(valueClass.isAssignableFrom(defaultValue.getClass()));
		}
		// generate identity
		this.identity = identity;
	}

	@Override
	public String identity() {
		return identity;
	}

	@Override
	public <Value> Value attribute(final String name) throws NullPointerException, IllegalArgumentException {
		return null;
	}

	/**
	 * Always throw IllegalStateException:a {@link MetaAttribute} is Immutable.
	 * 
	 * @throw IllegalStateException
	 */
	@Override
	public <Value> Entity attribute(final String name, final Value value) throws IllegalStateException {
		throw new IllegalStateException("MetaAttribute is Immutable");
	}

	@Override
	public MetaEntityContext metaEntityContext() {
		return MetaModel.getMetaAttributModel();
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public ClassInformation valueClass() {
		return valueClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <Value extends Serializable> Value defaultValue() {
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
			if (other.name() != null) {
				return false;
			}
		} else if (!name.equals(other.name())) {
			return false;
		}
		return true;
	}

}
