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
		final AddressBook addressBook = domainRepository.findByIdentifier(AddressBook.class, command.getAddressBookIdentifier());
		final Contact contact = domainRepository.findByIdentifier(Contact.class, command.getContact());
		addressBook.add(contact);
	}

}
