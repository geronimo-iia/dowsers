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

import org.intelligentsia.dowsers.core.reflection.ClassInformation;
import org.intelligentsia.keystone.api.artifacts.Version;

import com.google.common.collect.ImmutableCollection;

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
	private static final Version VERSION = new Version(1, 0, 0);

	/**
	 * Attribute meta model.
	 */
	private static final MetaEntityContext metaAttribute;

	/**
	 * Entity meta model.
	 */
	private static final MetaEntityContext metaEntity;

	static {
		metaAttribute = new MetaEntityContextBuilder(MetaAttribute.class.getName(), MetaModel.VERSION)
		// identity
				.metaAttribute("identity", String.class)
				// name
				.metaAttribute("name", String.class)
				// value TODO find class type
				.metaAttribute("valueClass", ClassInformation.class)
				// default value
				.metaAttribute("defaultValue", Object.class).build();

		metaEntity = new MetaEntityContextBuilder(MetaEntity.class.getName(), MetaModel.VERSION)
		// identity
				.metaAttribute("identity", String.class)
				// name
				.metaAttribute("name", String.class)
				// version
				.metaAttribute("version", Version.class, MetaModel.VERSION)
				// attributes TODO find class type
				.metaAttribute("metaAttributes", ImmutableCollection.class).build();
	}

	/**
	 * @return {@link MetaEntityContext} instance of {@link MetaAttribute}.
	 */
	public static MetaEntityContext getMetaAttributModel() {
		return metaAttribute;
	}

	/**
	 * @return {@link MetaEntityContext} instance of {@link MetaEntity}
	 */
	public static MetaEntityContext getMetaEntityModel() {
		return metaEntity;
	}
}
