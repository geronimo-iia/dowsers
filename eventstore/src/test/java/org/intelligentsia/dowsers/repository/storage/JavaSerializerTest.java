/**
 * 
 */
package org.intelligentsia.dowsers.repository.storage;

import org.intelligentsia.dowsers.repository.storage.Serializer;
import org.intelligentsia.dowsers.repository.storage.Serializers;

/**
 * JavaSerializerTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class JavaSerializerTest extends AbstractSerializerTest {

	/**
	 * @see org.intelligentsia.dowsers.repository.storage.AbstractSerializerTest#createSerializer(java.lang.Class)
	 */
	@Override
	protected <T> Serializer<T> createSerializer(final Class<T> className) {
		return Serializers.newJavaSerializer(className);
	}

}
