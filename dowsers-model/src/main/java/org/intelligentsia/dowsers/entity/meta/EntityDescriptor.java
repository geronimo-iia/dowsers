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
package org.intelligentsia.dowsers.entity.meta;

import java.util.Iterator;

import org.intelligentsia.dowsers.entity.Entity;
import org.intelligentsia.keystone.api.artifacts.Version;

/**
 * {@link EntityDescriptor} represents a definition for an
 * <code>{@link Entity}</code>.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface EntityDescriptor extends Iterable<PropertyDescriptor> {
	/**
	 * Returns a textual name of the entity.
	 * 
	 * @return non-<code>null</code>, empty or non-empty string
	 */
	String getName();

	/**
	 * Define domain version of {@link Entity}. A domain version tag a set of
	 * entity meta definition.
	 * 
	 * @return {@link Version} instance.
	 */
	Version getDomainVersion();

	/**
	 * @return {@see Boolean#TRUE} if this entity can be extended.
	 */
	boolean isExtendable();

	/**
	 * Return extended domain version associated.
	 * 
	 * @return {@link Version} instance.
	 * @throws IllegalStateException
	 *             if !{@link #isExtendable()}
	 */
	Version getExtendedDomainVersion() throws IllegalStateException;

	/**
	 * 
	 * @param name
	 *            property name
	 * @return {@see Boolean#TRUE} if this entity has specified named property.
	 * @throws NullPointerException
	 *             if name is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 */
	boolean contains(final String name) throws NullPointerException, IllegalArgumentException;

	/**
	 * 
	 * @param name
	 *            property name
	 * @return
	 * @throws NullPointerException
	 *             if name is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 * @throws IllegalStateException
	 *             if no {@link PropertyDescriptor} was found
	 */
	PropertyDescriptor getPropertyDescriptor(final String name) throws NullPointerException, IllegalArgumentException, IllegalStateException;

	/**
	 * Iterate on name of domain define properties descriptor.
	 * 
	 * @return an {@link Iterator} on name of Domain defined
	 *         {@link PropertyDescriptor}.
	 */
	Iterator<String> getDomainPropertyName();

	/**
	 * Iterate on name of extended domain define properties descriptor.
	 * 
	 * @return an {@link Iterator} on name of extended domain defined
	 *         {@link PropertyDescriptor}.
	 */
	Iterator<String> getExtendedPropertyName();
}
