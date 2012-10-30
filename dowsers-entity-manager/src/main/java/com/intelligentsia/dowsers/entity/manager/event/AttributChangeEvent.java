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
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * AttributChangeEvent.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class AttributChangeEvent extends ReferenceableEvent {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = 6776401362136948224L;

	private final Object value;

	/**
	 * Build a new instance of AttributChangeEvent.java.
	 * 
	 * @param reference
	 * @param value
	 * @throws NullPointerException
	 *             if reference is null
	 */
	public AttributChangeEvent(final Reference reference, final Object value) throws NullPointerException {
		super(reference);
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(reference, value);
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
		final AttributChangeEvent other = (AttributChangeEvent) obj;
		return Objects.equal(reference, other.reference) && Objects.equal(value, other.value);
	}

	@Override
	public String toString() {
		return "AttributChangeEvent [reference=" + reference + ", value=" + value + "]";
	}

}
