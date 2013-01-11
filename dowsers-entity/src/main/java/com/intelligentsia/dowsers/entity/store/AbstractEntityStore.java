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
package com.intelligentsia.dowsers.entity.store;

import java.util.Iterator;

import org.intelligentsia.dowsers.core.reflection.ClassInformation;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.reference.References;

/**
 * AbstractEntityStore implement some behaviour like
 * {@link EntityStore#find(Reference)}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public abstract class AbstractEntityStore implements EntityStore {

	@Override
	public Iterable<Reference> find(final Reference reference) throws NullPointerException {
		final Class<?> expectedType = ClassInformation.parse(reference.getEntityClassName()).getType();
		// filtering result from find(final Class<?> expectedType)
		final Iterator<Reference> iterator = Iterators.filter(find(expectedType).iterator(), new Predicate<Reference>() {
			@Override
			public boolean apply(final Reference input) {
				final Object object = find(expectedType, input);
				if (object != null) {
					final Entity entity = References.discover(object);
					final Object attribute = entity.attribute(reference.getAttributeName());
					if (attribute != null) {
						return attribute.equals(reference.getIdentity());
					}
				}
				return false;
			}
		});
		// return final iterator
		return new Iterable<Reference>() {

			@Override
			public Iterator<Reference> iterator() {
				return iterator;
			}
		};
	}

}
