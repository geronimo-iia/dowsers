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

import java.util.Set;

import org.intelligentsia.keystone.kernel.api.artifacts.Version;
import org.springframework.beans.factory.FactoryBean;

import com.google.common.collect.Sets;
import com.intelligentsia.dowsers.entity.meta.MetaAttribute;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaModel;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * MetaEntityFactory implements {@link FactoryBean} for {@link MetaEntity}
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class MetaEntityFactory implements FactoryBean<MetaEntity> {

	/**
	 * Identity.
	 */
	private Reference identity;
	/**
	 * Meta entity name.
	 */
	private String name;
	/**
	 * Meta entity version.
	 */
	private Version version;
	/**
	 * Set of meta properties.
	 */
	private Set<MetaAttribute> metaAttributes = Sets.newHashSet();

	public MetaEntityFactory() {
		super();
	}

	@Override
	public MetaEntity getObject() throws Exception {
		final MetaEntity.Builder builder = MetaEntity.builder().name(name);
		builder.version(version == null ? MetaModel.VERSION : version);
		if (metaAttributes != null) {
			for (final MetaAttribute attribute : metaAttributes) {
				builder.metaAttribute(attribute);
			}
		}
		if (identity != null) {
			builder.identity(identity);
		}
		return builder.build();
	}

	@Override
	public Class<?> getObjectType() {
		return MetaEntity.class;
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

	public Version getVersion() {
		return version;
	}

	public void setVersion(final Version version) {
		this.version = version;
	}

	public Set<MetaAttribute> getMetaAttributes() {
		return metaAttributes;
	}

	public void setMetaAttributes(final Set<MetaAttribute> metaAttributes) {
		this.metaAttributes = metaAttributes;
	}

}
