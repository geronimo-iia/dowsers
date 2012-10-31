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
import java.util.Collections;
import java.util.LinkedList;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * MetaEntityProviderFilterLastVersion select last version of delegated
 * {@link MetaEntityProvider}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class MetaEntityProviderFilterLastVersion extends MetaEntityProviderDecorator {

	/**
	 * Build a new instance of MetaEntityProviderFilterLastVersion.java.
	 * 
	 * @param metaEntityProvider
	 * @throws NullPointerException
	 */
	public MetaEntityProviderFilterLastVersion(final MetaEntityProvider metaEntityProvider) throws NullPointerException {
		super(metaEntityProvider);
	}

	@Override
	public Collection<MetaEntity> find(final Reference reference) throws NullPointerException {
		// call super
		final LinkedList<MetaEntity> metaEntities = Lists.newLinkedList(super.find(reference));
		// take first
		if (metaEntities.isEmpty()) {
			return ImmutableSet.of();
		} else {
			// sort
			Collections.sort(metaEntities);
			// take first
			return ImmutableSet.of(metaEntities.getFirst());
		}
	}
}
