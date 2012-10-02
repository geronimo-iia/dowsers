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
package org.intelligentsia.dowsers.eventstore;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.jdbcx.JdbcConnectionPool;

/**
 * Utility class to call for services test, to clear and fill tables used for
 * test table.
 * 
 * @author LRI
 * 
 */
public class H2DataUtility {

	private static final String DB_URL = "jdbc:h2:~/core";

	// private static final String CONFIG_INSERT =
	// "CREATE TABLE CONFIG AS SELECT * FROM CSVREAD('classpath:data/configuration_table.csv')";
	// private static final String CONFIG_DROP = "DROP TABLE IF EXISTS CONFIG";

	private static JdbcConnectionPool connectionPool;

	// /**
	// * Drop existing table Config
	// *
	// * @throws Exception
	// */
	// public static void dropConfig() {
	// executeRequest(CONFIG_DROP);
	//
	// }
	//
	// /**
	// * Populate table Config with configuration_table.csv
	// *
	// * @throws Exception
	// */
	// public static void populateConfig() {
	// dropConfig();
	// executeRequest("");
	// }

	/**
	 * Execute a request
	 * 
	 * @param request
	 */
	public static void executeRequest(final String request) {
		try {
			final Connection connection = H2DataUtility.getConnectionPool().getConnection();
			final Statement statement = connection.createStatement();
			statement.execute(request);
			statement.close();
			connection.close();
		} catch (final SQLException e) {
			throw new IllegalStateException("Problem accessing H2 database", e);
		} catch (final ClassNotFoundException e) {
			throw new IllegalStateException("Unable to find : org.h2.Driver", e);
		}
	}

	/**
	 * Get connection (and create it if needed)
	 * 
	 * @throws ClassNotFoundException
	 * 
	 * @throws Exception
	 */
	private static JdbcConnectionPool getConnectionPool() throws ClassNotFoundException {
		if (H2DataUtility.connectionPool == null) {
			Class.forName("org.h2.Driver");
			if (H2DataUtility.DB_URL.contains("$")) {
				H2DataUtility.connectionPool = JdbcConnectionPool.create("jdbc:h2:~/mega-atlas-service-test-dev", "", "");
			} else {
				H2DataUtility.connectionPool = JdbcConnectionPool.create(H2DataUtility.DB_URL, "", "");
			}

		}
		return H2DataUtility.connectionPool;
	}
}
