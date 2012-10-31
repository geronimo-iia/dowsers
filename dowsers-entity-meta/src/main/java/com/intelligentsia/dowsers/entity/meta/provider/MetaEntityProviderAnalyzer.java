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
/**
 * 
 */
package com.intelligentsia.dowsers.entity.meta.provider;

import java.util.Collection;

import org.intelligentsia.dowsers.core.reflection.ClassInformation;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * <code>MetaEntityProviderAnalyzer</code> implements {@link MetaEntityProvider}
 * by analyzing specified {@link Entity} class in {@link Reference}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 * 
 */
public class MetaEntityProviderAnalyzer implements MetaEntityProvider {

	/**
	 * @see com.intelligentsia.dowsers.entity.meta.provider.MetaEntityProvider#find(com.intelligentsia.dowsers.entity.reference.Reference)
	 */
	@Override
	public Collection<MetaEntity> find(final Reference reference) throws NullPointerException {
		Preconditions.checkNotNull(reference);
		final Collection<MetaEntity> result = Sets.newHashSet();
		// obtain class information
		final ClassInformation classInformation = ClassInformation.parse(reference.getEntityClassName());
		// analyze
		final MetaEntity metaEntity = analyze(classInformation);
		if (metaEntity != null) {
			result.add(metaEntity);
		}
		return result;
	}

	public MetaEntity analyze(final ClassInformation classInformation) throws NullPointerException {
		// TODO implements it
		return null;
	}

}
