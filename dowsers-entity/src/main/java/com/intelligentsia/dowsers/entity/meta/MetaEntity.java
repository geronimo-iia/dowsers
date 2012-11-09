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
import java.util.Arrays;
import java.util.Collection;

import org.intelligentsia.dowsers.core.Identified;
import org.intelligentsia.keystone.api.artifacts.Version;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.reference.References;

/**
 * A {@link MetaEntity} define:
 * <ul>
 * <li>name: an entity class name</li>
 * <li>metaAttributes: entity meta attributes @see {@link MetaAttribute}.</li>
 * <li>version: meta entity version</li>
 * </ul>
 * A {@link MetaEntity} have a natural order which follow their version
 * attribute.
 * 
 * <code>
 * 'Tis written: "In the beginning was the Word!"
 * Here now I'm balked! Who'll put me in accord?
 * It is impossible, the Word so high to prize,
 * I must translate it otherwise
 * If I am rightly by the Spirit taught.
 * 'Tis written: In the beginning was the Thought!
 * Consider well that line, the first you see,
 * That your pen may not write too hastily!
 * Is it then Thought that works, creative, hour by hour?
 * Thus should it stand: In the beginning was the Power!
 * Yet even while I write this word, I falter,
 * For something warns me, this too I shall alter.
 * The Spirit's helping me! I see now what I need
 * And write assured: In the beginning was the Deed!
 * </code>
 * <p>
 * Goethe, Faust, Faust Study
 * </p>
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MetaEntity implements Identified<Reference>, Serializable, Comparable<MetaEntity>, Entity {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = 5799292993506157623L;
	/**
	 * Identity.
	 */
	@JsonProperty
	private final Reference identity;

	/**
	 * Meta entity name.
	 */
	@JsonProperty
	private final String name;

	/**
	 * Meta entity version.
	 */
	@JsonProperty
	private final Version version;

	/**
	 * {@link MetaAttributeCollection}.
	 */
	@JsonProperty
	private final MetaAttributeCollection metaAttributes;

	/**
	 * Build a new instance of {@link MetaEntity}.
	 * 
	 * @param identity
	 *            MetaEntity identity
	 * @param metaEntityContext
	 *            meta entity Context
	 * @param name
	 *            entity name
	 * @param version
	 *            entity meta definition version
	 * @param metaAttributes
	 *            collection of {@link MetaEntity} define by version * @param
	 *            identity MetaAttribute's identity
	 * @throws NullPointerException
	 *             if name, version, metaAttributes is null
	 * @throws IllegalArgumentException
	 *             if name is empty of identify is not an identifier
	 */
	@JsonCreator
	private MetaEntity(@JsonProperty("identity") final Reference identity, @JsonProperty("name") final String name, @JsonProperty("version") final Version version, @JsonProperty("metaAttributes") final MetaAttributeCollection metaAttributes)
			throws NullPointerException, IllegalArgumentException {
		super();
		Preconditions.checkArgument(Preconditions.checkNotNull(identity).isIdentifier());
		this.identity = identity;
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		this.name = name;
		this.version = Preconditions.checkNotNull(version);
		this.metaAttributes = Preconditions.checkNotNull(metaAttributes);
	}

	@Override
	public Reference identity() {
		return identity;
	}

	/**
	 * Returns a textual class name of the entity.
	 * 
	 * @return non-<code>null</code>, empty or non-empty string
	 */
	public String name() {
		return name;
	}

	/**
	 * Define {@link MetaEntity} version of associated {@link Entity}.
	 * 
	 * @return {@link Version} instance.
	 */
	public Version version() {
		return version;
	}

	/**
	 * @return an {@link MetaAttributeCollection}.
	 */
	public MetaAttributeCollection metaAttributes() {
		return metaAttributes;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(getClass()).add("identity", identity()).add("name", name()).add("version", version()).add("metaAttributes", metaAttributes()).toString();
	}

	/**
	 * @return a new {@link Builder} instance.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
	 */
	public static class Builder {

		/**
		 * Identity.
		 */
		private Reference identity;
		/**
		 * Meta entity name.
		 */
		private String name;

		/**
		 * Meta entity version.
		 */
		private Version version;
		/**
		 * Set of meta properties.
		 */
		private final ImmutableSet.Builder<MetaAttribute> metaAttributes = ImmutableSet.builder();

		/**
		 * Build a new instance of MetaEntity.Builder.
		 */
		public Builder() {
			super();
			identity = References.newReference(MetaEntity.class);
		}

		/**
		 * Set identity.
		 * 
		 * @param identity
		 * @return this instance.
		 * @throws NullPointerException
		 *             if identity is null
		 * @throws IllegalArgumentException
		 *             if identity is not an identifier
		 */
		public Builder identity(final Reference identity) throws NullPointerException, IllegalArgumentException {
			Preconditions.checkArgument(Preconditions.checkNotNull(identity).isIdentifier());
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
		 * Set version.
		 * 
		 * @param version
		 * @return this instance.
		 * @throws NullPointerException
		 *             if version is null
		 */
		public Builder version(final Version version) throws NullPointerException {
			this.version = Preconditions.checkNotNull(version);
			return this;
		}

		/**
		 * Add a set of {@link MetaAttribute}.
		 * 
		 * @param metaAttributes
		 *            {@link Collection} of {@link MetaAttribute} to add.
		 * @return this instance.
		 * @throws NullPointerException
		 *             if metaAttributes is null
		 */
		public Builder metaAttributes(final MetaAttribute... metaAttributes) throws NullPointerException {
			this.metaAttributes.addAll(Arrays.asList(Preconditions.checkNotNull(metaAttributes)));
			return this;
		}

		/**
		 * Add a new instance of <code>MetaAttribute</code>.
		 * 
		 * @param name
		 *            attribute name
		 * @param valueClass
		 *            value class
		 * @return this instance.
		 * @throws NullPointerException
		 *             if name or valueClass is null
		 * @throws IllegalArgumentException
		 *             if name is empty
		 */
		public Builder metaAttribute(final String name, final Class<?> valueClass) throws NullPointerException, IllegalArgumentException {
			return metaAttribute(MetaAttribute.builder().name(name).valueClass(valueClass).build());
		}

		/**
		 * Add a new instance of <code>MetaAttribute</code>.
		 * 
		 * @param metaAttribute
		 * @return
		 * @throws NullPointerException
		 */
		public Builder metaAttribute(final MetaAttribute metaAttribute) throws NullPointerException {
			this.metaAttributes.add(Preconditions.checkNotNull(metaAttribute));
			return this;
		}

		/**
		 * @return a {@link MetaEntity} instance.
		 */
		public MetaEntity build() {
			return new MetaEntity(identity, name, version, new MetaAttributeCollection(metaAttributes.build()));
		}

	}

	@Override
	public int compareTo(final MetaEntity o) {
		return version.compareTo(o.version);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <Value> Value attribute(final String name) throws NullPointerException, IllegalArgumentException {
		if ("identity".endsWith(name)) {
			return (Value) identity();
		}
		if ("name".endsWith(name)) {
			return (Value) name();
		}
		if ("version".endsWith(name)) {
			return (Value) version();
		}
		if ("metaAttributes".endsWith(name)) {
			return (Value) metaAttributes();
		}
		return null;
	}

	@Override
	public <Value> Entity attribute(final String name, final Value value) throws NullPointerException, IllegalArgumentException {
		throw new IllegalArgumentException("MetaEntity is Immutable");
	}

	@Override
	public boolean contains(final String name) throws NullPointerException, IllegalArgumentException {
		return MetaModel.getMetaOfMetaEntity().attributeNames().contains(name);
	}

	@Override
	public ImmutableSet<String> attributeNames() {
		return MetaModel.getMetaEntityContext().definitionAttributeNames();
	}

	@Override
	public MetaEntityContext metaEntityContext() {
		return MetaModel.getMetaEntityContext();
	}
}
