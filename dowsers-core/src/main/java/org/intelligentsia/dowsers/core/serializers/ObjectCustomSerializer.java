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

import java.nio.ByteBuffer;

/**
 * ObjectCustomSerializer declare methode to "write an object to" and
 * "read an object from" a ByteBuffer.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface ObjectCustomSerializer {

	/**
	 * Write this instance to specified byte buffer.
	 * 
	 * @param byteBuffer
	 *            destination byte buffer
	 */
	public void writeTo(ByteBuffer byteBuffer);

	/**
	 * Read data from byte buffer
	 * 
	 * @param byteBuffer
	 *            source
	 */
	public void readFrom(ByteBuffer byteBuffer);
}
