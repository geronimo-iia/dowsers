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
package com.intelligentsia.dowsers.entity.meta.support;

import java.util.Collection;

import org.intelligentsia.keystone.api.artifacts.Version;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.EntityDynamic;
import com.intelligentsia.dowsers.entity.meta.MetaAttribute;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;

/**
 * {@link MetaEntityDefinition} is a version of an {@link MetaEntity}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MetaEntityDefinition extends EntityDynamic implements MetaEntity {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = 5799292993506157623L;

	/**
	 * Meta entity name.
	 */
	private final String name;

	/**
	 * Meta entity version.
	 */
	private final Version version;
	/**
	 * Collection of {@link MetaAttribute}.
	 */
	private final transient ImmutableCollection<MetaAttribute> metaAttributes;

	/**
	 * Build a new instance of {@link MetaEntityDefinition}.
	 * 
	 * @param definition
	 *            {@link MetaEntityDefinition} to copy
	 * @throws NullPointerException
	 *             if definition is null
	 */
	public MetaEntityDefinition(final MetaEntityDefinition definition) throws NullPointerException {
		this(Preconditions.checkNotNull(definition).name, definition.version, definition.metaAttributes(), definition.identity());
	}

	/**
	 * Build a new instance of {@link MetaEntityDefinition}.
	 * 
	 * @param name
	 *            entity name
	 * @param version
	 *            entity meta definition version
	 * @param metaAttributes
	 *            collection of {@link MetaEntity} define by version * @param
	 *            identity MetaAttribute's identity
	 * @throws NullPointerException
	 *             if name, version, metaProperties is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 */
	public MetaEntityDefinition(final String name, final Version version, final Collection<MetaAttribute> metaAttributes, final String identity) throws NullPointerException, IllegalArgumentException {
		super(identity);
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		this.name = name;
		this.version = Preconditions.checkNotNull(version);
		// meta attributes
		this.metaAttributes = ImmutableSet.copyOf(Preconditions.checkNotNull(metaAttributes));
		for (final MetaAttribute metaAttribute : metaAttributes) {
			super.attribute(metaAttribute.name(), metaAttribute);
		}
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public Version version() {
		return version;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <Value> Value attribute(final String name) throws NullPointerException, IllegalArgumentException {
		Preconditions.checkState(!"".equals(Preconditions.checkNotNull(name)));
		if ("name".equals(name)) {
			return (Value) name();
		} else if ("version".equals(name)) {
			return (Value) version();
		} else if ("metaAttributes".equals(name)) {
			return (Value) metaAttributes();
		}
		return null;
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
	public ImmutableCollection<MetaAttribute> metaAttributes() {
		return metaAttributes;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(getClass()).add("name", name).add("version", version).add("metaAttributes", attributes().values()).toString();
	}

}
