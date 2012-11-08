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

import java.util.Arrays;
import java.util.Collection;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaEntityProvider;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * <code>MetaEntityProviderComposite</code> aggregate {@link MetaEntity}
 * definition from multiple {@link MetaEntityProvider}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 * 
 */
public class MetaEntityProviderComposite implements MetaEntityProvider {

	/**
	 * Aggregation of {@link MetaEntityProvider}.
	 */
	private final Collection<MetaEntityProvider> metaEntityProviders;

	/**
	 * Build a new instance of <code>MetaEntityProviderComposite</code>.
	 * 
	 * @param metaEntityProviders
	 *            {@link MetaEntityProvider}'s
	 * @throws NullPointerException
	 *             if metaEntityProviders is null
	 */
	public MetaEntityProviderComposite(final MetaEntityProvider... metaEntityProviders) throws NullPointerException {
		this(Arrays.asList(metaEntityProviders));
	}

	/**
	 * Build a new instance of <code>MetaEntityProviderComposite</code>.
	 * 
	 * @param metaEntityProviders
	 *            collection of {@link MetaEntityProvider}
	 * @throws NullPointerException
	 *             if metaEntityProviders is null
	 */
	public MetaEntityProviderComposite(final Collection<MetaEntityProvider> metaEntityProviders) throws NullPointerException {
		super();
		this.metaEntityProviders = Preconditions.checkNotNull(metaEntityProviders);
	}

	@Override
	public Collection<MetaEntity> find(final Reference reference) throws NullPointerException {
		Preconditions.checkNotNull(reference);
		final Collection<MetaEntity> result = Sets.newLinkedHashSet();
		for (final MetaEntityProvider provider : metaEntityProviders) {
			result.addAll(provider.find(reference));
		}
		return result;
	}

}
