/**
 * 
 */
package com.iia.cqrs.sample.commands.handler;

import com.iia.cqrs.command.CommandHandler;
import com.iia.cqrs.command.CommandRegistry;
import com.iia.cqrs.domain.AggregateFactory;
import com.iia.cqrs.domain.DomainRepository;
import com.iia.cqrs.sample.commands.CreateContactCommand;
import com.iia.cqrs.sample.domain.contact.Contact;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class CreateContactCommandHandler extends CommandHandler<CreateContactCommand> {

	private final DomainRepository domainRepository;

	private final AggregateFactory aggregateFactory;

	/**
	 * Build a new instance of <code>CreateContactCommandHandler</code>
	 * 
	 * @param commandRegistry
	 * @param domainRepository
	 * @param aggregateFactory
	 * @throws NullPointerException
	 */
	public CreateContactCommandHandler(CommandRegistry commandRegistry, DomainRepository domainRepository,
			AggregateFactory aggregateFactory) throws NullPointerException {
		super(commandRegistry);
		this.domainRepository = domainRepository;
		this.aggregateFactory = aggregateFactory;
	}

	/**
	 * @see com.iia.cqrs.command.CommandHandler#onCommand(com.iia.cqrs.command.Command)
	 */
	@Override
	public void onCommand(final CreateContactCommand command) {
		final Contact contact = new Contact(aggregateFactory, command.getName());
		domainRepository.add(contact);
	}
}
