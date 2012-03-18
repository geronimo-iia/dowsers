/**
 * 
 */
package com.iia.cqrs.sample.domain;

import java.util.ArrayList;
import java.util.List;

import com.iia.cqrs.Entity;
import com.iia.cqrs.Identifier;
import com.iia.cqrs.Link;
import com.iia.cqrs.annotation.DomainEntity;
import com.iia.cqrs.annotation.Property;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
@DomainEntity
public class AddressBook extends Entity {

	@Property
	private List<Link<Contact>> contacts;

	/**
	 * Build a new instance of <code>AddressBook</code>
	 */
	public AddressBook() {
		super();
		contacts = new ArrayList<Link<Contact>>();
	}

	/**
	 * Build a new instance of <code>AddressBook</code>
	 * 
	 * @param identifier
	 */
	public AddressBook(Identifier identifier) {
		super(identifier);
	}

	public void add(Contact contact) {
		contacts.add(new Link<Contact>(contact));
	}

	/**
	 * @return the contacts
	 */
	public List<Link<Contact>> getContacts() {
		return this.contacts;
	}
}
