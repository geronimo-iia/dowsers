/**
 * 
 */
package com.iia.cqrs.sample.commands.handler;

import javax.annotation.PostConstruct;

import com.google.common.eventbus.Subscribe;
import com.iia.cqrs.DomainRepository;
import com.iia.cqrs.command.CommandRegistry;
import com.iia.cqrs.sample.commands.CreateContactCommand;
import com.iia.cqrs.sample.domain.Contact;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class CreateContactCommandHandler {

	private final DomainRepository domainRepository;
	private final CommandRegistry commandRegistry;

	/**
	 * Build a new instance of <code>CreateContactCommandHandler</code>
	 * 
	 * @param domainRepository
	 * @param commandRegistry
	 */
	public CreateContactCommandHandler(DomainRepository domainRepository, CommandRegistry commandRegistry) {
		super();
		this.domainRepository = domainRepository;
		this.commandRegistry = commandRegistry;
	}

	@PostConstruct
	protected void initalize() {
		commandRegistry.register(this);
	}

	@Subscribe
	public void onCreateContactCommand(CreateContactCommand command) {
		Contact contact = new Contact(command.getName());
		domainRepository.add(contact);
	}
}
