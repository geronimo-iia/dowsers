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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.StringTokenizer;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

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

	private transient Class<?> type;

	private transient List<Class<?>> genericClass;

	private final String description;

	/**
	 * Build a new instance of ClassInformation.java.
	 * 
	 * @param instance
	 *            instance object
	 * @throws NullPointerException
	 *             if instance is null
	 */
	public ClassInformation(final Object instance) throws NullPointerException {
		this(Preconditions.checkNotNull(instance).getClass());
	}

	/**
	 * Build a new instance of ClassInformation.java.
	 * 
	 * @param type
	 *            type base
	 * @throws NullPointerException
	 *             if type is null
	 */
	public ClassInformation(final Class<?> type) throws NullPointerException {
		this(Preconditions.checkNotNull(type), Reflection.findGenericClass(type));
	}

	/**
	 * Build a new instance of ClassInformation.java.
	 * 
	 * @param type
	 *            type base
	 * @param genericClass
	 *            generic classes
	 * @throws NullPointerException
	 *             if type or genericClass is null
	 */
	public ClassInformation(final Class<?> type, final List<Class<?>> genericClass) throws NullPointerException {
		super();
		this.type = Preconditions.checkNotNull(type);
		this.genericClass = Preconditions.checkNotNull(genericClass);
		// build a common description
		final StringBuilder builder = new StringBuilder(type.getName());
		if (!genericClass.isEmpty()) {
			builder.append("<");
			boolean notFirst = false;
			for (final Class<?> generic : genericClass) {
				if (notFirst) {
					builder.append(",");
				}
				if (generic == null) {
					builder.append(Object.class.getName());
				} else {
					builder.append(generic.getName());
				}
				notFirst = true;
			}
			builder.append(">");
		}
		description = builder.toString();
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
	public boolean isAssignableFrom(final Class<?> other) {
		return type.isAssignableFrom(other);
	}

	/**
	 * @return a textual description of class.
	 */
	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return description;
	}

	/**
	 * Parse a description and return a {@link ClassInformation} instance.
	 * 
	 * @param description
	 * @return a {@link ClassInformation}.
	 * @throws NullPointerException
	 *             if description is null
	 * @throws IllegalArgumentException
	 *             if description is empty or not a {@link ClassInformation}
	 *             representation
	 * @throws IllegalStateException
	 *             if {@link Class} cannot be loaded
	 */
	public static ClassInformation parse(final String description) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		final StringTokenizer tokenizer = new StringTokenizer(Preconditions.checkNotNull(description), "<");
		if (!tokenizer.hasMoreTokens()) {
			throw new IllegalArgumentException("invalid format");
		}

		try {
			final String className = tokenizer.nextToken();
			final Class<?> clazz = Class.forName(className, true, Thread.currentThread().getContextClassLoader());
			if (!tokenizer.hasMoreTokens()) {
				return new ClassInformation(clazz);
			}
			final List<Class<?>> classes = Lists.newArrayList();
			String list = tokenizer.nextToken();
			list = list.substring(0, list.length() - 1);
			final StringTokenizer parameters = new StringTokenizer(list, ",");
			while (parameters.hasMoreTokens()) {
				final String parameter = parameters.nextToken();
				classes.add(Class.forName(parameter, true, Thread.currentThread().getContextClassLoader()));
			}
			return new ClassInformation(clazz, classes);
		} catch (final ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}

	public static ClassInformation toClassInformation(final Class<?> type) {
		return new ClassInformation(type);
	}

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
		return Objects.equal(other.description, description);
	}

	@Override
	public int hashCode() {
		return description.hashCode();
	}

	/**
	 * Specify how serialization is done.
	 * 
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(final ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}

	/**
	 * Specify how deserialization is done.
	 * 
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		// after read description, initialize class
		final ClassInformation classInformation = parse(description);
		this.type = classInformation.type;
		this.genericClass = classInformation.genericClass;
	}
}
