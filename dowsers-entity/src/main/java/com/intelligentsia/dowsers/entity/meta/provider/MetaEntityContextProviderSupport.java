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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.EntityDynamic;
import com.intelligentsia.dowsers.entity.meta.MetaAttribute;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProvider;
import com.intelligentsia.dowsers.entity.meta.MetaEntityProvider;
import com.intelligentsia.dowsers.entity.meta.MetaModel;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * MetaEntityContextProviderSupport.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class MetaEntityContextProviderSupport implements MetaEntityContextProvider {

	private final MetaEntityProvider metaEntityProvider;

	private final Map<Reference, MetaEntityContext> contextEntities;

	public MetaEntityContextProviderSupport(final MetaEntityProvider metaEntityProvider) {
		this(metaEntityProvider, new LinkedHashMap<Reference, MetaEntityContext>());
	}

	public MetaEntityContextProviderSupport(final MetaEntityProvider metaEntityProvider, final Map<Reference, MetaEntityContext> contextEntities) throws NullPointerException {
		super();
		this.metaEntityProvider = Preconditions.checkNotNull(metaEntityProvider);
		this.contextEntities = Preconditions.checkNotNull(contextEntities);
	}

	@Override
	public MetaEntityContext find(final Reference reference) throws IllegalArgumentException, NullPointerException {
		if (reference.isIdentifier()) {
			return find(reference.getEntityClassReference());
		}
		// first check in local map
		final MetaEntityContext metaEntityContext = contextEntities.get(Preconditions.checkNotNull(reference));
		if (metaEntityContext == null) {
			// build it
			final Collection<MetaEntity> metaEntities = metaEntityProvider.find(reference);
			if (metaEntities.isEmpty()) {
				throw new IllegalArgumentException();
			}
			final Iterator<MetaEntity> iterator = metaEntities.iterator();
			final MetaEntityContext.Builder builder = MetaEntityContext.builder().definition(iterator.next());
			while (iterator.hasNext()) {
				builder.addExtendedDefinition(iterator.next());
			}
			return builder.build();
		}
		return metaEntityContext;
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

		private final Map<Reference, MetaEntityContext> contextEntities = Maps.newLinkedHashMap();

		/**
		 * Build a new instance of <code>Builder</code>.
		 */
		public Builder() {
			super();
			// add metamodel
			add(MetaAttribute.class, MetaModel.getMetaOfMetaAttribute()). //
					add(MetaEntity.class, MetaModel.getMetaOfMetaEntity()). //
					add(EntityDynamic.class, MetaModel.getMetaOfEntitydynamic()); //
		}

		public MetaEntityContextProviderSupport build(final MetaEntityProvider metaEntityProvider) throws NullPointerException {
			return new MetaEntityContextProviderSupport(metaEntityProvider, contextEntities);
		}

		/**
		 * Add a specific instance of {@link MetaEntityContext} for specified
		 * {@link Entity} instance.
		 * 
		 * @param reference
		 * @param metaEntityContext
		 * @return this instance
		 * @throws NullPointerException
		 *             if one of parameters is null
		 * @throws {@link IllegalArgumentException} if reference is an
		 *         identifier
		 */
		public Builder add(final Reference reference, final MetaEntityContext metaEntityContext) throws NullPointerException, IllegalArgumentException {
			if (Preconditions.checkNotNull(reference).isIdentifier()) {
				throw new IllegalArgumentException("Reference can only be an entity class reference");
			}
			contextEntities.put(reference, Preconditions.checkNotNull(metaEntityContext));
			return this;
		}

		/**
		 * Add a {@link MetaEntityContext} instance for specified class name.
		 * 
		 * @param clazz
		 * @param metaEntityContext
		 * @return this instance.
		 */
		public Builder add(final Class<?> clazz, final MetaEntityContext metaEntityContext) throws NullPointerException {
			contextEntities.put(Reference.newReference(clazz), Preconditions.checkNotNull(metaEntityContext));
			return this;
		}

		/**
		 * Add {@link MetaEntityContext} defintion for specified class name.
		 * 
		 * @param clazz
		 * @param definition
		 * @param extension
		 * @return this instance
		 */
		public Builder add(final Class<?> clazz, final MetaEntity definition, final MetaEntity... extension) throws NullPointerException {
			final MetaEntityContext.Builder builder = MetaEntityContext.builder().definition(Preconditions.checkNotNull(definition));
			if (extension != null) {
				for (final MetaEntity metaEntity : extension) {
					builder.addExtendedDefinition(metaEntity);
				}
			}
			contextEntities.put(Reference.newReference(clazz), builder.build());
			return this;
		}

	}

}
