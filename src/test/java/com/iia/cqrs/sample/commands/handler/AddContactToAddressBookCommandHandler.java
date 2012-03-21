/**
 * 
 */
package com.iia.cqrs.sample.commands.handler;

import com.iia.cqrs.DomainRepository;
import com.iia.cqrs.command.CommandHandler;
import com.iia.cqrs.command.CommandRegistry;
import com.iia.cqrs.sample.commands.AddContactToAddressBookCommand;
import com.iia.cqrs.sample.domain.AddressBook;
import com.iia.cqrs.sample.domain.Contact;

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
	public AddContactToAddressBookCommandHandler(CommandRegistry commandRegistry, DomainRepository domainRepository) throws NullPointerException {
		super(commandRegistry);
		this.domainRepository = domainRepository;
	}

	/**
	 * @see com.iia.cqrs.command.CommandHandler#onCommand(com.iia.cqrs.command.Command)
	 */
	@Override
	public void onCommand(AddContactToAddressBookCommand command) {
		AddressBook addressBook = domainRepository.findByIdentifier(AddressBook.class, command.getAddressBookIdentifier());
		Contact contact = domainRepository.findByIdentifier(Contact.class, command.getContact());
		addressBook.add(contact);
	}

}
