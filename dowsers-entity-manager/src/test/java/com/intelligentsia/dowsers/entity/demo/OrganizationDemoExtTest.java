package com.intelligentsia.dowsers.entity.demo;

import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaModel;
import com.intelligentsia.dowsers.entity.meta.provider.MetaEntityContextProviderSupport;
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
