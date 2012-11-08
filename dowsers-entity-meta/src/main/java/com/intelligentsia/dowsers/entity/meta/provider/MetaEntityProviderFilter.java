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
package com.intelligentsia.dowsers.entity.meta.provider;

import java.util.Collection;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaEntityProvider;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * MetaEntityProviderFilter apply a {@link Predicate} onto delegated result.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class MetaEntityProviderFilter extends MetaEntityProviderDecorator {

	private final Predicate<MetaEntity> predicate;

	/**
	 * Build a new instance of MetaEntityProviderFilter.java.
	 * 
	 * @param metaEntityProvider
	 * @param predicate
	 * @throws NullPointerException
	 *             if one of parameters is null
	 */
	public MetaEntityProviderFilter(final MetaEntityProvider metaEntityProvider, final Predicate<MetaEntity> predicate) throws NullPointerException {
		super(metaEntityProvider);
		this.predicate = Preconditions.checkNotNull(predicate);
	}

	@Override
	public Collection<MetaEntity> find(final Reference reference) throws NullPointerException {
		return Collections2.filter(metaEntityProvider.find(reference), predicate);
	}

}
