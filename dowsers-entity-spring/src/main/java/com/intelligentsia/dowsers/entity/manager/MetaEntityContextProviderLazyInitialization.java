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
package com.intelligentsia.dowsers.entity.manager;

import java.util.Map;

import com.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProviderSupport;
import com.intelligentsia.dowsers.entity.meta.MetaEntityProvider;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * MetaEntityContextProviderLazyInitialization is a class used to offer a lazy
 * initialization of MetaEntityContextProvider. When extra MetaEntityStore are
 * used, the need of EntityMapper can cause a cyclic dependencies.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class MetaEntityContextProviderLazyInitialization extends MetaEntityContextProviderSupport {

	/**
	 * Build a new instance of MetaEntityContextProviderLazyInitialization.java.
	 * 
	 * @param contextEntities
	 * @throws NullPointerException
	 */
	public MetaEntityContextProviderLazyInitialization(final Map<Reference, MetaEntityContext> contextEntities) throws NullPointerException {
		super(null, contextEntities);
	}

	/**
	 * @param metaEntityProvider
	 */
	void setMetaEntityProvider(final MetaEntityProvider metaEntityProvider) {
		super.metaEntityProvider = metaEntityProvider;
	}

}
