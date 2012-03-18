/**
 * 
 */
package com.iia.cqrs.sample.commands;

import java.util.UUID;

import com.iia.cqrs.command.Command;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class AddContactToAddressBookCommand extends Command {
	private final UUID addressBookIdentifier;
	private final UUID contact;

	/**
	 * Build a new instance of <code>AddContactToAddressBookCommand</code>
	 * 
	 * @param addressBookIdentifier
	 * @param name
	 */
	public AddContactToAddressBookCommand(UUID addressBookIdentifier, UUID contact) {
		super();
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
