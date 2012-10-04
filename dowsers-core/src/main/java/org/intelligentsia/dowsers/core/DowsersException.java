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

/**
 * DowsersException extends RuntimeException. Root exception of dowsers project.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DowsersException extends RuntimeException {
	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * identity instance.
	 */
	private final String identity;

	/**
	 * Build a new instance of DowsersException.java.
	 * 
	 * @param identity
	 */
	public DowsersException(final String identity) {
		super();
		this.identity = identity;
	}

	/**
	 * Build a new instance of DowsersException.java.
	 * 
	 * @param identity
	 * @param message
	 */
	public DowsersException(final String identity, final String message) {
		super(message);
		this.identity = identity;
	}

	/**
	 * 
	 * Build a new instance of DowsersException.
	 * 
	 * @param identity
	 * @param cause
	 */
	public DowsersException(final String identity, final Throwable cause) {
		super(cause);
		this.identity = identity;
	}

	/**
	 * Build a new instance of DowsersException.
	 * 
	 * @param message
	 * @param cause
	 */
	public DowsersException(final String identity, final String message, final Throwable cause) {
		super(message, cause);
		this.identity = identity;
	}

	/**
	 * @return the identity
	 */
	public String getIdentity() {
		return identity;
	}

	@Override
	public String toString() {
		return "DowsersException [identity=" + identity + ", message=" + getMessage() + "]";
	}

}
