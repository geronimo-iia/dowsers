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
package com.intelligentsia.dowsers.entity.meta;

import java.util.Arrays;
import java.util.Collection;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * <code>ResourceMetaEntityProvider</code> implements {@link MetaEntityProvider}
 * using a {@link Multimap} as underlying implementation. .
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 * 
 */
public class ResourceMetaEntityProvider implements MetaEntityProvider {

	private final Multimap<Reference, MetaEntity> definitions;

	/**
	 * Build a new instance of <code>ResourceMetaEntityProvider</code>.
	 * 
	 * @param definitions
	 *            a {@link Multimap} of {@link Reference}, {@link MetaEntity}.
	 * @throws NullPointerException
	 *             if definitions is null
	 */
	public ResourceMetaEntityProvider(final Multimap<Reference, MetaEntity> definitions) throws NullPointerException {
		super();
		this.definitions = Preconditions.checkNotNull(definitions);
	}

	/**
	 * @see com.intelligentsia.dowsers.entity.meta.MetaEntityProvider#find(com.intelligentsia.dowsers.entity.reference.Reference)
	 */
	@Override
	public Collection<MetaEntity> find(final Reference reference) throws NullPointerException {
		return definitions.get(reference);
	}

	public static Builder builder() {
		return new Builder();
	}

	/**
	 * <code>Builder</code>.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
	 * 
	 */
	public static class Builder {
		private final Multimap<Reference, MetaEntity> definitions = HashMultimap.create();

		public Builder() {
			super();
		}

		public Builder add(final Reference reference, final MetaEntity... metaEntity) throws NullPointerException {
			return add(reference, Arrays.asList(Preconditions.checkNotNull(metaEntity)));
		}

		public Builder add(final Reference reference, final Collection<MetaEntity> metaEntities) throws NullPointerException {
			definitions.putAll(Preconditions.checkNotNull(reference), Preconditions.checkNotNull(metaEntities));
			return this;
		}

		public ResourceMetaEntityProvider build() {
			return new ResourceMetaEntityProvider(definitions);
		}
	}
}
