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

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.intelligentsia.dowsers.entity.reference.Reference;

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
	protected final Reference reference;

	/**
	 * Build a new instance of ReferenceableEvent.java.
	 * 
	 * @param reference
	 * @throws NullPointerException
	 *             if reference is null
	 */
	public ReferenceableEvent(final Reference reference) throws NullPointerException {
		super();
		this.reference = Preconditions.checkNotNull(reference);
	}

	public Reference getReference() {
		return reference;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(reference);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ReferenceableEvent other = (ReferenceableEvent) obj;
		return Objects.equal(reference, other.reference);
	}

}
