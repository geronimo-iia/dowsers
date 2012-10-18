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

import org.intelligentsia.dowsers.core.ReadOnlyIterator;
import org.intelligentsia.keystone.api.artifacts.Version;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import com.intelligentsia.dowsers.entity.Entity;

/**
 * {@link MetaEntityContext} define methods to gain access on {@link MetaEntity}
 * of an {@link Entity}.
 * 
 * A {@link MetaEntityContext} is a {@link MetaEntity} composite of
 * {@link MetaEntity}. Each component is an extends of meta data given by super
 * {@link MetaEntity} instance.
 * 
 * We call version of {@link MetaEntity} definition the fisrst and initial
 * {@link MetaEntity} set to a {@link MetaEntityContext}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public interface MetaEntityContext extends Iterable<MetaAttribute> {

	/**
	 * Returns a textual class name of the entity.
	 * 
	 * @return non-<code>null</code>, empty or non-empty string
	 */
	String name();

	/**
	 * @return {@link Version} of {@link MetaEntity} definition.
	 */
	Version version();

	/**
	 * @return an ordered {@link ReadOnlyIterator} on {@link Version} which
	 *         compose this {@link MetaEntityContext}. First item will always be
	 *         the root {@link MetaEntity} definition of this
	 *         {@link MetaEntityContext}.
	 */
	ReadOnlyIterator<Version> versions();

	/**
	 * @param version
	 *            version
	 * @return {@see Boolean#TRUE} if this context has specified version of
	 *         {@link MetaEntity}.
	 * @throws NullPointerException
	 *             if version is null
	 */
	boolean containsVersion(Version version) throws NullPointerException;

	/**
	 * @param version
	 * @return {@link MetaEntity} instance for specified version or null if none
	 *         is found.
	 * @throws NullPointerException
	 *             if version is null
	 */
	ImmutableCollection<MetaAttribute> metaAttributes(Version version) throws NullPointerException;

	/**
	 * @param name
	 *            attribute name
	 * @return {@see Boolean#TRUE} if this entity has specified named attribute.
	 * @throws NullPointerException
	 *             if name is null
	 */
	boolean containsAttribute(String name) throws NullPointerException;

	/**
	 * @return a {@link ImmutableSet} of attributes names define by version of
	 *         {@link MetaEntity} definition.
	 */
	ImmutableSet<String> definitionAttributeNames();

	/**
	 * @return a {@link ImmutableCollection} of attributes names define by all
	 *         extended {@link MetaEntity} version.
	 */
	ImmutableSet<String> allExtendedAttributeNames();

}
