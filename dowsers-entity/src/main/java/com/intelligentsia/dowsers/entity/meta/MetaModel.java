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

import org.intelligentsia.dowsers.core.reflection.ClassInformation;
import org.intelligentsia.keystone.api.artifacts.Version;

import com.intelligentsia.dowsers.entity.EntityDynamic;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * MetaModel define Meta model for of meta data.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public enum MetaModel {
	;

	/**
	 * Meta model version.
	 */
	public static final Version VERSION = new Version(1, 0, 0);

	/**
	 * META DEFINITION for {@link MetaAttribute}.
	 */
	private static final MetaEntity metaMetaAttribute;

	/**
	 * Default {@link MetaEntityContext} for {@link MetaAttribute}.
	 */
	private static final MetaEntityContext metaAttributeContext;

	/**
	 * META DEFINITION for {@link MetaEntity}.
	 */
	private static final MetaEntity metaMetaEntity;

	/**
	 * Default {@link MetaEntityContext} for {@link MetaEntity}.
	 */
	private static final MetaEntityContext metaEntityContext;

	/**
	 * META DEFINITION for {@link EntityDynamic}.
	 */
	private static final MetaEntity metaEntityDynamic;

	/**
	 * META DEFINITION for Identity {@link MetaAttribute}.
	 */
	private static final MetaAttribute identityAttribute;

	static {

		// META ATTRIBUTE DEFINITION
		identityAttribute = MetaAttribute.builder().identity(Reference.parseString("urn:dowsers:com.intelligentsia.dowsers.entity.meta.MetaAttribute:identity#a3d44fd4-385d-4d09-b11b-21932abe2a25")).//
				name("identity").valueClass(Reference.class).build();

		final MetaAttribute name = MetaAttribute.builder().identity(Reference.parseString("urn:dowsers:com.intelligentsia.dowsers.entity.meta.MetaAttribute:identity#c2d720af-55eb-468d-adda-477b824d872c")).//
				name("name").valueClass(String.class).build();

		final MetaAttribute valueClass = MetaAttribute.builder().identity(Reference.parseString("urn:dowsers:com.intelligentsia.dowsers.entity.meta.MetaAttribute:identity#cd607a66-1e85-4bcc-b83e-e6494c0a1f09")).//
				name("valueClass").valueClass(ClassInformation.class).build();

		final MetaAttribute version = MetaAttribute.builder().identity(Reference.parseString("urn:dowsers:com.intelligentsia.dowsers.entity.meta.MetaAttribute:identity#53eb2d34-ab5c-4f29-93ba-678c08e64304")).//
				name("version").valueClass(Version.class).build();

		final MetaAttribute metaAttributes = MetaAttribute.builder().identity(Reference.parseString("urn:dowsers:com.intelligentsia.dowsers.entity.meta.MetaAttribute:identity#7b3f0f26-8ab5-4a94-8e15-0fc2dd8fcb60")).//
				name("metaAttributes").valueClass(MetaAttributeCollection.class).build();

		// META ENTITY DEFINITION
		metaMetaAttribute = new MetaEntity.Builder(). // definition
				name(MetaAttribute.class.getName()).version(VERSION).identity(Reference.parseString("urn:dowsers:com.intelligentsia.dowsers.entity.meta.MetaEntity:identity#f45e8827-c619-4b99-a8a2-e5c36d11ee47"))
				// attributes
				.metaAttributes(identityAttribute, name, valueClass).build();

		metaMetaEntity = new MetaEntity.Builder(). // definition
				name(MetaEntity.class.getName()).version(VERSION).identity(Reference.parseString("urn:dowsers:com.intelligentsia.dowsers.entity.meta.MetaEntity:identity#fbdff898-07c0-4933-9751-667c308831d6"))
				// attributes
				.metaAttributes(identityAttribute, name, version, metaAttributes).build();

		metaEntityDynamic = new MetaEntity.Builder(). // definition
				name(EntityDynamic.class.getName()).version(VERSION).identity(Reference.parseString("urn:dowsers:com.intelligentsia.dowsers.entity.meta.MetaEntity:identity#f3506e0b-e726-4924-9835-072ada7b8d29"))
				// attributes
				.metaAttributes(identityAttribute).build();

		// META CONTEXT
		metaAttributeContext = MetaEntityContext.builder().definition(metaMetaAttribute).build();

		metaEntityContext = MetaEntityContext.builder().definition(metaMetaEntity).build();
	}

	/**
	 * @return {@link MetaEntity} definition of {@link MetaAttribute}.
	 */
	public static MetaEntity getMetaOfMetaAttribute() {
		return metaMetaAttribute;
	}

	/**
	 * @return {@link MetaEntity} definition of {@link MetaEntity}.
	 */
	public static MetaEntity getMetaOfMetaEntity() {
		return metaMetaEntity;
	}

	/**
	 * @return {@link MetaEntity} definition of {@link EntityDynamic}.
	 */
	public static MetaEntity getMetaOfEntitydynamic() {
		return metaEntityDynamic;
	}

	/**
	 * @return {@link MetaAttribute} instance of identity.
	 */
	public static MetaAttribute getIdentityAttribute() {
		return identityAttribute;
	}

	public static MetaEntityContext getDefaultMetaAttributeContext() {
		return metaAttributeContext;
	}

	public static MetaEntityContext getDefaultMetaEntityContext() {
		return metaEntityContext;
	}

}
