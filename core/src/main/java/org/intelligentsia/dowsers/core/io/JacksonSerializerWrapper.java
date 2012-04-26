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
package org.intelligentsia.dowsers.core.io;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * JacksonSerializerWrapper.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class JacksonSerializerWrapper implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Class<?> valueType;
	private final byte[] s;

	/**
	 * Build a new instance of JacksonSerializerWrapper.
	 * 
	 * @param target
	 */
	public JacksonSerializerWrapper(final Object target) {
		valueType = target.getClass();
		s = JacksonSerializer.write(target);
	}

	/**
	 * For Serializable and Externalizable classes, the readResolve method
	 * allows a class to replace/resolve the object read from the stream before
	 * it is returned to the caller. By implementing the readResolve method, a
	 * class can directly control the types and instances of its own instances
	 * being deserialized. The method is defined as follows:
	 * 
	 * @return
	 * @throws ObjectStreamException
	 */
	private Object readResolve() throws ObjectStreamException {
		return JacksonSerializer.read(s, valueType);
	}

}
