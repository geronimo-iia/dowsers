/**
 * 
 */
package com.iia.cqrs.sample.commands;

import java.util.UUID;

import com.iia.cqrs.command.Command;
import com.iia.cqrs.command.CommandInvoker;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class AddContactToAddressBookCommand extends Command {
	private final UUID addressBookIdentifier;
	private final UUID contact;

	/**
	 * Build a new instance of AddContactToAddressBookCommand.
	 * 
	 * @param commandInvoker
	 * @param addressBookIdentifier
	 * @param contact
	 * @throws NullPointerException
	 */
	public AddContactToAddressBookCommand(CommandInvoker commandInvoker, UUID addressBookIdentifier, UUID contact) throws NullPointerException {
		super(commandInvoker);
		this.addressBookIdentifier = addressBookIdentifier;
		this.contact = contact;
	}

	/**
	 * @return the contact
	 */
	public UUID getContact() {
		return this.contact;
	}

	public UUID getAddressBookIdentifier() {
		return this.addressBookIdentifier;
	}
}
