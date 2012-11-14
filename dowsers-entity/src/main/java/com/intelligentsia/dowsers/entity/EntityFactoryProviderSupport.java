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
package com.intelligentsia.dowsers.entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.intelligentsia.dowsers.core.reflection.Reflection;
import org.intelligentsia.keystone.api.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.intelligentsia.dowsers.entity.EntityFactories.EntityFactory;
import com.intelligentsia.dowsers.entity.EntityFactories.EntityFactoryProvider;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProvider;
import com.intelligentsia.dowsers.entity.meta.MetaModel;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * EntityFactoryProviderSupport implements {@link EntityFactoryProvider}.
 * 
 * This implementation manage factory for:
 * <ul>
 * <li>All entity as interface or abstract class which can be proxified> In this
 * case, default implementation will be an {@link EntityDynamic} instance</li>
 * <li>All EntityDynamic</li>
 * <li>Registered entity factory</li>
 * <li>Other unregistered factory if flag enableDefaultFactory is set to
 * {@link Boolean#TRUE}</li>
 * </ul>
 * 
 * In case of unregistered factory, entity class must follow this rules.
 * <ul>
 * <li>Must implement {@link Entity} interface</li>
 * <li>Must have a default constructor (public or not)</li>
 * <li>Must have a field (can be private) named 'identity' of type
 * {@link Reference}.</li>
 * </ul>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class EntityFactoryProviderSupport implements EntityFactoryProvider {

	private final MetaEntityContextProvider metaEntityContextProvider;

	private final Map<Class<?>, EntityFactories.EntityFactory<?>> factories;

	private final boolean enableDefaultFactory;

	/**
	 * Build a new instance of EntityFactoryProviderSupport.java.
	 * 
	 * @param metaEntityContextProvider
	 *            meta Entity Context Provider instance
	 * @throws NullPointerException
	 *             if metaEntityContextProvider is null
	 */
	public EntityFactoryProviderSupport(MetaEntityContextProvider metaEntityContextProvider) throws NullPointerException {
		this(metaEntityContextProvider, null, Boolean.TRUE);

	}

	/**
	 * Build a new instance of EntityFactoryProviderSupport.java.
	 * 
	 * @param metaEntityContextProvider
	 *            meta Entity Context Provider instance
	 * @throws NullPointerException
	 *             if metaEntityContextProvider is null
	 */
	public EntityFactoryProviderSupport(MetaEntityContextProvider metaEntityContextProvider, Map<Class<?>, EntityFactory<?>> factories, final boolean enableDefaultFactory) {
		super();
		this.metaEntityContextProvider = Preconditions.checkNotNull(metaEntityContextProvider);
		this.factories = factories == null ? new HashMap<Class<?>, EntityFactories.EntityFactory<?>>() : factories;
		this.enableDefaultFactory = enableDefaultFactory;
	}

	/**
	 * @see com.intelligentsia.dowsers.entity.EntityFactoryProvider#newInstance(java.lang.Class)
	 * 
	 * @throws IllegalArgumentException
	 *             if no {@link MetaEntityContext} was found
	 */

	@SuppressWarnings("unchecked")
	@Override
	public <T> EntityFactory<T> newInstance(final Class<T> expectedType) throws NullPointerException, IllegalArgumentException {
		// should we use a proxy ?
		if (expectedType.isInterface() || Modifier.isAbstract(expectedType.getModifiers())) {
			MetaEntityContext metaEntityContext = metaEntityContextProvider.find(Reference.newReference(expectedType));
			return EntityFactories.newEntityProxyDynamicFactory(expectedType, metaEntityContext);
		}
		// EntityDynamic
		if (EntityDynamic.class.getName().equals(expectedType.getName())) {
			return (EntityFactory<T>) EntityFactories.newEntityDynamicFactory(MetaModel.getEntityDynamicContext());
		}
		// in a registered factory ?
		EntityFactories.EntityFactory<?> factory = factories.get(expectedType);
		if (factory != null) {
			return (EntityFactory<T>) factory;
		}
		if (!enableDefaultFactory) {
			throw new IllegalArgumentException(StringUtils.format("No EntityFactory for %s", expectedType));
		}
		// other
		if (!Entity.class.isAssignableFrom(expectedType)) {
			throw new IllegalArgumentException(StringUtils.format("No EntityFactory can be created for %s (Not assignable to Entity interface)", expectedType));
		}
		EntityFactory<T> entityFactory = buildDefaultEntityFactory(expectedType);
		// register for later
		factories.put(expectedType, entityFactory);
		// return result
		return entityFactory;
	}

	/**
	 * @param expectedType
	 * @return
	 */
	public <T> EntityFactory<T> buildDefaultEntityFactory(final Class<T> expectedType) {
		EntityFactory<T> entityFactory = new EntityFactory<T>() {

			private final Constructor<T> constructor = Preconditions.checkNotNull(Reflection.findDefaultConstructor(expectedType), StringUtils.format("No EntityFactory can be created for %s (No default constructor)", expectedType));
			private final Field identity = Preconditions.checkNotNull(Reflection.findField(expectedType, "identity"), StringUtils.format("No EntityFactory can be created for %s (Not identity field)", expectedType));

			@Override
			public T newInstance() {
				try {
					return constructor.newInstance();
				} catch (Throwable e) {
					throw Throwables.propagate(e);
				}
			}

			@SuppressWarnings("unchecked")
			@Override
			public T newInstance(Reference identifier) throws NullPointerException, IllegalArgumentException {
				try {
					Entity entity = (Entity) newInstance();
					identity.set(entity, identifier);
					return (T) entity;
				} catch (Throwable e) {
					throw Throwables.propagate(e);
				}
			}
		};
		return entityFactory;
	}

	/**
	 * @return a {@link Builder} for {@link EntityFactoryProviderSupport}.
	 */
	public static final Builder builder() {
		return new Builder();
	}

	/**
	 * Builder pattern for {@link EntityFactoryProviderSupport}.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
	 */
	public static class Builder {

		Map<Class<?>, EntityFactories.EntityFactory<?>> factories = Maps.newHashMap();

		boolean enableDefaultFactory = Boolean.TRUE;

		/**
		 * Build a new instance of EntityFactoryProviderSupport.
		 */
		public Builder() {
			super();
		}

		/**
		 * Enable default factory mechanism.
		 * 
		 * @return this instance.
		 */
		public Builder enableDefaultFactory() {
			enableDefaultFactory = Boolean.TRUE;
			return this;
		}

		/**
		 * Disable default factory mechanism.
		 * 
		 * @return this instance.
		 */
		public Builder disableDefaultFactory() {
			enableDefaultFactory = Boolean.FALSE;
			return this;
		}

		/**
		 * Register a specific factory>
		 * 
		 * @param clazz
		 *            entity class
		 * @param factory
		 *            factory
		 * @return this instance.
		 * @throws NullPointerException
		 *             if clazz or factory is null
		 */
		public <T> Builder register(Class<T> clazz, EntityFactories.EntityFactory<T> factory) throws NullPointerException {
			factories.put(Preconditions.checkNotNull(clazz), Preconditions.checkNotNull(factory));
			return this;
		}

		/**
		 * @param metaEntityContextProvider
		 * @return an {@link EntityFactoryProviderSupport} instance
		 * @throws NullPointerException
		 *             if metaEntityContextProvider is null
		 */
		public EntityFactoryProviderSupport build(MetaEntityContextProvider metaEntityContextProvider) throws NullPointerException {
			return new EntityFactoryProviderSupport(metaEntityContextProvider);
		}
	}

}
