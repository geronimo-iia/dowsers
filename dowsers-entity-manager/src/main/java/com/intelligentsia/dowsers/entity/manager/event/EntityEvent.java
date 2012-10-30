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

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * EntityEvent.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityEvent extends ReferenceableEvent {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = 6789876342404165413L;

	public enum Kind {
		CREATED, UPDATED, REMOVED
	}

	protected final Kind kind;

	/**
	 * Build a new instance of EntityEvent.java.
	 * 
	 * @param reference
	 * @param kind
	 * @throws NullPointerException
	 */
	public EntityEvent(final Reference reference, final Kind kind) throws NullPointerException {
		super(reference);
		this.kind = Preconditions.checkNotNull(kind);
	}

	public Kind getKind() {
		return kind;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(reference, kind);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final EntityEvent other = (EntityEvent) obj;
		return Objects.equal(reference, other.reference) && Objects.equal(kind, other.kind);
	}

	@Override
	public String toString() {
		return "EntityEvent [reference=" + reference + ", kind=" + kind + "]";
	}

}
