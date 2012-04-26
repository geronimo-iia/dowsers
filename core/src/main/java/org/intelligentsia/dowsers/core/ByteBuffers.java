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
package org.intelligentsia.dowsers.core;

import java.nio.ByteBuffer;

/**
 * ByteBuffers.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public enum ByteBuffers {
	;

	public static void write(final ByteBuffer bb, final int num) {
		if (num < -127 || num > 127) {
			bb.put((byte) -128);
			bb.putInt(num);
		} else {
			bb.put((byte) num);
		}
	}

	public static int readInt(final ByteBuffer bb) {
		final int n = bb.get();
		if (n == -128) {
			return bb.getInt();
		}
		return n;
	}

	public static void write(final ByteBuffer bb, final long num) {
		if (num < -127 || num > 127) {
			bb.put((byte) -128);
			bb.putLong(num);
		} else {
			bb.put((byte) num);
		}
	}

	public static long readLong(final ByteBuffer bb) {
		final int n = bb.get();
		if (n == -128) {
			return bb.getLong();
		}
		return n;
	}

	public static void write(final ByteBuffer bb, final Enum<?> anEnum) {
		bb.put((byte) anEnum.ordinal());
	}

	public static int readOrdinal(final ByteBuffer bb) {
		return bb.get() & 0xFF;
	}

}
