/**
 * 
 */
package org.intelligentsia.dowsers.sample.commands.handler;

import org.intelligentsia.dowsers.command.CommandHandler;
import org.intelligentsia.dowsers.command.CommandHandlerRegistry;
import org.intelligentsia.dowsers.domain.AggregateFactory;
import org.intelligentsia.dowsers.domain.DomainRepository;
import org.intelligentsia.dowsers.sample.commands.CreateContactCommand;
import org.intelligentsia.dowsers.sample.domain.contact.Contact;


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
	public CreateContactCommandHandler(CommandHandlerRegistry commandRegistry, DomainRepository domainRepository,
			AggregateFactory aggregateFactory) throws NullPointerException {
		super(commandRegistry);
		this.domainRepository = domainRepository;
		this.aggregateFactory = aggregateFactory;
	}

	/**
	 * @see org.intelligentsia.dowsers.command.CommandHandler#onCommand(org.intelligentsia.dowsers.command.Command)
	 */
	@Override
	public void onCommand(final CreateContactCommand command) {
		final Contact contact = new Contact(aggregateFactory, command.getName());
		domainRepository.add(contact);
	}
}
