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
package org.intelligentsia.dowsers.core.serializers;

import org.intelligentsia.dowsers.core.serializers.jackson.DowsersJacksonModule;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Throwables;

/**
 * JacksonSerializer.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class JacksonSerializer {

	private static final ObjectMapper mapper = new ObjectMapper();

	static {

		// SerializationFeature for changing how JSON is written

		// to enable standard indentation ("pretty-printing"):
		JacksonSerializer.mapper.disable(SerializationFeature.INDENT_OUTPUT);
		// to allow serialization of "empty" POJOs (no properties to serialize)
		// (without this setting, an exception is thrown in those cases)
		JacksonSerializer.mapper.enable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		// to write java.util.Date, Calendar as number (timestamp):
		JacksonSerializer.mapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		// DeserializationFeature for changing how JSON is read as POJOs:
		// to prevent exception when encountering unknown property:
		JacksonSerializer.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		// to allow coercion of JSON empty String ("") to null Object value:
		JacksonSerializer.mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

		mapper.registerModule(new DowsersJacksonModule());
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
