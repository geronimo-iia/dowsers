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
package org.intelligentsia.dowsers.model;

import java.io.PrintStream;

import org.intelligentsia.dowsers.model.meta.MetaEntityContext;
import org.intelligentsia.utilities.StringUtils;

import com.google.common.base.Preconditions;

/**
 * AccessLoggerEntityDecorator extends {@link EntityDecorator} by log all access
 * on {@link Entity} method.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class AccessLoggerEntityDecorator extends EntityDecorator {

	private final PrintStream printStream;

	/**
	 * Build a new instance of AccessLoggerEntityDecorator.java.
	 * 
	 * @param entity
	 * @param printStream
	 * @throws NullPointerException
	 */
	public AccessLoggerEntityDecorator(final Entity entity, final PrintStream printStream) throws NullPointerException {
		super(entity);
		this.printStream = Preconditions.checkNotNull(printStream);
	}

	@Override
	public String getIdentity() {
		printStream.println(StringUtils.format("getIdentity of entity '%s': '%s'", entity.getClass().getSimpleName(), entity.getIdentity()));
		return super.getIdentity();
	}

	@Override
	public MetaEntityContext getMetaEntityContext() {
		printStream.println(StringUtils.format("getMetaEntityContext of entity '%s': '%s'", entity.getClass().getSimpleName(), entity.getIdentity()));
		return super.getMetaEntityContext();
	}

	@Override
	public <Value> Value getProperty(String name) throws NullPointerException {
		printStream.println(StringUtils.format("getProperty '%s' of entity '%s#%s'", name, entity.getClass().getSimpleName(), entity.getIdentity()));
		return super.getProperty(name);
	}

	@Override
	public <Value> void setProperty(String name, Value value) throws NullPointerException {
		printStream.println(StringUtils.format("setProperty '%s' of entity '%s#%s': %s", name, entity.getClass().getSimpleName(), entity.getIdentity(), value));
		super.setProperty(name, value);
	}

}
