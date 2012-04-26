/**
 * 
 */
package org.intelligentsia.dowsers.core.io;

import org.intelligentsia.dowsers.core.io.Serializer;
import org.intelligentsia.dowsers.core.io.Serializers;


/**
 * JavaSerializerTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class JavaSerializerTest extends AbstractSerializerTest {

	/**
	 * @see org.intelligentsia.dowsers.core.io.serializer.AbstractSerializerTest#createSerializer(java.lang.Class)
	 */
	@Override
	protected <T> Serializer<T> createSerializer(final Class<T> className) {
		return Serializers.newJavaSerializer(className);
	}

}
