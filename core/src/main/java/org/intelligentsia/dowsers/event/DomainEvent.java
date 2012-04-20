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
package org.intelligentsia.dowsers.event;

import java.io.Serializable;

import org.intelligentsia.dowsers.core.IdentifierFactoryProvider;
import org.intelligentsia.dowsers.domain.Entity;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * DomainEvent are internal events with the purpose to capturing intent.
 * 
 * 
 * We separate the domain behavior from the state changes resulting from this
 * domain behavior including the triggering of external behavior.<br />
 * 
 * 
 * Instead of writing these state changes directly to the internal variables you
 * create an event and fire it internally.<br />
 * This event as well as the method name of the behavior should be descriptive
 * in the Ubiquitous Language of the domain.<br />
 * Then the event will be handled inside the domain Aggregate Root which will
 * set the internal state to the correct values.<br />
 * Remember that the event handler should not be doing any logic other then
 * setting the state, the logic should be in the domain method.
 * 
 * 
 * DomainEvent class has a natural order on ordinal value and her equality is
 * based on event identity.
 * 
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public abstract class DomainEvent implements Comparable<DomainEvent>, Serializable {
	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -1L;
	/**
	 * Event identity instance.
	 */
	private final String eventIdentity;
	/**
	 * Event ordinal value.
	 */
	private long ordinal;
	/**
	 * Entity identity instance.
	 */
	private final String entityIdentity;
	/**
	 * Command identity instance.
	 */
	private String commandIdentity;

	/**
	 * Build a new instance of DomainEvent.
	 * 
	 * @param entity
	 *            entity which event carries on
	 * @throws NullPointerException
	 *             if entity is null
	 */
	public DomainEvent(final Entity entity) throws NullPointerException {
		this(IdentifierFactoryProvider.generateNewIdentifier(), Preconditions.checkNotNull(entity).getIdentity());
	}

	/**
	 * Build a new instance of DomainEvent.
	 * 
	 * @param eventIdentity
	 *            identity of this event
	 * @param entityIdentity
	 *            identity of entity which event carries on
	 */
	protected DomainEvent(final String eventIdentity, final String entityIdentity) {
		super();
		this.eventIdentity = eventIdentity;
		this.entityIdentity = entityIdentity;
		ordinal = 0;
	}

	/**
	 * @return the entityIdentity
	 */
	public String getEntityIdentity() {
		return entityIdentity;
	}

	/**
	 * @return the eventIdentity
	 */
	public String getEventIdentity() {
		return eventIdentity;
	}

	/**
	 * @return ordinal value of this event.
	 */
	public long getOrdinal() {
		return ordinal;
	}

	/**
	 * Set ordinal value.
	 * 
	 * @param ordinal
	 *            ordinal value to set
	 */
	void setOrdinal(final long ordinal) {
		this.ordinal = ordinal;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("eventIdentity", eventIdentity).add("ordinal", ordinal).add("entityIdentity", entityIdentity).toString();
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final DomainEvent o) {
		return Long.valueOf(ordinal).compareTo(o.ordinal);
	}

	/**
	 * DomainEvent compare by identity, not by attributes.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DomainEvent other = (DomainEvent) obj;
		return Objects.equal(other.getEventIdentity(), getEventIdentity());
	}

	/**
	 * @return the commandIdentity
	 */
	public String getCommandIdentity() {
		return commandIdentity;
	}

}
