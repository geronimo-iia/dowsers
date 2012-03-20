/**
 * 
 */
package com.iia.cqrs.storage;

/**
 * JsonSerializerTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class JsonSerializerTest extends AbstractSerializerTest {

	/**
	 * @see com.iia.cqrs.storage.AbstractSerializerTest#createSerializer(java.lang.Class)
	 */
	@Override
	protected <T> Serializer<T> createSerializer(final Class<T> className) {
		return Serializers.newJacksonSerializer(className);
	}

}
