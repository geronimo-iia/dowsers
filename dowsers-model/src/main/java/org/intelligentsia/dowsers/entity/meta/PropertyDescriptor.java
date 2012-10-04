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

/**
 * {@link PropertyDescriptor} describe value object properties.
 * 
 * @param <Value>
 *            the value class generic parameter
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface PropertyDescriptor {
	/**
	 * Returns the property name.
	 * 
	 * @return non-<code>null</code> textual property identifier
	 */
	String getName();

	/**
	 * Returns the <code>{@link Class}</code> object representing the
	 * <code>Value</code> generic parameter.
	 * 
	 * @return non-<code>null</code> <code>{@link Class}</code> instance
	 */
	Class<?> getValueClass();

	/**
	 * Returns the property default value.
	 * 
	 * @return <code>null</code> or non-<code>null</code> <code>Value</code>
	 *         instance
	 */
	<Value> Value getDefaultValue();
}
