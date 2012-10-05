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

import java.util.Set;

import org.intelligentsia.dowsers.core.ReadOnlyIterator;
import org.intelligentsia.dowsers.entity.Entity;
import org.intelligentsia.keystone.api.artifacts.Version;

/**
 * {@link MetaEntityContext} define methods to gain access on {@link MetaEntity}
 * of an {@link Entity}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public interface MetaEntityContext extends MetaEntity {

	/**
	 * @return a not null {@link Version} which define root {@link MetaEntity}.
	 */
	Version getRootVersion();

	/**
	 * @return an ordered {@link ReadOnlyIterator} on {@link Version} which
	 *         compose this {@link MetaEntityContext}. First item will always be
	 *         the root {@link MetaEntity} definition.
	 */
	ReadOnlyIterator<Version> getVersions();

	/**
	 * @param version
	 * @return {@link MetaEntity} instance for specified version or null if none
	 *         is found.
	 * @throws NullPointerException
	 *             if version is null
	 */
	MetaEntity getMetaEntity(Version version) throws NullPointerException;

	/**
	 * @return a immutable {@link Set} of property names define by all extended
	 *         {@link MetaEntity} version.
	 */
	Set<String> getAllExtendedPropertyNames();
}
