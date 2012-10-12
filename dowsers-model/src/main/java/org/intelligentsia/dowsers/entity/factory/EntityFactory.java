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
package org.intelligentsia.dowsers.entity.factory;

import org.intelligentsia.dowsers.entity.Entity;
import org.intelligentsia.dowsers.entity.meta.MetaEntityContextNotFoundException;

/**
 * EntityFactory declare methods to instanciate an {@link Entity}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface EntityFactory {

	/**
	 * Build a new instance of an Entity.
	 * 
	 * @param className
	 *            entity class Name
	 * @param identity
	 *            identity
	 * @return instance of <code>T</code>
	 * @throws NullPointerException
	 *             if className or identity is null
	 * @throws MetaEntityContextNotFoundException
	 *             if no meta was found for specified entity class name
	 */
	public <T extends Entity> T newInstance(Class<?> className, final String identity) throws NullPointerException, MetaEntityContextNotFoundException;
}
