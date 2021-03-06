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
package com.intelligentsia.dowsers.entity.serializer;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

import org.intelligentsia.dowsers.core.DowsersException;
import org.intelligentsia.dowsers.core.serializers.JacksonSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intelligentsia.dowsers.entity.EntityProxy;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProvider;

/**
 * EntityMapper is a base class to read and write entity.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityMapper {

	private final ObjectMapper mapper;

	/**
	 * Build a new instance of <code>EntityMapper</code>. You must call
	 * {@link EntityMapper#initialize(MetaEntityContextProvider)} before using
	 * this instance.
	 */
	public EntityMapper() {
		this(JacksonSerializer.getMapper());
	}

	/**
	 * Build a new instance of <code>EntityMapper</code>.
	 * 
	 * @param mapper
	 *            You must call
	 *            {@link EntityMapper#initialize(MetaEntityContextProvider)}
	 *            before using this instance.
	 */
	public EntityMapper(final ObjectMapper mapper) {
		super();
		this.mapper = mapper;
	}

	/**
	 * Build a new instance of EntityMapper.java.
	 * 
	 * @param metaEntityContextProvider
	 */
	public EntityMapper(final MetaEntityContextProvider metaEntityContextProvider) {
		this(JacksonSerializer.getMapper(), metaEntityContextProvider);
	}

	/**
	 * Build a new instance of EntityMapper.java.
	 * 
	 * @param mapper
	 * @param metaEntityContextProvider
	 */
	public EntityMapper(final ObjectMapper mapper, final MetaEntityContextProvider metaEntityContextProvider) {
		super();
		this.mapper = mapper;
		initialize(metaEntityContextProvider);
	}

	public void initialize(final MetaEntityContextProvider metaEntityContextProvider) {
		this.mapper.registerModule(new EntityDowsersJacksonModule(metaEntityContextProvider));
	}

	/**
	 * Method that can be used to serialize any Java value as JSON output, using
	 * Writer provided.
	 * 
	 * @param writer
	 * @param value
	 * @throws DowsersException
	 */
	public void writeValue(final Writer writer, final Object value) throws DowsersException {
		try {
			mapper.writeValue(writer, value);
		} catch (final Throwable e) {
			throw new DowsersException(e);
		}
	}

	/**
	 * Method that can be used to parse JSON to specified Java value, using
	 * reader provided.
	 * 
	 * @param reader
	 * @param valueType
	 * @return
	 * @throws DowsersException
	 */
	@SuppressWarnings("unchecked")
	public <T> T readValue(final Reader reader, final Class<T> valueType) throws DowsersException {
		if (valueType.isInterface() || Modifier.isAbstract(valueType.getModifiers())) {
			try {
				final EntityProxy entityProxy = mapper.readValue(reader, EntityProxy.class);
				return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { valueType, EntityProxyHandler.class }, entityProxy);
			} catch (final Throwable e) {
				throw new DowsersException(e);
			}
		}
		try {
			return mapper.readValue(reader, valueType);
		} catch (final Throwable e) {
			throw new DowsersException(e);
		}
	}
}
