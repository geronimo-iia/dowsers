package com.intelligentsia.dowsers.entity.model;

import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaModel;

/**
 * Person.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface Person {

	public static MetaEntity META = MetaEntity.builder().name(Person.class.getName()).version(MetaModel.VERSION). //
			addMetaAttribute("firstName", String.class).//
			addMetaAttribute("LastName", String.class).//
			addMetaAttribute("yearOld", Integer.class)//
			.build();

	String getFirstName();

	String getLastName();

	Integer getYearOld();

	void setFirstName(String name);

	void setLastName(String name);

	void setYearOld(Integer years);

}
