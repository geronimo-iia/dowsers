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
package com.intelligentsia.dowsers.entity.meta;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.EntityDynamic;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * MetaEntityContextProviderSupport implements {@link MetaEntityContextProvider}
 * . TODO better impls.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class MetaEntityContextProviderSupport implements MetaEntityContextProvider {

	private final Map<Reference, MetaEntityContext> contextEntities;

	public MetaEntityContextProviderSupport() {
		this(new LinkedHashMap<Reference, MetaEntityContext>());
	}

	public MetaEntityContextProviderSupport(final Map<Reference, MetaEntityContext> contextEntities) throws NullPointerException {
		super();
		this.contextEntities = Preconditions.checkNotNull(contextEntities);
	}

	@Override
	public MetaEntityContext find(final Reference reference) throws IllegalArgumentException, NullPointerException {
		final MetaEntityContext context = contextEntities.get(Preconditions.checkNotNull(reference));
		if (context == null) {
			if (reference.getIdentity() != null) {
				return contextEntities.get(reference.getEntityClassReference());
			}
			throw new IllegalArgumentException("no context found");
		}
		return context;
	}

	//
	// @Override
	// public MetaEntityContext find(final Class<?> clazz, final String
	// identity) throws IllegalArgumentException, NullPointerException {
	// return find(clazz.getName(), identity);
	// }
	//
	// @Override
	// public MetaEntityContext find(final Class<?> clazz) throws
	// NullPointerException {
	// return find(clazz.getName());
	// }

	// public MetaEntityContext find(final String className, final String
	// identity) throws IllegalArgumentException, NullPointerException {
	// if (identity != null) {
	// final MetaEntityContext context = specificContextEntities.get(identity);
	// if (context != null) {
	// return context;
	// }
	// }
	// return find(className);
	// }
	//
	// public MetaEntityContext find(final String className) throws
	// NullPointerException {
	// final MetaEntityContext context = contextEntities.get(className);
	// // check for error
	// if (context == null) {
	// throw new IllegalArgumentException("no context found");
	// }
	// return context;
	// }

	/**
	 * Add default {@link MetaEntityContext} for {@link MetaAttribute},
	 * {@link MetaEntity} and {@link EntityDynamic}.
	 * 
	 * @return this instance.
	 */
	public MetaEntityContextProviderSupport addDefaultMetaEntityContext() {
		return add(MetaAttribute.class, MetaEntityContext.builder().definition(MetaModel.getMetaOfMetaAttribute()).build()). //
				add(MetaEntity.class, MetaEntityContext.builder().definition(MetaModel.getMetaOfMetaEntity()).build()). //
				add(EntityDynamic.class, MetaEntityContext.builder().definition(MetaModel.getMetaOfEntitydynamic()).build());
	}

	/**
	 * Add a specific instance of {@link MetaEntityContext} for specified
	 * {@link Entity} instance.
	 * 
	 * @param reference
	 * @param metaEntityContext
	 * @return this instance
	 */
	public MetaEntityContextProviderSupport add(final Reference reference, final MetaEntityContext metaEntityContext) throws NullPointerException {
		contextEntities.put(Preconditions.checkNotNull(reference), metaEntityContext);
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
	public MetaEntityContextProviderSupport add(final Class<?> clazz, final MetaEntity definition, final MetaEntity... extension) throws NullPointerException {
		final MetaEntityContext.Builder builder = MetaEntityContext.builder().definition(Preconditions.checkNotNull(definition));
		if (extension != null) {
			for (final MetaEntity metaEntity : extension) {
				builder.addExtendedDefinition(metaEntity);
			}
		}
		contextEntities.put(Reference.newReference(clazz), builder.build());
		return this;
	}

	/**
	 * Add a {@link MetaEntityContext} instance for specified class name.
	 * 
	 * @param clazz
	 * @param metaEntityContext
	 * @return this instance.
	 */
	public MetaEntityContextProviderSupport add(final Class<?> clazz, final MetaEntityContext metaEntityContext) throws NullPointerException {
		contextEntities.put(Reference.newReference(clazz), metaEntityContext);
		return this;
	}

}
