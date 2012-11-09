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

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.intelligentsia.dowsers.core.reflection.ClassInformation;
import org.intelligentsia.dowsers.core.reflection.Reflection;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.annotation.Attribute;
import com.intelligentsia.dowsers.entity.annotation.DomainEntity;
import com.intelligentsia.dowsers.entity.annotation.IgnoreAttribute;
import com.intelligentsia.dowsers.entity.meta.MetaAttribute;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaEntityProvider;
import com.intelligentsia.dowsers.entity.meta.MetaModel;
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
	 * Build a new instance of MetaEntityProviderAnalyzer.
	 */
	public MetaEntityProviderAnalyzer() {
		super();
	}

	/**
	 * @see com.intelligentsia.dowsers.entity.meta.MetaEntityProvider#find(com.intelligentsia.dowsers.entity.reference.Reference)
	 */
	@Override
	public Collection<MetaEntity> find(final Reference reference) throws NullPointerException {
		Preconditions.checkNotNull(reference);
		final Collection<MetaEntity> result = Sets.newLinkedHashSet();
		if (reference.isIdentifier()) {
			return result;
		}
		// obtain class information
		final ClassInformation classInformation = ClassInformation.parse(reference.getEntityClassName());
		// analyze
		final MetaEntity metaEntity = analyze(classInformation);
		if (metaEntity != null) {
			if (!metaEntity.metaAttributes().isEmpty()) {
				result.add(metaEntity);
			}
		}
		return result;
	}

	/**
	 * @param classInformation
	 *            class Information to analyze
	 * @return a {@link MetaEntity} instance
	 * @throws NullPointerException
	 *             if classInformation is null
	 */
	@VisibleForTesting
	static MetaEntity analyze(final ClassInformation classInformation) throws NullPointerException {
		Preconditions.checkNotNull(classInformation);
		final Class<?> clazz = classInformation.getType();
		// init builder with class name and MetaModel version.
		final MetaEntity.Builder builder = MetaEntity.builder().name(clazz.getName()).version(MetaModel.VERSION);
		// analyze attributes
		final Iterator<MetaAttribute> iterator = analyzeMetaAttribute(clazz).iterator();
		while (iterator.hasNext()) {
			builder.metaAttribute(iterator.next());
		}
		return builder.build();
	}

	/**
	 * Analyze MetaAttribute on specified class.
	 * 
	 * @param clazz
	 * @return an instance of {@link List} of {@link MetaAttribute}
	 * @throws NullPointerException
	 *             if clazz is null
	 * @throws IllegalArgumentException
	 *             if something was wrong with attribute declaration
	 */
	@VisibleForTesting
	static Collection<MetaAttribute> analyzeMetaAttribute(final Class<?> clazz) throws NullPointerException, IllegalArgumentException {
		final Map<String, MetaAttribute> attributes = Maps.newHashMap();
		Preconditions.checkNotNull(clazz);

		// auto discover attributes
		final DomainEntity domainEntity = clazz.getAnnotation(DomainEntity.class);
		final boolean autoDiscovering = domainEntity != null ? domainEntity.autoDiscovering() : Boolean.TRUE;

		// for each method
		for (final Method method : clazz.getMethods()) {
			if (hasAttributeSignature(method, autoDiscovering)) {
				final String name = extractName(method);
				if (!attributes.containsKey(name)) {
					attributes.put(name, MetaAttribute.builder().name(name).valueClass(extractValueClass(method)).build());
				}
			}
		}

		// check for Identity attribute
		if (!attributes.containsKey(MetaModel.getIdentityAttribute().name())) {
			attributes.put(MetaModel.getIdentityAttribute().name(), MetaModel.getIdentityAttribute());
		} else {
			// check Identity attribute compliance
			if (!attributes.get(MetaModel.getIdentityAttribute().name()).equals(MetaModel.getIdentityAttribute())) {
				throw new IllegalArgumentException("Declared 'identity' attribute must be a Reference type");
			}
		}
		return attributes.values();
	}

	/**
	 * Extract a method name.
	 * 
	 * @param method
	 * @return a name
	 */
	@VisibleForTesting
	static String extractName(final Method method) {
		final String name = method.getName();
		if (name.startsWith("get") || name.startsWith("set")) {
			return Reflection.toFieldName(name);
		}
		return Reflection.uncapitalize(name);
	}

	@VisibleForTesting
	static Class<?> extractValueClass(final Method method) {
		if (Void.TYPE == method.getReturnType()) {
			return method.getParameterTypes()[0];
		}
		return method.getReturnType();
	}

	/**
	 * Check if the specified method can be an attribute.
	 * 
	 * @param method
	 * @return true if specified method can be an attribute, false other else.
	 */
	@VisibleForTesting
	static boolean hasAttributeSignature(final Method method, final boolean autoDiscovering) {
		if (method.getAnnotation(IgnoreAttribute.class) != null) {
			return false;
		}
		if (!autoDiscovering && (method.getAnnotation(Attribute.class) == null)) {
			return false;
		}
		if (Void.TYPE == method.getReturnType()) {
			return method.getParameterTypes().length == 1;
		} else {
			return method.getParameterTypes().length == 0;
		}

	}
}
