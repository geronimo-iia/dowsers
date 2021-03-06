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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.UUID;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.ObjectBuffer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Preconditions;

/**
 * Serializers.
 * 
 * 
 * @see https://github.com/eishay/jvm-serializers
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public enum Serializers {
	;

	/**
	 * Build a new <code>Serializer</code> using Kryo automatic data binding .
	 * 
	 * @param innerType
	 *            type to serialize
	 * @param kryo
	 *            a kryo instance.
	 * @return a <code>Serializer</code> instance.
	 */
	public static <T> Serializer<T> newKryoSerializer(final Class<T> innerType, final Kryo kryo) {
		return new KryoSerializer<T>(innerType, kryo);
	}

	/**
	 * Build a new <code>Serializer</code> using Json Jackson data binding and
	 * default object mapper.
	 * 
	 * @param innerType
	 *            type to serialize.
	 * @return a <code>Serializer</code> instance.
	 */
	public static <T> Serializer<T> newJacksonSerializer(final Class<T> innerType) {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		return Serializers.newJacksonSerializer(innerType, mapper);
	}

	/**
	 * Build a new <code>Serializer</code> using Json Jackson data binding.
	 * 
	 * @param innerType
	 *            type to serialize.
	 * @param mapper
	 *            Object mapper to use
	 * @return a <code>Serializer</code> instance.
	 */
	public static <T> Serializer<T> newJacksonSerializer(final Class<T> innerType, final ObjectMapper mapper) {
		return new JacksonSerializer<T>(innerType, mapper);
	}

	/**
	 * Build a new <code>Serializer</code> using standard java serialization
	 * mechanism.
	 * 
	 * @param innerType
	 *            type to serialize.
	 * @return a <code>Serializer</code> instance.
	 */
	public static <T> Serializer<T> newJavaSerializer(final Class<T> innerType) {
		return new JavaSerializer<T>(innerType);
	}

	/**
	 * @return a new instance of kryo with most of java type registered.
	 */
	public static Kryo newKryo() {
		final Kryo kryo = new Kryo();
		// array
		kryo.register(boolean[].class);
		kryo.register(short[].class);
		kryo.register(int[].class);
		kryo.register(long[].class);
		kryo.register(String[].class);
		kryo.register(Boolean[].class);
		kryo.register(Short[].class);
		kryo.register(Integer[].class);
		kryo.register(Long[].class);
		// collections
		kryo.register(ArrayList.class);
		kryo.register(LinkedList.class);
		kryo.register(HashMap.class);
		kryo.register(HashSet.class);
		// utilities
		kryo.register(Locale.class);
		kryo.register(Currency.class);
		kryo.register(UUID.class);
		// ok ?
		return kryo;
	}

	/**
	 * KryoSerializer implementation.
	 * 
	 * @param <T>
	 */
	public static class KryoSerializer<T> extends Serializer<T> {

		private final Kryo kryo;

		public KryoSerializer(final Class<T> innerType, final Kryo kryo) {
			super(innerType);
			this.kryo = Preconditions.checkNotNull(kryo);
		}

		@Override
		public T deserialize(final byte[] array) throws Exception {
			final ObjectBuffer objectBuffer = new ObjectBuffer(kryo);
			return objectBuffer.readObjectData(array, innerType);
		}

		@Override
		public byte[] serialize(final T content) throws Exception {
			final ObjectBuffer objectBuffer = new ObjectBuffer(kryo);
			return objectBuffer.writeObjectData(content);
		}
	}

	/**
	 * JacksonSerializer implementation.
	 * 
	 * @param <T>
	 */
	public static class JacksonSerializer<T> extends Serializer<T> {

		private final ObjectMapper mapper;

		/**
		 * Build a new instance of JacksonSerializer.
		 * 
		 * @param innerType
		 * @param mapper
		 * @throws NullPointerException
		 */
		public JacksonSerializer(final Class<T> innerType, final ObjectMapper mapper) throws NullPointerException {
			super(innerType);
			this.mapper = Preconditions.checkNotNull(mapper);
		}

		@Override
		public final byte[] serialize(final T content) throws Exception {
			return mapper.writeValueAsBytes(content);
		}

		@Override
		public final T deserialize(final byte[] array) throws Exception {
			return mapper.readValue(array, 0, array.length, innerType);
		}
	}

	/**
	 * JavaSerializer implementation.
	 * 
	 * @param <T>
	 */
	public static class JavaSerializer<T> extends Serializer<T> {

		public JavaSerializer(final Class<T> innerType) {
			super(innerType);
		}

		@Override
		@SuppressWarnings("unchecked")
		public T deserialize(final byte[] array) throws Exception {
			final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(array));
			return (T) ois.readObject();
		}

		@Override
		public byte[] serialize(final T content) throws IOException, Exception {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
			final ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(content);
			final byte[] array = baos.toByteArray();
			return array;
		}
	}

}
