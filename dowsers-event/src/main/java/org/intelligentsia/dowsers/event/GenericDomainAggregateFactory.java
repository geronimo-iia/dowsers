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

import org.intelligentsia.dowsers.domain.Aggregate;
import org.intelligentsia.dowsers.domain.AggregateFactory;
import org.intelligentsia.dowsers.domain.DomainEntity;
import org.intelligentsia.dowsers.event.processor.EventProcessorProvider;

import com.google.common.base.Preconditions;

/**
 * GenericDomainAggregateFactory implement a generic Factory for Aggregate
 * class. Hide EventProcessorProvider initialization.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class GenericDomainAggregateFactory implements AggregateFactory {

	/**
	 * EventProcessorProvider instance to use to create Aggregate instance.
	 */
	private final EventProcessorProvider eventProcessorProvider;

	/**
	 * Build a new instance of GenericDomainAggregateFactory.
	 * 
	 * @param eventProcessorProvider
	 *            eventProcessorProvider instance
	 * @throws NullPointerException
	 *             if eventProcessorProvider is null
	 */
	public GenericDomainAggregateFactory(final EventProcessorProvider eventProcessorProvider) throws NullPointerException {
		super();
		this.eventProcessorProvider = Preconditions.checkNotNull(eventProcessorProvider);
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.AggregateFactory#create(org.intelligentsia.dowsers.domain.DomainEntity)
	 */
	@Override
	public <T extends DomainEntity> DomainAggregate newInstance(final T domainEntity) throws NullPointerException {
		final DomainAggregate domainAggregate = new DomainAggregate(eventProcessorProvider);
		domainAggregate.registerRoot(domainEntity);
		return domainAggregate;
	}

	/**
	 * @see org.intelligentsia.dowsers.domain.AggregateFactory#newInstance()
	 */
	@Override
	public Aggregate newInstance() {
		return new DomainAggregate(eventProcessorProvider);
	}

}
