/**
 * 
 */
package org.intelligentsia.dowsers.storage;

import org.intelligentsia.dowsers.storage.Serializer;
import org.intelligentsia.dowsers.storage.Serializers;

/**
 * JsonSerializerTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class JsonSerializerTest extends AbstractSerializerTest {

	/**
	 * @see org.intelligentsia.dowsers.storage.AbstractSerializerTest#createSerializer(java.lang.Class)
	 */
	@Override
	protected <T> Serializer<T> createSerializer(final Class<T> className) {
		return Serializers.newJacksonSerializer(className);
	}

}
