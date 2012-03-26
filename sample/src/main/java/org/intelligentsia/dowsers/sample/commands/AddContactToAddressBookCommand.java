/**
 * 
 */
package org.intelligentsia.dowsers.sample.commands;

import java.util.UUID;

import org.intelligentsia.dowsers.command.Command;
import org.intelligentsia.dowsers.command.CommandInvoker;


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
	public AddContactToAddressBookCommand(final CommandInvoker commandInvoker, final UUID addressBookIdentifier, final UUID contact) throws NullPointerException {
		super(commandInvoker);
		this.addressBookIdentifier = addressBookIdentifier;
		this.contact = contact;
	}

	/**
	 * @return the contact
	 */
	public UUID getContact() {
		return contact;
	}

	public UUID getAddressBookIdentifier() {
		return addressBookIdentifier;
	}
}
