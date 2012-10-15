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
package org.intelligentsia.dowsers.core.reflection;

import java.io.Serializable;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * ClassInformation.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class ClassInformation implements Serializable {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -5302164830551846190L;

	private final Class<?> type;

	private final List<Class<?>> genericClass;

	/**
	 * Build a new instance of ClassInformation.java.
	 * 
	 * @param instance
	 * @throws NullPointerException
	 *             if instance is null
	 */
	public ClassInformation(Object instance) throws NullPointerException {
		super();
		type = Preconditions.checkNotNull(instance.getClass());
		this.genericClass = Reflection.findGenericClass(instance);
	}

	/**
	 * Build a new instance of ClassInformation.java.
	 * 
	 * @param type
	 * @param genericClass
	 * @throws NullPointerException
	 *             if type or genericClass is null
	 */
	public ClassInformation(Class<?> type, List<Class<?>> genericClass) throws NullPointerException {
		super();
		this.type = Preconditions.checkNotNull(type);
		this.genericClass = Preconditions.checkNotNull(genericClass);
	}

	public Class<?> getType() {
		return type;
	}

	public List<Class<?>> getGenericClass() {
		return genericClass;
	}

	/**
	 * Determines if the class or interface represented by this ClassInformation
	 * object is either the same as, or is a superclass or superinterface of,
	 * the class or interface represented by the specified Class parameter.
	 * 
	 * @param other
	 * @return
	 */
	public boolean isAssignableFrom(Class<?> other) {
		return type.isAssignableFrom(other);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(type.getName());
		if (!genericClass.isEmpty()) {
			builder.append("<");
			boolean notFirst = false;
			for (Class<?> generic : genericClass) {
				if (notFirst) {
					builder.append(",");
				}
				builder.append(generic.getName());
				notFirst = true;
			}
			builder.append(">");
		}
		return builder.toString();
	}

	/**
	 * Entities compare by identity, not by attributes.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ClassInformation other = (ClassInformation) obj;
		if (!Objects.equal(other.type.getName(), type.getName())) {
			return false;
		}
		if (other.genericClass.size() != genericClass.size()) {
			return false;
		}
		for (int i = 0; i < genericClass.size(); i++) {
			if (other.genericClass.get(i).getName().equals(genericClass.get(i).getName())) {
				return false;
			}
		}
		return true;
	}
}
