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
import java.util.Map;

import org.intelligentsia.dowsers.core.IdentifierFactoryProvider;
import org.intelligentsia.dowsers.core.reflection.ClassInformation;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.EntityDynamic;

/**
 * <code>MetaAttribute</code>: Ash nazg durbatulûk, ash nazg gimbatul, ash nazg
 * thrakatulûk agh burzum-ishi krimpatul.
 * 
 * {@link MetaAttribute} is composed of:
 * <ul>
 * <li>name: attribute's name</li>
 * <li>valueClass: attribute's class name</li>
 * <li>defaultValue: attribute's default value</li>
 * </ul>
 * 
 * 
 * Note on Removing local attribute:
 * <ul>
 * <li>Avoid if-else test on attribute()</li>
 * <li>Easier serialization</li>
 * </ul>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 * 
 */
public class MetaAttribute extends EntityDynamic {
	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -5802346012784857540L;

	/**
	 * Build a new instance of <code>MetaAttribute</code>.
	 * 
	 * @param metaAttribute
	 *            meta attribute instance to copy
	 * @throws NullPointerException
	 *             if metaAttribute is null
	 */
	public MetaAttribute(final MetaAttribute metaAttribute) throws NullPointerException {
		this(Preconditions.checkNotNull(metaAttribute).name(), metaAttribute.valueClass(), metaAttribute.defaultValue(), metaAttribute.identity());
	}

	/**
	 * Build a new instance of <code>MetaAttribute</code>.
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
	public MetaAttribute(final String name, final Class<?> valueClass, final Object defaultValue) {
		this(name, new ClassInformation(valueClass), defaultValue, IdentifierFactoryProvider.generateNewIdentifier());
	}

	/**
	 * Build a new instance of <code>MetaAttribute</code>.
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
	public MetaAttribute(final String name, final Class<?> valueClass) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		this(name, new ClassInformation(valueClass), null, IdentifierFactoryProvider.generateNewIdentifier());
	}

	/**
	 * Build a new instance of <code>MetaAttribute</code>.
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
	public MetaAttribute(final String name, final ClassInformation valueClass, final Object defaultValue, final String identity) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		super(identity);
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		super.attribute("name", name);
		super.attribute("valueClass", Preconditions.checkNotNull(valueClass));
		super.attribute("defaultValue", defaultValue);
		if (defaultValue != null) {
			Preconditions.checkState(valueClass.isAssignableFrom(defaultValue.getClass()));
		}
	}

	/**
	 * Build a new instance of MetaAttribute.java.
	 * 
	 * @param attributes
	 */
	public MetaAttribute(Map<String, Object> attributes) {
		this(IdentifierFactoryProvider.generateNewIdentifier(), attributes);
	}

	/**
	 * Build a new instance of MetaAttribute.java.
	 * 
	 * @param identity
	 * @param attributes
	 */
	public MetaAttribute(String identity, Map<String, Object> attributes) {
		this((String) attributes.get("name"), (ClassInformation) attributes.get("valueClass"), attributes.get("defaultValue"), identity);
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

	/**
	 * Returns the attribute name.
	 * 
	 * @return non-<code>null</code> textual attribute name
	 */
	public String name() {
		return attribute("name");
	}

	/**
	 * Returns the <code>{@link ClassInformation}</code> object representing the
	 * <code>Value</code> of attribute.
	 * 
	 * @return non-<code>null</code> <code>{@link ClassInformation}</code>
	 *         instance
	 */
	public ClassInformation valueClass() {
		return attribute("valueClass");
	}

	/**
	 * Returns the attribute default value.
	 * 
	 * @return <code>null</code> or non-<code>null</code> <code>Value</code>
	 *         instance
	 */
	@SuppressWarnings("unchecked")
	public <Value extends Serializable> Value defaultValue() {
		return (Value) attribute("defaultValue");
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(getClass()).add("identity", identity()).add("name", name()).add("valueClass", valueClass()).add("defaultValue", defaultValue()).toString();
	}

	/**
	 * @return a new {@link Builder} instance.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder implements builder pattern for {@link MetaAttribute}.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
	 */
	public static class Builder {

		private String identity;
		private String name;
		private ClassInformation valueClass;
		private Object defaultValue;

		/**
		 * Build a new instance of MetaEntityContextBuilder.
		 */
		public Builder() {
			super();
			identity = IdentifierFactoryProvider.generateNewIdentifier();
		}

		public MetaAttribute build() {
			return new MetaAttribute(name, valueClass, defaultValue, identity);
		}

		/**
		 * Set identity.
		 * 
		 * @param identity
		 * @return this instance.
		 * @throws NullPointerException
		 *             if identity is null
		 * @throws IllegalArgumentException
		 *             if identity is empty
		 */
		public Builder identity(final String identity) throws NullPointerException, IllegalArgumentException {
			Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(identity)));
			this.identity = identity;
			return this;
		}

		/**
		 * Set name.
		 * 
		 * @param name
		 * @return this instance.
		 * @throws NullPointerException
		 *             if name is null
		 * @throws IllegalArgumentException
		 *             if name is empty
		 */
		public Builder name(final String name) throws NullPointerException, IllegalArgumentException {
			Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
			this.name = name;
			return this;
		}

		/**
		 * Set valueClass.
		 * 
		 * @param valueClass
		 * @return this instance.
		 * @throws NullPointerException
		 *             if valueClass is null
		 */
		public Builder valueClass(final ClassInformation valueClass) throws NullPointerException {
			this.valueClass = Preconditions.checkNotNull(valueClass);
			return this;
		}

		/**
		 * Set valueClass.
		 * 
		 * @param valueClass
		 * @return this instance.
		 * @throws NullPointerException
		 *             if valueClass is null
		 */
		public Builder valueClass(final Class<?> valueClass) throws NullPointerException {
			this.valueClass = new ClassInformation(Preconditions.checkNotNull(valueClass));
			return this;
		}

		/**
		 * Set defaultValue.
		 * 
		 * @param defaultValue
		 * @return this instance.
		 * @throws NullPointerException
		 *             if defaultValue is null
		 */
		public Builder defaultValue(final Object defaultValue) throws NullPointerException {
			this.defaultValue = Preconditions.checkNotNull(defaultValue);
			return this;
		}

	}

}
