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
package org.intelligentsia.dowsers.entity.io.serializer;
 
import org.intelligentsia.dowsers.entity.dynamic.EntityDynamicSupport;
import org.intelligentsia.dowsers.entity.meta.MetaAttributeDefinition;
import org.intelligentsia.dowsers.entity.meta.MetaEntityContextDefinition;
import org.intelligentsia.dowsers.entity.meta.MetaEntityContextRepository;
import org.intelligentsia.dowsers.entity.meta.MetaEntityDefinition;

import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * EntityJsonModule.
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class EntityJsonModule extends SimpleModule {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = 1421909448372149344L;

	/**
	 * Build a new instance of EntityJsonModule.java.
	 * 
	 * @param metaEntityContextRepository
	 *            {@link MetaEntityContextRepository} instance
	 */
	public EntityJsonModule(final MetaEntityContextRepository metaEntityContextRepository) {
		super("entity-json-module", VersionUtil.versionFor(EntityJsonModule.class));

		addDeserializer(EntityDynamicSupport.class, new EntityDynamicJsonDeSerializer(metaEntityContextRepository));
		addSerializer(EntityDynamicSupport.class, new EntityDynamicJsonSerializer());

		addDeserializer(MetaAttributeDefinition.class, new MetaAttributeDefinitionDeSerializer());
		addSerializer(MetaAttributeDefinition.class, new MetaAttributeDefinitionSerializer());

		addDeserializer(MetaEntityDefinition.class, new MetaEntityDefinitionDeserializer());
		addSerializer(MetaEntityDefinition.class, new MetaEntityDefinitionSerializer());

		addDeserializer(MetaEntityContextDefinition.class, new MetaEntityContextDefinitionDeSerializer());
		addSerializer(MetaEntityContextDefinition.class, new MetaEntityContextDefinitionSerializer());

	}

}
