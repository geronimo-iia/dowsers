/**
 * 
 */
package com.iia.cqrs.sample.commands.handler;

import javax.annotation.PostConstruct;

import com.google.common.eventbus.Subscribe;
import com.iia.cqrs.DomainRepository;
import com.iia.cqrs.command.CommandRegistry;
import com.iia.cqrs.sample.commands.CreateAddressBookCommand;
import com.iia.cqrs.sample.domain.AddressBook;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class CreateAddressBookCommandHandler {

	private final DomainRepository domainRepository;
	private final CommandRegistry commandRegistry;

	/**
	 * Build a new instance of <code>CreateAddressBookCommandHandler</code>
	 * 
	 * @param domainRepository
	 * @param commandRegistry
	 */
	public CreateAddressBookCommandHandler(DomainRepository domainRepository, CommandRegistry commandRegistry) {
		super();
		this.domainRepository = domainRepository;
		this.commandRegistry = commandRegistry;
	}

	@PostConstruct
	protected void initalize() {
		commandRegistry.register(this);
	}

	@Subscribe
	public void onCreateAddressBookCommand(CreateAddressBookCommand command) {
		domainRepository.add(new AddressBook());
	}
}
