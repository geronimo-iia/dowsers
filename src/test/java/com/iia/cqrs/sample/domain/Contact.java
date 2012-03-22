package com.iia.cqrs.sample.domain;

import com.iia.cqrs.Entity;
import com.iia.cqrs.annotation.DomainEntity;
import com.iia.cqrs.annotation.Property;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
@DomainEntity
public class Contact extends Entity {

	@Property
	private final String name;

	/**
	 * Build a new instance of <code>Contact</code>
	 */
	public Contact(final String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
