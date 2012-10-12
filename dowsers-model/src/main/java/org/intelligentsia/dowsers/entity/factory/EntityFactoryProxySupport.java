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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.intelligentsia.dowsers.entity.Entity;
import org.intelligentsia.dowsers.entity.EntityProxyHandler;
import org.intelligentsia.dowsers.entity.meta.MetaEntityContextNotFoundException;

import com.google.common.base.Preconditions;

/**
 * EntityFactoryProxySupport implements {@link EntityFactory} with proxy
 * pattern.
 * 
 * {@link InvocationHandler} used is {@link EntityProxyHandler} and default
 * {@link Entity} implementation is delagate to another {@link EntityFactory}
 * instance.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityFactoryProxySupport implements EntityFactory {

	/**
	 * Underlying {@link EntityFactory} instance.
	 */
	private final EntityFactory entityFactory;

	/**
	 * Build a new instance of EntityFactoryProxySupport.java.
	 * 
	 * @param delegate
	 *            {@link EntityFactory} instance to delegate underlying
	 *            implementation.
	 * 
	 * 
	 */
	public EntityFactoryProxySupport(final EntityFactory delegate) throws NullPointerException {
		super();
		this.entityFactory = Preconditions.checkNotNull(delegate);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity> T newInstance(final Class<?> className, final String identity) throws NullPointerException, MetaEntityContextNotFoundException {
		final Entity entity = entityFactory.newInstance(className, identity);
		return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { className, Entity.class }, new EntityProxyHandler(className, entity));
	}

}
