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
package org.intelligentsia.dowsers.entity.factory;

import org.intelligentsia.dowsers.entity.Entity;
import org.intelligentsia.dowsers.entity.meta.MetaEntityContextRepository;

import com.google.common.base.Preconditions;

/**
 * AbstractEntityFactorySupport implements {@link EntityFactory} to easy
 * instantiation of {@link Entity}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 * @param <T>
 */
public abstract class AbstractEntityFactorySupport implements EntityFactory {

	/**
	 * {@link MetaEntityContextRepository} instance.
	 */
	private final MetaEntityContextRepository metaEntityContextRepository;

	/**
	 * Build a new instance of AbstractEntityFactorySupport.java.
	 * 
	 * @param metaEntityContextRepository
	 * @throws NullPointerException
	 *             if metaEntityContextRepository is null
	 */
	public AbstractEntityFactorySupport(final MetaEntityContextRepository metaEntityContextRepository) throws NullPointerException {
		super();
		this.metaEntityContextRepository = Preconditions.checkNotNull(metaEntityContextRepository);
	}

	/**
	 * @return {@link MetaEntityContextRepository} instance.
	 */
	protected MetaEntityContextRepository getMetaEntityContextRepository() {
		return metaEntityContextRepository;
	}

}
