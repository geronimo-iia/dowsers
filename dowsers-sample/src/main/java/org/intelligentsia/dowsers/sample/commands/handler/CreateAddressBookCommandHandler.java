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
/**
 * 
 */
package org.intelligentsia.dowsers.sample.commands.handler;

import org.intelligentsia.dowsers.command.CommandHandler;
import org.intelligentsia.dowsers.command.CommandHandlerRegistry;
import org.intelligentsia.dowsers.domain.AggregateFactory;
import org.intelligentsia.dowsers.domain.DomainRepository;
import org.intelligentsia.dowsers.sample.commands.CreateAddressBookCommand;
import org.intelligentsia.dowsers.sample.domain.addressbook.AddressBook;


/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class CreateAddressBookCommandHandler extends CommandHandler<CreateAddressBookCommand> {

	private final DomainRepository domainRepository;
	private final AggregateFactory aggregateFactory;

	/**
	 * Build a new instance of <code>CreateAddressBookCommandHandler</code>
	 * 
	 * @param commandRegistry
	 * @param domainRepository
	 * @param aggregateFactory
	 * @throws NullPointerException
	 */
	public CreateAddressBookCommandHandler(CommandHandlerRegistry commandRegistry, DomainRepository domainRepository,
			AggregateFactory aggregateFactory) throws NullPointerException {
		super(commandRegistry);
		this.domainRepository = domainRepository;
		this.aggregateFactory = aggregateFactory;
	}

	/**
	 * @see org.intelligentsia.dowsers.command.CommandHandler#onCommand(org.intelligentsia.dowsers.command.Command)
	 */
	@Override
	public void onCommand(final CreateAddressBookCommand command) {
		domainRepository.store(new AddressBook(aggregateFactory));
	}
}
