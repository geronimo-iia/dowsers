/**
 * 
 */
package com.iia.cqrs.storage;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * AbstractSerializerTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public abstract class AbstractSerializerTest {

	private Serializer<DummyObject> serializer;

	@Before
	public void setup() {
		serializer = createSerializer(DummyObject.class);
	}

	protected abstract <T> Serializer<T> createSerializer(Class<T> className);

	@Test
	public void testDummyObject() throws Exception {
		final DummyObject dummyObject = new DummyObject("John");
		dummyObject.getListName().add("test1");
		dummyObject.getListName().add("test2");
		final byte[] bytes = serializer.serialize(dummyObject);
		Assert.assertNotNull(bytes);
		Assert.assertTrue(bytes.length > 0);
		final DummyObject dummyObject2 = serializer.deserialize(bytes);
		Assert.assertNotNull(dummyObject2);
		Assert.assertEquals(dummyObject, dummyObject2);
		Assert.assertEquals(dummyObject.getName(), dummyObject2.getName());
		Assert.assertNotNull(dummyObject2.getListName());
		Assert.assertTrue(dummyObject.getListName().size() == dummyObject2.getListName().size());
	}

}
