package org.intelligentsia.dowsers.storage;

import com.google.common.base.Preconditions;

/**
 * Interface describing a serialization mechanism.
 * 
 * @see https://github.com/eishay/jvm-serializers
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public abstract class Serializer<T> {

	protected final Class<T> innerType;

	/**
	 * Build a new instance of Serializer.
	 * 
	 * @param innerType
	 *            inner type
	 * @throws NullPointerException
	 *             if innerType is null
	 */
	public Serializer(final Class<T> innerType) throws NullPointerException {
		super();
		this.innerType = Preconditions.checkNotNull(innerType);
	}

	/**
	 * Deserializes the object.
	 * 
	 * @param array
	 *            the bytes providing the serialized data
	 * @return the serialized object, cast to the expected type
	 * @throws Exception
	 */
	public abstract T deserialize(byte[] array) throws Exception;

	/**
	 * Serializes the given <code>object</code>.
	 * 
	 * @param content
	 *            The object to serialize
	 * @return the byte array representing the serialized object.
	 * @throws Exception
	 *             when an error occurs while processing
	 */
	public abstract byte[] serialize(T content) throws Exception;

}
