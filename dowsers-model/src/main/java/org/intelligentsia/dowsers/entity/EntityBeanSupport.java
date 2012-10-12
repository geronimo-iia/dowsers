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
package org.intelligentsia.dowsers.entity;

import org.intelligentsia.dowsers.entity.meta.MetaEntityContext;

/**
 * EntityBeanSupport extends {@link EntitySupport}. This is teh base class for
 * all POJO {@link Entity}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityBeanSupport extends EntitySupport {

	public EntityBeanSupport(final String identity, final MetaEntityContext metaEntityContext) throws NullPointerException {
		super(identity, metaEntityContext);
	}

	@Override
	public <Value> Value attribute(final String name) throws NullPointerException {
		// TODO implements this
		return null;
	}

	@Override
	public <Value> Entity attribute(final String name, final Value value) throws NullPointerException {
		// TODO implements this
		return this;
	}

}
