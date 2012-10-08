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
package org.intelligentsia.dowsers.model.meta;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * 
 * {@link MetaProperty}: Ash nazg durbatulûk, ash nazg gimbatul, ash nazg
 * thrakatulûk agh burzum-ishi krimpatul.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MetaProperty {

	/**
	 * property name.
	 */
	private final String name;

	/**
	 * Property value class.
	 */
	private final Class<?> valueClass;

	/**
	 * default value.
	 */
	private final Object value;

	/**
	 * Build a new instance of MetaProperty.java.
	 * 
	 * @param metaProperty
	 * @throws NullPointerException
	 *             if metaProperty is null
	 */
	public MetaProperty(final MetaProperty metaProperty) throws NullPointerException {
		this.name = Preconditions.checkNotNull(metaProperty).name;
		this.valueClass = metaProperty.valueClass;
		this.value = metaProperty.value;
	}

	/**
	 * Build a new instance of MetaProperty.java.
	 * 
	 * @param name
	 *            property name
	 * @param valueClass
	 *            value class
	 * @param value
	 *            default value
	 * @throws NullPointerException
	 *             if name or valueClass is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 * @throws IllegalStateException
	 *             if value is not assignable to specified value class
	 */
	public MetaProperty(final String name, final Class<?> valueClass, final Object value) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		super();
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		this.name = name;
		this.valueClass = Preconditions.checkNotNull(valueClass);
		this.value = value;
		if (value != null) {
			Preconditions.checkState(valueClass.isAssignableFrom(value.getClass()));
		}
	}

	/**
	 * Returns the property name.
	 * 
	 * @return non-<code>null</code> textual property identifier
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the <code>{@link Class}</code> object representing the
	 * <code>Value</code> generic parameter.
	 * 
	 * @return non-<code>null</code> <code>{@link Class}</code> instance
	 */
	public Class<?> getValueClass() {
		return valueClass;
	}

	/**
	 * Returns the property default value.
	 * 
	 * @return <code>null</code> or non-<code>null</code> <code>Value</code>
	 *         instance
	 */
	@SuppressWarnings("unchecked")
	public <Value> Value getDefaultValue() {
		return (Value) value;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(getClass()).add("name", name).add("valueClass", valueClass).add("value", value).toString();
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
		final MetaProperty other = (MetaProperty) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
