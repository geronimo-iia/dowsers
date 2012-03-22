/**
 * 
 */
package com.iia.cqrs.sample.commands.handler;

import com.iia.cqrs.DomainRepository;
import com.iia.cqrs.command.CommandHandler;
import com.iia.cqrs.command.CommandRegistry;
import com.iia.cqrs.sample.commands.CreateAddressBookCommand;
import com.iia.cqrs.sample.domain.AddressBook;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class CreateAddressBookCommandHandler extends CommandHandler<CreateAddressBookCommand> {

	private final DomainRepository domainRepository;

	/**
	 * Build a new instance of CreateAddressBookCommandHandler.
	 * 
	 * @param commandRegistry
	 * @param domainRepository
	 * @throws NullPointerException
	 */
	public CreateAddressBookCommandHandler(final CommandRegistry commandRegistry, final DomainRepository domainRepository) throws NullPointerException {
		super(commandRegistry);
		this.domainRepository = domainRepository;
	}

	/**
	 * @see com.iia.cqrs.command.CommandHandler#onCommand(com.iia.cqrs.command.Command)
	 */
	@Override
	public void onCommand(final CreateAddressBookCommand command) {
		domainRepository.add(new AddressBook());
	}
}
