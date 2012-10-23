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

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.intelligentsia.dowsers.entity.EntityDynamic;
import com.intelligentsia.dowsers.entity.Reference;

/**
 * MetaEntityContextProviderSupport implements {@link MetaEntityContextProvider}
 * .
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class MetaEntityContextProviderSupport implements MetaEntityContextProvider {

	private final Map<String, MetaEntityContext> contextEntities;

	public MetaEntityContextProviderSupport() {
		this(new LinkedHashMap<String, MetaEntityContext>());
	}

	public MetaEntityContextProviderSupport(final Map<String, MetaEntityContext> contextEntities) throws NullPointerException {
		super();
		this.contextEntities = Preconditions.checkNotNull(contextEntities);
	}

	@Override
	public MetaEntityContext find(final URI reference) throws IllegalArgumentException, NullPointerException {
		// try to find a specific one
		MetaEntityContext context = contextEntities.get(Preconditions.checkNotNull(reference));
		if (context == null) {
			// try to find for class name
			context = contextEntities.get(Reference.getEntityPart(reference));
		}
		// check for error
		if (context == null) {
			throw new IllegalArgumentException("no context found for " + reference);
		}
		return context;
	}

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
	public MetaEntityContextProviderSupport add(final URI reference, final MetaEntityContext metaEntityContext) {
		contextEntities.put(Preconditions.checkNotNull(reference).toString(), metaEntityContext);
		return this;
	}

	/**
	 * Add a {@link MetaEntityContext} instance for specified class name.
	 * 
	 * @param className
	 * @param metaEntityContext
	 * @return this instance.
	 */
	public MetaEntityContextProviderSupport add(final Class<?> className, final MetaEntityContext metaEntityContext) {
		contextEntities.put(Preconditions.checkNotNull(className).getName(), metaEntityContext);
		return this;
	}

	/**
	 * @return a {@link MetaEntityContextProviderSupport} instance.
	 */
	public static MetaEntityContextProviderSupport builder() {
		return new MetaEntityContextProviderSupport();
	}

}
