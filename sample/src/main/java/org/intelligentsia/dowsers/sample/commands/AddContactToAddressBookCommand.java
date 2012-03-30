/**
 * 
 */
package org.intelligentsia.dowsers.sample.commands;

import org.intelligentsia.dowsers.command.Command;
import org.intelligentsia.dowsers.command.CommandInvoker;


/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class AddContactToAddressBookCommand extends Command {
	private final String addressBookIdentifier;
	private final String contact;

	/**
	 * Build a new instance of AddContactToAddressBookCommand.
	 * 
	 * @param commandInvoker
	 * @param addressBookIdentifier
	 * @param contact
	 * @throws NullPointerException
	 */
	public AddContactToAddressBookCommand(final CommandInvoker commandInvoker, final String addressBookIdentifier, final String contact) throws NullPointerException {
		super(commandInvoker);
		this.addressBookIdentifier = addressBookIdentifier;
		this.contact = contact;
	}

	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}

	public String getAddressBookIdentifier() {
		return addressBookIdentifier;
	}
}
