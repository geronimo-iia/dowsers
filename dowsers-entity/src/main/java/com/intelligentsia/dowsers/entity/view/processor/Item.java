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
package com.intelligentsia.dowsers.entity.view.processor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.intelligentsia.keystone.kernel.api.Preconditions;

import com.google.common.base.Objects;

/**
 * Item.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class Item {

	private Map<String, Object> attributes;

	public Item() {
		this(new HashMap<String, Object>());
	}

	public Item(final Map<String, Object> attributes) throws NullPointerException {
		super();
		this.attributes = Preconditions.checkNotNull(attributes);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(getClass()).add("attributes", attributes).toString();
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(attributes);
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
		final Item other = (Item) obj;
		return Objects.equal(attributes, other.attributes);
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(final Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public int size() {
		return attributes.size();
	}

	public boolean isEmpty() {
		return attributes.isEmpty();
	}

	public Object get(final String key) {
		return attributes.get(key);
	}

	public Object put(final String key, final Object value) {
		return attributes.put(key, value);
	}

	public Object remove(final String key) {
		return attributes.remove(key);
	}

	public void clear() {
		attributes.clear();
	}

	public Set<String> keySet() {
		return attributes.keySet();
	}

	public void putAll(final Map<? extends String, ? extends Object> m) {
		attributes.putAll(m);
	}

}
