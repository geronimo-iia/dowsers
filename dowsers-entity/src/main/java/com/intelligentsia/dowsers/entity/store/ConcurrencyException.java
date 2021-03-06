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
/**
 * 
 */
package com.intelligentsia.dowsers.entity.store;

/**
 * ConcurrencyException.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class ConcurrencyException extends RuntimeException {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = 4086184226630110642L;

	/**
	 * Build a new instance of ConcurrencyException.java.
	 */
	public ConcurrencyException() {
		super();
	}

	/**
	 * Build a new instance of ConcurrencyException.java.
	 * 
	 * @param message
	 */
	public ConcurrencyException(final String message) {
		super(message);
	}

	/**
	 * Build a new instance of ConcurrencyException.java.
	 * 
	 * @param cause
	 */
	public ConcurrencyException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Build a new instance of ConcurrencyException.java.
	 * 
	 * @param message
	 * @param cause
	 */
	public ConcurrencyException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
