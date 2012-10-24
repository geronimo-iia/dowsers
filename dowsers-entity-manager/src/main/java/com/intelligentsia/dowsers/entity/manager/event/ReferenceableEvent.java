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
package com.intelligentsia.dowsers.entity.manager.event;

import java.io.Serializable;
import java.net.URI;

import com.google.common.base.Preconditions;

/**
 * ReferenceableEvent Event is base class for all dowsers event.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class ReferenceableEvent implements Serializable {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -279650386406114338L;

	/**
	 * URN
	 */
	protected final URI reference;

	/**
	 * Build a new instance of ReferenceableEvent.java.
	 * 
	 * @param reference
	 * @throws NullPointerException
	 *             if reference is null
	 */
	public ReferenceableEvent(URI reference) throws NullPointerException {
		super();
		this.reference = Preconditions.checkNotNull(reference);
	}

	public URI getReference() {
		return reference;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reference == null) ? 0 : reference.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReferenceableEvent other = (ReferenceableEvent) obj;
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		return true;
	}

}
