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
package org.intelligentsia.dowsers.eventstore.support.jdbc;

import java.sql.Connection;

import org.intelligentsia.dowsers.core.DowsersException;

/**
 * Represents work that needs to be performed in a manner which isolates it from
 * any current application unit of work transaction.
 * 
 * Here we throw a DowsersException and add a way to retrun a value.
 * 
 * 
 * 
 * @author Steve Ebersole
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public interface IsolatedWork<T> {
	/**
	 * Perform the actual work to be done.
	 * 
	 * @param connection
	 *            The JDBC connection to use.
	 * @throws DowsersException
	 */
	public T doWork(Connection connection) throws DowsersException;
}
