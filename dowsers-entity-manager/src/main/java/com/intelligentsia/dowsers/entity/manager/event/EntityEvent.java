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

import java.net.URI;

import com.google.common.base.Preconditions;

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
	public EntityEvent(URI reference, Kind kind) throws NullPointerException {
		super(reference);
		this.kind = Preconditions.checkNotNull(kind);
	}

	public Kind getKind() {
		return kind;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((kind == null) ? 0 : kind.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityEvent other = (EntityEvent) obj;
		if (kind != other.kind)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EntityEvent [reference=" + reference + ", kind=" + kind + "]";
	}

}
