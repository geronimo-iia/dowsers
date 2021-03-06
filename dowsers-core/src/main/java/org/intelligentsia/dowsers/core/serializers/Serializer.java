/**
 *        Licensed to the Apache Software Foundation (ASF) under one
 *        or more contributor license agreements.  See the NOTICE file
 *        distributed with this work for additional information
 *        regarding copyright ownership.  The ASF licenses this file
 *        to you under the Apache License, Version 2.0 (the
 *        "License"); you may not use this file except in compliance
 *        with the License.  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing,
 *        software distributed under the License is distributed on an
 *        "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *        KIND, either express or implied.  See the License for the
 *        specific language governing permissions and limitations
 *        under the License.
 *
 */
package org.intelligentsia.dowsers.core.serializers;

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
