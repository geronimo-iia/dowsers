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
package org.intelligentsia.dowsers.domain;

import org.intelligentsia.dowsers.DowsersException;

/**
 * DomainEntityNotFoundException.
 * 
 * @author jgt
 * 
 */
public class DomainEntityNotFoundException extends DowsersException {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = 1022622007007509677L;

	/**
	 * Build a new instance of DomainEntityNotFoundException.
	 * 
	 * @param identity
	 */
	public DomainEntityNotFoundException(final String identity) {
		super(identity);
	}

	/**
	 * Build a new instance of DomainEntityNotFoundException.
	 * 
	 * @param identity
	 * @param message
	 */
	public DomainEntityNotFoundException(final String identity, final String message) {
		super(identity, message);
	}

	/**
	 * Build a new instance of DomainEntityNotFoundException.
	 * 
	 * @param identity
	 * @param cause
	 */
	public DomainEntityNotFoundException(final String identity, final Throwable cause) {
		super(identity, cause);
	}

	/**
	 * Build a new instance of DomainEntityNotFoundException.
	 * 
	 * @param identity
	 * @param message
	 * @param cause
	 */
	public DomainEntityNotFoundException(final String identity, final String message, final Throwable cause) {
		super(identity, message, cause);
	}

}
