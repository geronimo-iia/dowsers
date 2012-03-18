/**
 * 
 */
package com.iia.cqrs.sample.commands.handler;

import javax.annotation.PostConstruct;

import com.google.common.eventbus.Subscribe;
import com.iia.cqrs.DomainRepository;
import com.iia.cqrs.command.CommandRegistry;
import com.iia.cqrs.sample.commands.AddContactToAddressBookCommand;
import com.iia.cqrs.sample.domain.AddressBook;
import com.iia.cqrs.sample.domain.Contact;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class AddContactToAddressBookCommandHandler {

	private final DomainRepository domainRepository;
	private final CommandRegistry commandRegistry;

	/**
	 * Build a new instance of <code>AddContactToAddressBookCommandHandler</code>
	 * 
	 * @param domainRepository
	 * @param commandRegistry
	 */
	public AddContactToAddressBookCommandHandler(DomainRepository domainRepository, CommandRegistry commandRegistry) {
		super();
		this.domainRepository = domainRepository;
		this.commandRegistry = commandRegistry;
	}

	@PostConstruct
	protected void initalize() {
		commandRegistry.register(this);
	}

	@Subscribe
	public void onAddContactToAddressBookCommand(AddContactToAddressBookCommand command) {
		AddressBook addressBook = domainRepository.findByIdentifier(command.getAddressBookIdentifier());
		Contact contact = domainRepository.findByIdentifier(command.getContact());
		addressBook.add(contact);
	}
}
