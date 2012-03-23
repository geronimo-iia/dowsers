/**
 * 
 */
package com.iia.cqrs.sample.domain.addressbook;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.iia.cqrs.annotation.Property;
import com.iia.cqrs.domain.AggregateFactory;
import com.iia.cqrs.domain.DomainEntity;
import com.iia.cqrs.domain.Identifier;
import com.iia.cqrs.sample.domain.contact.Contact;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class AddressBook extends DomainEntity {

	@Property
	private final List<UUID> contacts = new ArrayList<UUID>();

	/**
	 * Build a new instance of <code>AddressBook</code>
	 * 
	 * @param aggregateFactory
	 * @param identifier
	 * @throws NullPointerException
	 */
	public AddressBook(AggregateFactory aggregateFactory, Identifier identifier) throws NullPointerException {
		super(aggregateFactory, identifier);
	}

	/**
	 * Build a new instance of <code>AddressBook</code>
	 * 
	 * @param aggregateFactory
	 */
	public AddressBook(AggregateFactory aggregateFactory) {
		super(aggregateFactory);
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
