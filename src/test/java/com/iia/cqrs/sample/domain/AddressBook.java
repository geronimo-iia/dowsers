/**
 * 
 */
package com.iia.cqrs.sample.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.iia.cqrs.Entity;
import com.iia.cqrs.annotation.DomainEntity;
import com.iia.cqrs.annotation.Property;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
@DomainEntity
public class AddressBook extends Entity {

	@Property
	private final List<UUID> contacts;

	/**
	 * Build a new instance of <code>AddressBook</code>
	 */
	public AddressBook() {
		super();
		contacts = new ArrayList<UUID>();
	}

	public void add(final Contact contact) {
		contacts.add(contact.getIdentifier().getIdentity());
	}

	/**
	 * @return the contacts
	 */
	public List<UUID> getContacts() {
		return contacts;
	}

	/**
	 * @return
	 */
	public int size() {
		return contacts.size();
	}
}
