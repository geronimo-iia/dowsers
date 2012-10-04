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
package org.intelligentsia.dowsers.core.io;

import java.util.Locale;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.util.VersionUtil;
import org.intelligentsia.dowsers.core.io.serializers.LocaleJsonDeserializer;
import org.intelligentsia.dowsers.core.io.serializers.LocaleJsonSerializer;
import org.intelligentsia.dowsers.core.io.serializers.LocaleKeyDeserializer;

import com.google.common.base.Throwables;

/**
 * JacksonSerializer.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class JacksonSerializer {

	private static final ObjectMapper mapper = new ObjectMapper();

	static {
		JacksonSerializer.mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		JacksonSerializer.mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, false);
		// Add custom de/serializer
		SimpleModule testModule = new SimpleModule("DowsersJacksonSerializer", VersionUtil.versionFor(mapper.getClass()));
		testModule.addKeyDeserializer(Locale.class, new LocaleKeyDeserializer());
		testModule.addSerializer(new LocaleJsonSerializer());
		testModule.addDeserializer(Locale.class, new LocaleJsonDeserializer());
		mapper.registerModule(testModule);
	}

	/**
	 * @return the mapper
	 */
	public static ObjectMapper getMapper() {
		return JacksonSerializer.mapper;
	}

	/**
	 * 
	 * @param obj
	 *            object to serialize
	 * @return result byte array
	 * @throws RuntimeException
	 */
	public static byte[] write(final Object obj) throws RuntimeException {
		try {
			return JacksonSerializer.mapper.writeValueAsBytes(obj);
		} catch (final Exception e) {
			throw Throwables.propagate(Throwables.getRootCause(e));
		}
	}

	/**
	 * Deserialize an object.
	 * 
	 * @param bytes
	 *            data
	 * @param valueType
	 *            value type
	 * @return deserialized instance
	 * @throws RuntimeException
	 */
	public static Object read(final byte[] bytes, final Class<?> valueType) throws RuntimeException {
		try {
			return JacksonSerializer.mapper.readValue(bytes, 0, bytes.length, valueType);
		} catch (final Exception e) {
			throw Throwables.propagate(Throwables.getRootCause(e));
		}
	}
}
