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
