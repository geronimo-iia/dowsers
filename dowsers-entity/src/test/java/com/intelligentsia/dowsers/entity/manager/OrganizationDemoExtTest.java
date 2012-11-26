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
package com.intelligentsia.dowsers.entity.manager;

import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProviderSupport;
import com.intelligentsia.dowsers.entity.meta.MetaModel;
import com.intelligentsia.dowsers.entity.meta.provider.MetaEntityProviders;
import com.intelligentsia.dowsers.entity.model.Organization;

public class OrganizationDemoExtTest extends OrganizationDemoTest {

	public static MetaEntity META = MetaEntity.builder().name(Organization.class.getName()).version(MetaModel.VERSION). //
			metaAttribute("description", String.class).//
			build();

	@Override
	protected MetaEntityContextProviderSupport newMetaEntityContextProvider() {
		return MetaEntityContextProviderSupport.builder()//
				.add(Organization.class, Organization.META, META)//
				.build(MetaEntityProviders.newMetaEntityProviderAnalyzer());
	}

	@Override
	protected Organization newOrganization() {
		final Organization organization = factory.newInstance();
		organization.name("Intelligents-ia");
		organization.attribute("description", "iia");
		return organization;
	}

}
