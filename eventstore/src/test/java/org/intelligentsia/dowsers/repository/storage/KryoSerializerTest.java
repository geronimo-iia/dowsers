/**
 * 
 */
package org.intelligentsia.dowsers.repository.storage;

import org.intelligentsia.dowsers.repository.storage.Serializer;
import org.intelligentsia.dowsers.repository.storage.Serializers;

import com.esotericsoftware.kryo.Kryo;

/**
 * KryoSerializerTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class KryoSerializerTest extends AbstractSerializerTest {

	/**
	 * @see org.intelligentsia.dowsers.repository.storage.AbstractSerializerTest#createSerializer(java.lang.Class)
	 */
	@Override
	protected <T> Serializer<T> createSerializer(final Class<T> className) {
		final Kryo kryo = Serializers.newKryo();
		kryo.register(className);
		return Serializers.newKryoSerializer(className, kryo);
	}

}
