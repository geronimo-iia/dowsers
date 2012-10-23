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
package com.intelligentsia.dowsers.entity;

import java.net.URI;
import java.util.Map;

import com.google.common.collect.Maps;
import com.intelligentsia.dowsers.entity.meta.MetaAttribute;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProvider;
import com.intelligentsia.dowsers.entity.meta.MetaModel;
import com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity;
import com.intelligentsia.dowsers.entity.model.SampleEntity;

public class MockMetaEntityContextRepository implements MetaEntityContextProvider {

	Map<String, MetaEntityContext> map = Maps.newHashMap();

	public MockMetaEntityContextRepository() {
		map.put(MetaAttribute.class.getName(), MetaModel.getMetaAttributModel());
		map.put(MetaEntity.class.getName(), MetaModel.getMetaEntityModel());
		map.put(EntityDynamic.class.getName(), MetaModel.getEntityDynamicModel());

		map.put(SampleEntity.class.getName(), MetaEntityContext.builder().definition( // definition
				MetaEntity.builder().name(SampleEntity.class.getName()).version(MetaModel.VERSION). // attributes
						addMetaAttribute("name", String.class).//
						addMetaAttribute("description", String.class).build())//
				.build());

		map.put(CustomizableSampleEntity.class.getName(), MetaEntityContext.builder().definition( // definition
				MetaEntity.builder().name(CustomizableSampleEntity.class.getName()).version(MetaModel.VERSION). // attributes
						addMetaAttribute("name", String.class).//
						addMetaAttribute("description", String.class).build())//
				.build());
	}

	@Override
	public MetaEntityContext find(final URI reference) throws IllegalArgumentException, NullPointerException {
		final MetaEntityContext context = map.get(Reference.getEntityPart(reference));
		if (context == null) {
			throw new IllegalArgumentException("no context found for " + reference);
		}
		return context;
	}
}
