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
package com.intelligentsia.dowsers.entity.model;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import com.google.common.collect.Maps;

public class Description implements Iterable<LocalizedDescription> {

	private Map<Locale, String> descriptions;

	public Description() {
		descriptions = Maps.newHashMap();
	}

	public Map<Locale, String> getDescriptions() {
		return descriptions;
	}

	public Description add(final Locale locale, final String description) {
		descriptions.put(locale, description);
		return this;
	}

	public void setDescriptions(final Map<Locale, String> descriptions) {
		this.descriptions = descriptions;
	}

	@Override
	public Iterator<LocalizedDescription> iterator() {
		final Iterator<Map.Entry<Locale, String>> iterator = descriptions.entrySet().iterator();
		return new Iterator<LocalizedDescription>() {
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public LocalizedDescription next() {
				final Map.Entry<Locale, String> entry = iterator.next();
				return new LocalizedDescription(entry.getKey(), entry.getValue());
			}

			@Override
			public void remove() {
			}

		};
	}

}
