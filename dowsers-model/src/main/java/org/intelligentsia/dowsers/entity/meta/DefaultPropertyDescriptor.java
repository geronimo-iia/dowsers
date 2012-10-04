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

import com.google.common.base.Preconditions;

/**
 * DefaultPropertyDescriptor.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class DefaultPropertyDescriptor implements PropertyDescriptor {

	private final String name;

	private final Class<?> valueClass;

	private final Object value;

	/**
	 * Build a new instance of DefaultPropertyDescriptor.java.
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
	public DefaultPropertyDescriptor(String name, Class<?> valueClass, Object value) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		super();
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		this.name = name;
		this.valueClass = Preconditions.checkNotNull(valueClass);
		this.value = value;
		if (value != null) {
			Preconditions.checkState(valueClass.isAssignableFrom(value.getClass()));
		}
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
	public <Value> Value getDefaultValue() {
		return (Value) value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultPropertyDescriptor other = (DefaultPropertyDescriptor) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DefaultPropertyDescriptor [name=" + name + ", valueClass=" + valueClass + ", value=" + value + "]";
	}

}
