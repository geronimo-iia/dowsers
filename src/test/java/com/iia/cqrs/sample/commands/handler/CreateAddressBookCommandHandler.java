/**
 * 
 */
package com.iia.cqrs.sample.commands.handler;

import com.iia.cqrs.command.CommandHandler;
import com.iia.cqrs.command.CommandRegistry;
import com.iia.cqrs.domain.AggregateFactory;
import com.iia.cqrs.domain.DomainRepository;
import com.iia.cqrs.sample.commands.CreateAddressBookCommand;
import com.iia.cqrs.sample.domain.addressbook.AddressBook;

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
	public CreateAddressBookCommandHandler(CommandRegistry commandRegistry, DomainRepository domainRepository,
			AggregateFactory aggregateFactory) throws NullPointerException {
		super(commandRegistry);
		this.domainRepository = domainRepository;
		this.aggregateFactory = aggregateFactory;
	}

	/**
	 * @see com.iia.cqrs.command.CommandHandler#onCommand(com.iia.cqrs.command.Command)
	 */
	@Override
	public void onCommand(final CreateAddressBookCommand command) {
		domainRepository.add(new AddressBook(aggregateFactory));
	}
}
