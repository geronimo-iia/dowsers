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
package org.intelligentsia.dowsers.model.meta;

import org.intelligentsia.dowsers.core.ReadOnlyIterator;

/**
 * A {@link MetaEntity} define:
 * <ul>
 * <li>an entity name</li>
 * <li>entity properties metadata @see {@link MetaProperty}.</li>
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
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface MetaEntity {

	/**
	 * Returns a textual name of the entity.
	 * 
	 * @return non-<code>null</code>, empty or non-empty string
	 */
	String getName();

	/**
	 * @param name
	 *            property name
	 * @return {@see Boolean#TRUE} if this entity has specified named property.
	 * @throws NullPointerException
	 *             if name is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 */
	boolean contains(String name) throws NullPointerException, IllegalArgumentException;

	/**
	 * 
	 * @param name
	 *            property name
	 * @return a {@link MetaProperty} instance with specified name or null if
	 *         none is found.
	 * @throws NullPointerException
	 *             if name is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 */
	MetaProperty getMetaProperty(String name) throws NullPointerException, IllegalArgumentException;

	/**
	 * @return a {@link ReadOnlyIterator} on property name.
	 */
	ReadOnlyIterator<String> getMetaPropertyNames();

	/**
	 * @return an read only {@link ReadOnlyIterator} on {@link MetaProperty}.
	 */
	ReadOnlyIterator<MetaProperty> getMetaProperties();

}