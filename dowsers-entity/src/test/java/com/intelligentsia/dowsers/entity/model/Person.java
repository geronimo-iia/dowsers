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
import com.intelligentsia.dowsers.entity.meta.MetaModel;
import com.intelligentsia.dowsers.entity.validation.MetaCompliant;

/**
 * Person.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
@MetaCompliant
public interface Person {

	public static MetaEntity META = MetaEntity.builder().name(Person.class.getName()).version(MetaModel.VERSION). //
			metaAttributes(MetaModel.getIdentityAttribute()).//
			metaAttribute("firstName", String.class).//
			metaAttribute("lastName", String.class).//
			metaAttribute("yearOld", Integer.class).//
			metaAttribute("bot", Boolean.class)//
			.build();

	String getFirstName();

	String getLastName();

	Integer getYearOld();

	void setFirstName(String name);

	void setLastName(String name);

	void setYearOld(Integer years);

	Boolean isBot();

	void bot(Boolean bot);

}
