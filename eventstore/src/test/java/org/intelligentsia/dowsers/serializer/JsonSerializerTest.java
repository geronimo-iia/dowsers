/**
 * 
 */
package org.intelligentsia.dowsers.serializer;

import org.intelligentsia.dowsers.serializer.Serializer;
import org.intelligentsia.dowsers.serializer.Serializers;

/**
 * JsonSerializerTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class JsonSerializerTest extends AbstractSerializerTest {

	/**
	 * @see org.intelligentsia.dowsers.serializer.AbstractSerializerTest#createSerializer(java.lang.Class)
	 */
	@Override
	protected <T> Serializer<T> createSerializer(final Class<T> className) {
		return Serializers.newJacksonSerializer(className);
	}

}
