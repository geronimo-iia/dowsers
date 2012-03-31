/**
 * 
 */
package org.intelligentsia.dowsers.sample.domain.addressbook;

import java.util.ArrayList;
import java.util.List;

import org.intelligentsia.dowsers.annotation.Property;
import org.intelligentsia.dowsers.domain.AggregateFactory;
import org.intelligentsia.dowsers.domain.DomainEntity;
import org.intelligentsia.dowsers.sample.domain.contact.Contact;


/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class AddressBook extends DomainEntity {

	@Property
	private final List<String> contacts = new ArrayList<String>();

	/**
	 * Build a new instance of <code>AddressBook</code>
	 * 
	 * @param aggregateFactory
	 * @param identifier
	 * @throws NullPointerException
	 */
	public AddressBook(AggregateFactory aggregateFactory, String identifier) throws NullPointerException {
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
		contacts.add(contact.getIdentity());
	}

	/**
	 * @return the contacts
	 */
	public List<String> getContacts() {
		return contacts;
	}

	/**
	 * @return
	 */
	public int size() {
		return contacts.size();
	}
}
