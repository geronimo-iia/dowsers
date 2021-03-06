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

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.ObjectBuffer;

/**
 * KryoSerialize.
 * 
 * From https://github.com/eivindw/hazelcast-kryo-example
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class KryoSerializer {

	private static final Kryo kryo = new Kryo();

	static {
		KryoSerializer.kryo.setRegistrationOptional(true);
	}

	public static void register(final Class<?>... classes) {
		for (final Class<?> clazz : classes) {
			KryoSerializer.kryo.register(clazz);
		}
	}

	public static byte[] write(final Object obj) {
		final ObjectBuffer objectBuffer = new ObjectBuffer(KryoSerializer.kryo);
		return objectBuffer.writeClassAndObject(obj);
	}

	public static Object read(final byte[] bytes) {
		final ObjectBuffer objectBuffer = new ObjectBuffer(KryoSerializer.kryo);
		return objectBuffer.readClassAndObject(bytes);
	}

}
