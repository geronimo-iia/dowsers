/**
 * 
 */
package com.iia.cqrs.storage;

import com.esotericsoftware.kryo.Kryo;

/**
 * KryoSerializerTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class KryoSerializerTest extends AbstractSerializerTest {

	/**
	 * @see com.iia.cqrs.storage.AbstractSerializerTest#createSerializer(java.lang.Class)
	 */
	@Override
	protected <T> Serializer<T> createSerializer(final Class<T> className) {
		final Kryo kryo = Serializers.newKryo();
		kryo.register(className);
		return Serializers.newKryoSerializer(className, kryo);
	}

}
