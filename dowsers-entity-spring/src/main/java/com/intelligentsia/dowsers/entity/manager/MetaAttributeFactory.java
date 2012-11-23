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

import org.springframework.beans.factory.FactoryBean;

import com.intelligentsia.dowsers.entity.meta.MetaAttribute;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * MetaAttributeFactory implements {@link FactoryBean} for {@link MetaAttribute}
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class MetaAttributeFactory implements FactoryBean<MetaAttribute> {

	private Reference identity;
	private String name;
	private Class<?> valueClass;

	public MetaAttributeFactory() {
		super();
	}

	@Override
	public MetaAttribute getObject() throws Exception {
		final MetaAttribute.Builder builder = MetaAttribute.builder().name(name).valueClass(valueClass);
		if (identity != null) {
			builder.identity(identity);
		}
		return builder.build();
	}

	@Override
	public Class<?> getObjectType() {
		return MetaAttribute.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public Reference getIdentity() {
		return identity;
	}

	public void setIdentity(final Reference identity) {
		this.identity = identity;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Class<?> getValueClass() {
		return valueClass;
	}

	public void setValueClass(final Class<?> valueClass) {
		this.valueClass = valueClass;
	}

}
