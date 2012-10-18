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
package com.intelligentsia.dowsers.entity.meta;

import java.io.Serializable;

import org.intelligentsia.dowsers.core.IdentifierFactoryProvider;
import org.intelligentsia.dowsers.core.reflection.ClassInformation;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.EntityDynamic;

/**
 * <code>MetaAttributeDefinition</code> implements {@link MetaAttribute}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 * 
 */
public class MetaAttributeDefinition extends EntityDynamic implements MetaAttribute {
	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -5802346012784857540L;
//
//	/**
//	 * attribute name.
//	 */
//	private final String name;
//
//	/**
//	 * attribute value class.
//	 */
//	private final ClassInformation valueClass;
//
//	/**
//	 * attribute default value.
//	 */
//	private final Object defaultValue;

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
	 * 
	 */
	public MetaAttributeDefinition(final String name, final Class<?> valueClass, final Object defaultValue) {
		this(name, new ClassInformation(valueClass), defaultValue, IdentifierFactoryProvider.generateNewIdentifier());
	}

	/**
	 * Build a new instance of <code>MetaAttributeDefinition</code>.
	 * 
	 * @param name
	 *            attribute name
	 * @param valueClass
	 *            value class 
	 * @throws NullPointerException
	 *             if name or valueClass is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 * @throws IllegalStateException
	 *             if value is not assignable to specified value class
	 */
	public MetaAttributeDefinition(final String name, final Class<?> valueClass) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		this(name, new ClassInformation(valueClass), null, IdentifierFactoryProvider.generateNewIdentifier());
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
		super(identity);
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		super.attribute( "name", name);
		super.attribute( "valueClass", Preconditions.checkNotNull(valueClass));
		super.attribute( "defaultValue", defaultValue);
//		this.name = name;
//		this.valueClass = Preconditions.checkNotNull(valueClass);
//		this.defaultValue = defaultValue;
		if (defaultValue != null) {
			Preconditions.checkState(valueClass.isAssignableFrom(defaultValue.getClass()));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <Value> Value attribute(final String name) throws NullPointerException, IllegalArgumentException {
		Preconditions.checkState(!"".equals(Preconditions.checkNotNull(name)));
		if ("identity".equals(name)) {
			return (Value) identity();
		}
//		if ("name".equals(name)) {
//			return (Value) name();
//		} else if ("valueClass".equals(name)) {
//			return (Value) valueClass();
//		} else if ("defaultValue".equals(name)) {
//			return (Value) defaultValue();
//		}
		return super.attribute(name);
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
	public String name() {
		return attribute("name");//name;
	}

	@Override
	public ClassInformation valueClass() {
		return attribute("valueClass");//valueClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <Value extends Serializable> Value defaultValue() {
		return (Value) attribute("defaultValue");// defaultValue;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(getClass()).add("name", name()).add("valueClass", valueClass()).add("defaultValue", defaultValue()).toString();
	}

}
