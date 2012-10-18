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

import java.util.Iterator;

/**
 * {@link ReadOnlyIterator} implements a read only {@link Iterator}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 * @param <E>
 */
public abstract class ReadOnlyIterator<E> implements Iterator<E> {

	/**
	 * @throws IllegalStateException
	 *             always
	 */
	@Override
	public void remove() {
		throw new IllegalStateException("Forbidden operation");
	}

	/**
	 * Build a read only version of specified iterator.
	 * 
	 * @param iterator
	 *            source iterator
	 * @return a {@link ReadOnlyIterator} of specified {@link Iterator}.
	 */
	public static <T> ReadOnlyIterator<T> newReadOnlyIterator(final Iterator<T> iterator) {
		return new ReadOnlyIterator<T>() {

			@Override
			public T next() {
				return iterator.next();
			}

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}
		};
	}
}
