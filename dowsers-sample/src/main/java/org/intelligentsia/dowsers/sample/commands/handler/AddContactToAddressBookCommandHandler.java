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
import org.intelligentsia.dowsers.domain.DomainRepository;
import org.intelligentsia.dowsers.sample.commands.AddContactToAddressBookCommand;
import org.intelligentsia.dowsers.sample.domain.addressbook.AddressBook;
import org.intelligentsia.dowsers.sample.domain.contact.Contact;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class AddContactToAddressBookCommandHandler extends CommandHandler<AddContactToAddressBookCommand> {

	private final DomainRepository domainRepository;

	/**
	 * Build a new instance of AddContactToAddressBookCommandHandler.
	 * 
	 * @param commandRegistry
	 * @param domainRepository
	 * @throws NullPointerException
	 */
	public AddContactToAddressBookCommandHandler(final CommandHandlerRegistry commandRegistry, final DomainRepository domainRepository) throws NullPointerException {
		super(commandRegistry);
		this.domainRepository = domainRepository;
	}

	/**
	 * @see org.intelligentsia.dowsers.command.CommandHandler#onCommand(org.intelligentsia.dowsers.command.Command)
	 */
	@Override
	public void onCommand(final AddContactToAddressBookCommand command) {
		final AddressBook addressBook = domainRepository.find(AddressBook.class, command.getAddressBookIdentifier());
		final Contact contact = domainRepository.find(Contact.class, command.getContact());
		addressBook.add(contact);
	}

}
