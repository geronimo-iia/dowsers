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
package org.intelligentsia.dowsers.model;

import com.google.common.base.Preconditions;

/**
 * {@link Property} act as value object.
 * 
 * <p>
 * What is a value object (a Property) ?
 * </p>
 * <ul>
 * <li>“A Value Object cannot live on its own without an Entity.”</li>
 * <li>Eric Evans:<br />
 * “An object that represents a descriptive aspect of the domain with no
 * conceptual identity is called a VALUE OBJECT. VALUE OBJECTS are instantiated
 * to represent elements of the design that we care about only for what they
 * are, not who or which they are.” [Evans 2003]</li>
 * </ul>
 * 
 * 
 * @param <Value>
 *            Object value class
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class Property {

	private final String name;

	private Object value;

	/**
	 * Build a new instance of Property.java.
	 * 
	 * @param name
	 * @throws NullPointerException
	 *             if name is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 */
	public Property(String name) throws NullPointerException, IllegalArgumentException {
		super();
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		this.name = name;
	}

	/**
	 * @return property name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return object value instance.
	 */
	@SuppressWarnings("unchecked")
	public <Value> Value getValue() {
		return (Value) value;
	}

	/**
	 * @param value
	 *            value to set
	 */
	public <Value> void setValue(final Value value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Property other = (Property) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
