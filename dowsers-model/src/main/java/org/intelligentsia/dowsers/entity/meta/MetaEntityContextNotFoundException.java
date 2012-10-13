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
package org.intelligentsia.dowsers.entity.meta;

import org.intelligentsia.dowsers.core.DowsersException;

/**
 * MetaEntityContextNotFoundException.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MetaEntityContextNotFoundException extends DowsersException {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -1511366330372733890L;

	/**
	 * Build a new instance of <code>MetaEntityContextNotFoundException</code>.
	 */
	public MetaEntityContextNotFoundException() {
		super();
	}

	/**
	 * Build a new instance of <code>MetaEntityContextNotFoundException</code>.
	 * 
	 * @param message
	 * @param cause
	 */
	public MetaEntityContextNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Build a new instance of <code>MetaEntityContextNotFoundException</code>.
	 * 
	 * @param message
	 */
	public MetaEntityContextNotFoundException(String message) {
		super(message);
	}

	/**
	 * Build a new instance of <code>MetaEntityContextNotFoundException</code>.
	 * 
	 * @param cause
	 */
	public MetaEntityContextNotFoundException(Throwable cause) {
		super(cause);
	}

}
