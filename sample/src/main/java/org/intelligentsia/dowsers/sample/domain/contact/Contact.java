package org.intelligentsia.dowsers.sample.domain.contact;

import org.intelligentsia.dowsers.annotation.Property;
import org.intelligentsia.dowsers.domain.AggregateFactory;
import org.intelligentsia.dowsers.domain.DomainEntity;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Contact extends DomainEntity {

	@Property
	private final String name;

	/**
	 * Build a new instance of <code>Contact</code>
	 * 
	 * @param aggregateFactory
	 * @param name
	 */
	public Contact(AggregateFactory aggregateFactory, String name) {
		super(aggregateFactory);
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
