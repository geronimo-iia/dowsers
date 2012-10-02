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
// $Id$

package net.sf.persist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Iterator backed by a ResultSet.
 */
public final class ResultSetIterator implements Iterator {

	public static final int TYPE_OBJECT = 1;
	public static final int TYPE_MAP = 2;

	private final ResultSet resultSet;
	private final Persist persist;
	private final Class objectClass;
	private final int type;
	private boolean hasNext = false;

	public ResultSetIterator(final Persist persist, final Class objectClass, final ResultSet resultSet, final int type) {

		if (type != TYPE_OBJECT && type != TYPE_MAP) {
			throw new RuntimeSQLException("Invalid ResultSetIterator type: " + type);
		}

		this.persist = persist;
		this.objectClass = objectClass;
		this.resultSet = resultSet;
		this.type = type;

		try {
			hasNext = resultSet.next();
		} catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
	}

	public boolean hasNext() {
		return hasNext;
	}

	public Object next() {
		try {
			final Object ret;
			if (type == TYPE_OBJECT) {
				ret = persist.loadObject(objectClass, resultSet);
			} else if (type == TYPE_MAP) {
				ret = Persist.loadMap(resultSet);
			} else {
				ret = null;
			}

			hasNext = resultSet.next();
			return ret;
		} catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
	}

	public void remove() {
		try {
			this.resultSet.deleteRow();
		} catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
	}

}
