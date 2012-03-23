/**
 * 
 */
package com.iia.cqrs.sample.commands.handler;

import com.iia.cqrs.command.CommandHandler;
import com.iia.cqrs.command.CommandRegistry;
import com.iia.cqrs.domain.DomainRepository;
import com.iia.cqrs.sample.commands.AddContactToAddressBookCommand;
import com.iia.cqrs.sample.domain.addressbook.AddressBook;
import com.iia.cqrs.sample.domain.contact.Contact;

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
	public AddContactToAddressBookCommandHandler(final CommandRegistry commandRegistry, final DomainRepository domainRepository) throws NullPointerException {
		super(commandRegistry);
		this.domainRepository = domainRepository;
	}

	/**
	 * @see com.iia.cqrs.command.CommandHandler#onCommand(com.iia.cqrs.command.Command)
	 */
	@Override
	public void onCommand(final AddContactToAddressBookCommand command) {
		final AddressBook addressBook = domainRepository.findByIdentifier(AddressBook.class, command.getAddressBookIdentifier());
		final Contact contact = domainRepository.findByIdentifier(Contact.class, command.getContact());
		addressBook.add(contact);
	}

}
