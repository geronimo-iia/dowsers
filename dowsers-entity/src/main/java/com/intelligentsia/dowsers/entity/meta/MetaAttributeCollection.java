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
import java.util.Iterator;

import org.intelligentsia.keystone.api.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;

/**
 * MetaAttributeCollection is a {@link ImmutableCollection} of
 * {@link MetaAttribute}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MetaAttributeCollection implements Iterable<MetaAttribute>, Serializable {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -4410480905712842651L;

	/**
	 * {@link ImmutableSet} {@link MetaAttribute}.
	 */
	@JsonProperty("items")
	private final ImmutableSet<MetaAttribute> metaAttributes;

	/**
	 * Build a new instance of <code>MetaAttributeCollection</code>.
	 * 
	 * @param metaAttributes
	 */
	@JsonCreator
	public MetaAttributeCollection(@JsonProperty("items") final ImmutableSet<MetaAttribute> metaAttributes) {
		super();
		this.metaAttributes = Preconditions.checkNotNull(metaAttributes);
	}

	/**
	 * @param name
	 * @return a {@link MetaAttribute} with specified name
	 * @throws NullPointerException
	 *             if name is null
	 * @throws IllegalArgumentException
	 *             if no {@link MetaAttribute} with specified name exists.
	 */
	public MetaAttribute metaAttribute(final String name) throws NullPointerException, IllegalArgumentException {
		Preconditions.checkNotNull(name);
		final Iterator<MetaAttribute> iterator = metaAttributes.iterator();
		while (iterator.hasNext()) {
			final MetaAttribute metaAttribute = iterator.next();
			if (name.equals(metaAttribute.name())) {
				return metaAttribute;
			}
		}
		throw new IllegalArgumentException(StringUtils.format("MetaAttribute %s not found", name));
	}

	@Override
	public Iterator<MetaAttribute> iterator() {
		return metaAttributes.iterator();
	}

	public ImmutableSet<MetaAttribute> getMetaAttributes() {
		return metaAttributes;
	}

	@JsonIgnore
	public boolean isEmpty() {
		return metaAttributes.isEmpty();
	}

	public int size() {
		return metaAttributes.size();
	}

}
