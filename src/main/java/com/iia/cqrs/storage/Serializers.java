/**
 * 
 */
package com.iia.cqrs.storage;

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

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.ObjectBuffer;
import com.google.common.base.Preconditions;

/**
 * Serializers.
 * 
 * 
 * @see https://github.com/eishay/jvm-serializers
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public final class Serializers {

	/**
	 * Build a new instance of Serializers.
	 */
	private Serializers() {
	}

	/**
	 * Build a new <code>Serializer</code> using Kryo automatic data binding .
	 * 
	 * @param innerType
	 *            type to serialize
	 * @param kryo
	 *            a kryo instance.
	 * @return a <code>Serializer</code> instance.
	 */
	public static <T> Serializer<T> newKryoSerializer(Class<T> innerType, Kryo kryo) {
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
	public static <T> Serializer<T> newJacksonSerializer(Class<T> innerType) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return newJacksonSerializer(innerType, mapper);
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
	public static <T> Serializer<T> newJacksonSerializer(Class<T> innerType, ObjectMapper mapper) {
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
	public static <T> Serializer<T> newJavaSerializer(Class<T> innerType) {
		return new JavaSerializer<T>(innerType);
	}

	/**
	 * @return a new instance of kryo with most of java type registered.
	 */
	public static Kryo newKryo() {
		Kryo kryo = new Kryo();
		// array
		kryo.register(String[].class);
		kryo.register(Boolean[].class);
		kryo.register(boolean[].class);
		kryo.register(Short[].class);
		kryo.register(short[].class);
		kryo.register(Integer[].class);
		kryo.register(int[].class);
		kryo.register(Long[].class);
		kryo.register(long[].class);
		// collections
		kryo.register(ArrayList.class);
		kryo.register(LinkedList.class);
		kryo.register(HashMap.class);
		kryo.register(HashSet.class);
		// utils
		kryo.register(Locale.class);
		kryo.register(Currency.class);
		kryo.register(UUID.class);

		return kryo;
	}

	/**
	 * KryoSerializer implementation.
	 * 
	 * @param <T>
	 */
	public static class KryoSerializer<T> extends Serializer<T> {

		private final Kryo kryo;

		public KryoSerializer(Class<T> innerType, Kryo kryo) {
			super(innerType);
			this.kryo = Preconditions.checkNotNull(kryo);
		}

		public T deserialize(byte[] array) throws Exception {
			ObjectBuffer objectBuffer = new ObjectBuffer(kryo, 1024);
			return objectBuffer.readObjectData(array, innerType);
		}

		public byte[] serialize(T content) throws Exception {
			ObjectBuffer objectBuffer = new ObjectBuffer(kryo, 1024);
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
		public JacksonSerializer(Class<T> innerType, ObjectMapper mapper) throws NullPointerException {
			super(innerType);
			this.mapper = Preconditions.checkNotNull(mapper);
		}

		public final byte[] serialize(T content) throws Exception {
			return mapper.writeValueAsBytes(content);
		}

		public final T deserialize(byte[] array) throws Exception {
			return (T) mapper.readValue(array, 0, array.length, innerType);
		}
	}

	/**
	 * JavaSerializer implementation.
	 * 
	 * @param <T>
	 */
	public static class JavaSerializer<T> extends Serializer<T> {

		public JavaSerializer(Class<T> innerType) {
			super(innerType);
		}

		@SuppressWarnings("unchecked")
		public T deserialize(byte[] array) throws Exception {
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(array));
			return (T) ois.readObject();
		}

		public byte[] serialize(T content) throws IOException, Exception {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(content);
			byte[] array = baos.toByteArray();
			return array;
		}
	}
}
