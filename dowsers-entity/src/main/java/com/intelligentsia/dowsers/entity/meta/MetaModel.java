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

import org.intelligentsia.dowsers.core.reflection.ClassInformation;
import org.intelligentsia.keystone.api.artifacts.Version;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import com.intelligentsia.dowsers.entity.EntityDynamic;

/**
 * MetaModel define Meta model for of meta data.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public enum MetaModel {
	;

	/**
	 * Meta model version.
	 */
	public static final Version VERSION = new Version(1, 0, 0);

	/**
	 * Attribute meta model.
	 */
	private static final MetaEntityContext metaAttributeContext;

	/**
	 * Entity meta model.
	 */
	private static final MetaEntityContext metaEntityContext;

	private static final MetaEntityContext entityDynamicModel;

	static {
		metaAttributeContext = MetaEntityContext.builder().definition(new MetaEntity.Builder(). // definition
				name(MetaAttribute.class.getName()).version(VERSION)
				// identity
				.addMetaAttribute("identity", String.class)
				// name
				.addMetaAttribute("name", String.class)
				// value
				.addMetaAttribute("valueClass", ClassInformation.class)
				// default value
				.addMetaAttribute("defaultValue", Object.class).build()

		).build();

		ImmutableSet<MetaAttribute> attributes = ImmutableSet.of();

		metaEntityContext = MetaEntityContext.builder().definition(new MetaEntity.Builder(). // definition
				name(MetaEntity.class.getName()).version(VERSION)
				// identity
				.addMetaAttribute("identity", String.class)
				// name
				.addMetaAttribute("name", String.class)
				// version
				.addMetaAttribute("version", Version.class, MetaModel.VERSION)
				// metaAttributes
				.metaAttributes(MetaAttribute.builder().name("metaAttributes").valueClass(new ClassInformation(attributes)).build()).build()).build();

		entityDynamicModel = MetaEntityContext.builder().definition(new MetaEntity.Builder(). // definition
				name(EntityDynamic.class.getName()).version(VERSION)
				// identity
				.addMetaAttribute("identity", String.class).build()).build();
	}

	/**
	 * @return {@link MetaEntityContext} instance of {@link MetaAttribute}.
	 */
	public static MetaEntityContext getMetaAttributModel() {
		return metaAttributeContext;
	}

	/**
	 * @return {@link MetaEntityContext} instance of {@link MetaEntity}
	 */
	public static MetaEntityContext getMetaEntityModel() {
		return metaEntityContext;
	}

	public static MetaEntityContext getEntityDynamicModel() {
		return entityDynamicModel;
	}
}
