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

import org.intelligentsia.dowsers.core.Identified;
import org.intelligentsia.dowsers.core.IdentifierFactoryProvider;
import org.intelligentsia.dowsers.core.reflection.ClassInformation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

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
 * Note on Removing local attribute versus attribute map usage:
 * <ul>
 * <li>Avoid if-else test on attribute()</li>
 * <li>uniform serialization</li>
 * </ul>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 * 
 */
public class MetaAttribute implements Identified, Serializable {
	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -5802346012784857540L;

	@JsonProperty
	private final String identity;

	@JsonProperty
	private final String name;

	@JsonProperty
	private final ClassInformation valueClass;

	/**
	 * Build a new instance of <code>MetaAttribute</code>.
	 * 
	 * @param identity
	 *            MetaAttribute's identity
	 * @param name
	 *            attribute name
	 * @param valueClass
	 *            value class
	 * 
	 * @throws NullPointerException
	 *             if name or identity or valueClass is null
	 * @throws IllegalArgumentException
	 *             if name or identity is empty
	 */
	@JsonCreator
	private MetaAttribute(@JsonProperty("identity") final String identity, @JsonProperty("name") final String name, @JsonProperty("valueClass") final ClassInformation valueClass) throws NullPointerException, IllegalArgumentException {
		super();
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(identity)));
		this.identity = identity;
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		this.name = name;
		this.valueClass = Preconditions.checkNotNull(valueClass);

	}

	@Override
	public String identity() {
		return identity;
	}

	/**
	 * Returns the attribute name.
	 * 
	 * @return non-<code>null</code> textual attribute name
	 */
	public String name() {
		return name;
	}

	/**
	 * Returns the <code>{@link ClassInformation}</code> object representing the
	 * <code>Value</code> of attribute.
	 * 
	 * @return non-<code>null</code> <code>{@link ClassInformation}</code>
	 *         instance
	 */
	public ClassInformation valueClass() {
		return valueClass;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(getClass()).add("identity", identity()).add("name", name()).add("valueClass", valueClass()).toString();
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

		/**
		 * Build a new instance of MetaEntityContextBuilder.
		 */
		public Builder() {
			super();
			identity = IdentifierFactoryProvider.generateNewIdentifier();
		}

		public MetaAttribute build() {
			return new MetaAttribute(identity, name, valueClass);
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

	}

}
