/**
 * 
 */
package com.iia.cqrs.sample.commands.handler;

import com.iia.cqrs.DomainRepository;
import com.iia.cqrs.command.CommandHandler;
import com.iia.cqrs.command.CommandRegistry;
import com.iia.cqrs.sample.commands.CreateContactCommand;
import com.iia.cqrs.sample.domain.Contact;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class CreateContactCommandHandler extends CommandHandler<CreateContactCommand> {

	private final DomainRepository domainRepository;

	/**
	 * Build a new instance of CreateContactCommandHandler.
	 * 
	 * @param commandRegistry
	 * @param domainRepository
	 * @throws NullPointerException
	 */
	public CreateContactCommandHandler(final CommandRegistry commandRegistry, final DomainRepository domainRepository) throws NullPointerException {
		super(commandRegistry);
		this.domainRepository = domainRepository;
	}

	/**
	 * @see com.iia.cqrs.command.CommandHandler#onCommand(com.iia.cqrs.command.Command)
	 */
	@Override
	public void onCommand(final CreateContactCommand command) {
		final Contact contact = new Contact(command.getName());
		domainRepository.add(contact);
	}
}
