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

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.intelligentsia.dowsers.core.IdentifierFactoryProvider;
import org.intelligentsia.keystone.api.artifacts.Version;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.EntityDynamic;

/**
 * A {@link MetaEntity} define:
 * <ul>
 * <li>name: an entity class name</li>
 * <li>metaAttributes: entity meta attributes @see {@link MetaAttribute}.</li>
 * <li>version: meta entity version</li>
 * </ul>
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
public class MetaEntity extends EntityDynamic {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = 5799292993506157623L;

	/**
	 * ImmutableMap of {@link MetaAttribute}.
	 */
	private final transient ImmutableMap<String, MetaAttribute> metaAttributes;

	/**
	 * Build a new instance of {@link MetaEntity}.
	 * 
	 * @param definition
	 *            {@link MetaEntity} to copy
	 * @throws NullPointerException
	 *             if definition is null
	 */
	public MetaEntity(final MetaEntity definition) throws NullPointerException {
		this(Preconditions.checkNotNull(definition).name(), definition.version(), definition.metaAttributes(), definition.identity());
	}

	/**
	 * Build a new instance of {@link MetaEntity}.
	 * 
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
	 *             if name is empty
	 */
	public MetaEntity(final String name, final Version version, final MetaAttributeCollection metaAttributes, final String identity) throws NullPointerException, IllegalArgumentException {
		super(identity);
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		super.attribute("name", name);
		super.attribute("version", Preconditions.checkNotNull(version));
		super.attribute("metaAttributes", Preconditions.checkNotNull(metaAttributes));
		// meta attributes
		ImmutableMap.Builder<String, MetaAttribute> builder = ImmutableMap.builder();
		Iterator<MetaAttribute> iterator = metaAttributes.iterator();
		while (iterator.hasNext()) {
			MetaAttribute attribute = iterator.next();
			builder.put(attribute.name(), attribute);
		}
		this.metaAttributes = builder.build();
	}

	/**
	 * Build a new instance of MetaEntity.
	 * 
	 * @param attributes
	 */
	public MetaEntity(Map<String, Object> attributes) {
		this(IdentifierFactoryProvider.generateNewIdentifier(), attributes);
	}

	/**
	 * Build a new instance of MetaEntity.java.
	 * 
	 * @param identity
	 * @param attributes
	 */
	public MetaEntity(String identity, Map<String, Object> attributes) {
		this((String) attributes.get("name"), (Version) attributes.get("version"), (MetaAttributeCollection) attributes.get("metaAttributes"), identity);
	}

	/**
	 * Returns a textual class name of the entity.
	 * 
	 * @return non-<code>null</code>, empty or non-empty string
	 */
	public String name() {
		return attribute("name");
	}

	/**
	 * Define {@link MetaEntity} version of associated {@link Entity}.
	 * 
	 * @return {@link Version} instance.
	 */
	public Version version() {
		return attribute("version");
	}

	/**
	 * @return an {@link ImmutableCollection} on {@link MetaAttribute}.
	 */
	public MetaAttributeCollection metaAttributes() {
		return attribute("metaAttributes");
	}

	/**
	 * @param name
	 *            attribute name
	 * @return {@see Boolean#TRUE} if this entity has specified named attribute.
	 * @throws NullPointerException
	 *             if name is null
	 */
	public boolean containsMetaAttribute(String name) throws NullPointerException {
		return metaAttributes.containsKey(name);
	}

	/**
	 * @return a {@link ImmutableSet} of meta attributes names .
	 */
	public ImmutableSet<String> metaAttributeNames() {
		return metaAttributes.keySet();
	}

	/**
	 * Get {@link MetaAttribute} of specified name.
	 * 
	 * @param attributeName
	 *            attribute name
	 * @return {@link MetaAttribute} instance or specified name or null if none
	 *         is found
	 * @throws NullPointerException
	 *             if attributName id null
	 * @throws {@link IllegalArgumentException} if attributName is empty
	 */
	public MetaAttribute metaAttribute(String attributeName) throws NullPointerException, IllegalArgumentException {
		return metaAttributes.get(attributeName);
	}

	/**
	 * Always throw IllegalStateException:a {@link MetaEntity} is Immutable.
	 * 
	 * @throw IllegalStateException
	 */
	@Override
	public <Value> Entity attribute(final String name, final Value value) throws IllegalStateException {
		throw new IllegalStateException("MetaEntity is Immutable");
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
		private String identity;
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
		private ImmutableSet.Builder<MetaAttribute> metaAttributes = ImmutableSet.builder();

		/**
		 * Build a new instance of MetaEntity.Builder.
		 */
		public Builder() {
			super();
			identity = IdentifierFactoryProvider.generateNewIdentifier();
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
		 * Add a new instance of <code>MetaAttributeDefinition</code>.
		 * 
		 * @param name
		 *            attribute name
		 * @param valueClass
		 *            value class
		 * @param defaultValue
		 *            default value
		 * @return this instance.
		 * @throws NullPointerException
		 *             if name or valueClass is null
		 * @throws IllegalArgumentException
		 *             if name is empty
		 * @throws IllegalStateException
		 *             if value is not assignable to specified value class
		 */
		public Builder addMetaAttribute(final String name, final Class<?> valueClass, final Object defaultValue) throws NullPointerException, IllegalArgumentException, IllegalStateException {
			this.metaAttributes.add(new MetaAttribute(name, valueClass, defaultValue));
			return this;
		}

		/**
		 * Add a new instance of <code>MetaAttributeDefinition</code>.
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
		public Builder addMetaAttribute(final String name, final Class<?> valueClass) throws NullPointerException, IllegalArgumentException {
			this.metaAttributes(new MetaAttribute(name, valueClass));
			return this;
		}

		public MetaEntity build() {
			return new MetaEntity(name, version, new MetaAttributeCollection(metaAttributes.build()), identity);
		}

	}
}
