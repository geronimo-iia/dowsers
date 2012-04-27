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
package org.intelligentsia.dowsers.event.snapshot;

import java.lang.reflect.Field;
import java.util.List;

import org.intelligentsia.dowsers.annotation.Property;
import org.intelligentsia.dowsers.domain.Aggregate;
import org.intelligentsia.dowsers.event.DomainEvent;

/**
 * PropertySnapshotEventGenerator implements SnapshotGenerator using Property
 * annotation.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class PropertySnapshotEventGenerator implements SnapshotGenerator {

	/**
	 * @see org.intelligentsia.dowsers.event.snapshot.SnapshotGenerator#generate(org.intelligentsia.dowsers.domain.Entity)
	 */
	@Override
	public SnapshotEvent generate(final Aggregate aggregate) {
		// generate sumary
		final List<DomainEvent> summary = generateSummary(aggregate);
		// build snapshot event
		final SnapshotEvent snapshotEvent = new SnapshotEvent(aggregate.getRoot().getIdentity(), aggregate.getVersion(), summary);
		return snapshotEvent;
	}

	/**
	 * @param aggregate
	 * @return a domain event summary list
	 */
	protected List<DomainEvent> generateSummary(final Aggregate aggregate) {
		// could be very complicated if we considere local entity and
		// collection.
		final Class<?> entityClass = aggregate.getRoot().getClass();
		for (final Field field : entityClass.getFields()) {
			final Property property = field.getAnnotation(Property.class);
			if (property != null) {

			}
		}

		return null;
	}

}
