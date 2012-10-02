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

import java.util.List;

/**
 * Represents a result from an insert operation with auto-generated keys.
 */
public final class Result {

	private final int rowsModified;
	private final List<String> generatedKeys;

	public Result(final int rowsModified, final List generatedKeys) {
		this.rowsModified = rowsModified;
		this.generatedKeys = generatedKeys;
	}

	public int getRowsModified() {
		return rowsModified;
	}

	public List<String> getGeneratedKeys() {
		return generatedKeys;
	}

}
