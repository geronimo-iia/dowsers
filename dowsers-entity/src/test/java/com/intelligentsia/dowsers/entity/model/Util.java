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

import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProvider;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProviderSupport;
import com.intelligentsia.dowsers.entity.meta.MetaModel;

public enum Util {
	;

	public static MetaEntityContextProvider getMetaEntityContextProvider() {
		return MetaEntityContextProviderSupport.builder().addDefaultMetaEntityContext().add(SampleEntity.class, MetaEntityContext.builder().definition( // definition
				MetaEntity.builder().name(SampleEntity.class.getName()).version(MetaModel.VERSION). // attributes
						addMetaAttribute("name", String.class).//
						addMetaAttribute("description", String.class).build()).build()) //
				.add(CustomizableSampleEntity.class, MetaEntityContext.builder().definition( // definition
						MetaEntity.builder().name(CustomizableSampleEntity.class.getName()).version(MetaModel.VERSION). // attributes
								addMetaAttribute("name", String.class).//
								addMetaAttribute("description", String.class).build()).build());

	}
}
