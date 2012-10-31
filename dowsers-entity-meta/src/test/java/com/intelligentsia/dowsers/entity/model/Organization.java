package com.intelligentsia.dowsers.entity.model;

import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaModel;

public interface Organization extends Entity {

	public static MetaEntity META = MetaEntity.builder().name(Organization.class.getName()).version(MetaModel.VERSION). //
			metaAttributes(MetaModel.getIdentityAttribute()).//
			metaAttribute("name", String.class).build();

	public String name();

	public void name(String name);

}
