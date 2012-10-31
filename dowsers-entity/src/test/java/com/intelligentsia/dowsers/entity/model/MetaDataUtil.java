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
package com.intelligentsia.dowsers.entity.model;

import com.intelligentsia.dowsers.entity.EntityFactories;
import com.intelligentsia.dowsers.entity.EntityFactories.EntityFactory;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProvider;
import com.intelligentsia.dowsers.entity.meta.MetaModel;
import com.intelligentsia.dowsers.entity.meta.provider.MetaEntityContextProviderSupport;
import com.intelligentsia.dowsers.entity.meta.provider.MetaEntityProviders;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * MetaDataUtil.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public enum MetaDataUtil {

	;

	public static final String ORDER = "order";
	public static final String DESCRIPTION = "a blablablabalbablbalablabb";
	public static final String NAME = "Hello John";

	public static final Reference IDENTIFIER = Reference.newReference(CustomizableSampleEntity.class, "4c8b03dd-908a-4cad-8d48-3c7277d44ac9");

	private final static MetaEntityContextProvider metaEntityContextProviderSupport = MetaEntityContextProviderSupport.builder(). //
			add(SampleEntity.class, MetaEntity.builder().name(SampleEntity.class.getName()).version(MetaModel.VERSION). // attributes
					metaAttribute("name", String.class).//
					metaAttribute("description", String.class).build()) //
			.add(CustomizableSampleEntity.class, MetaEntity.builder().name(CustomizableSampleEntity.class.getName()).version(MetaModel.VERSION). // attributes
					metaAttribute("name", String.class).//
					metaAttribute("description", String.class).//
					metaAttribute("order", Long.class).build()) //
			.add(Organization.class, Organization.META)//
			.add(Person.class, Person.META).build(MetaEntityProviders.newMetaEntityProviderAnalyzer());

	public static MetaEntityContextProvider getMetaEntityContextProvider() {
		return metaEntityContextProviderSupport;
	}

	public static CustomizableSampleEntity getCustomizableSampleEntity() {
		final EntityFactory<CustomizableSampleEntity> factory = EntityFactories.newEntityProxyDynamicFactory(CustomizableSampleEntity.class, MetaDataUtil.getMetaEntityContextProvider().find(Reference.newReference(CustomizableSampleEntity.class)));
		final CustomizableSampleEntity entity = factory.newInstance(MetaDataUtil.IDENTIFIER);
		entity.setName(MetaDataUtil.NAME);
		entity.setDescription(MetaDataUtil.DESCRIPTION);
		entity.attribute(MetaDataUtil.ORDER, 1L);
		return entity;
	}

}
