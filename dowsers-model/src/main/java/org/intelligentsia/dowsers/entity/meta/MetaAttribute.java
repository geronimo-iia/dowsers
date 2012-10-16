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

import java.io.Serializable;

import org.intelligentsia.dowsers.core.Identified;
import org.intelligentsia.dowsers.core.reflection.ClassInformation;

/**
 * MetaAttribute: Ash nazg durbatulûk, ash nazg gimbatul, ash nazg thrakatulûk
 * agh burzum-ishi krimpatul.
 * 
 * {@link MetaAttribute} is composed of:
 * <ul>
 * <li>name: attribute's name</li>
 * <li>valueClass: attribute's class name</li>
 * <li>defaultValue: attribute's default value</li>
 * </ul>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public interface MetaAttribute extends Identified {

	/**
	 * Returns the attribute name.
	 * 
	 * @return non-<code>null</code> textual attribute name
	 */
	public String name();

	/**
	 * Returns the <code>{@link ClassInformation}</code> object representing the
	 * <code>Value</code> of attribute.
	 * 
	 * @return non-<code>null</code> <code>{@link ClassInformation}</code>
	 *         instance
	 */
	public ClassInformation valueClass();

	/**
	 * Returns the attribute default value.
	 * 
	 * @return <code>null</code> or non-<code>null</code> <code>Value</code>
	 *         instance
	 */
	public <Value extends Serializable> Value defaultValue();

}
