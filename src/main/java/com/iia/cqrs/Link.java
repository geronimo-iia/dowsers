/**
 * 
 */
package com.iia.cqrs;

import java.util.UUID;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Link<T extends Entity> {

	private UUID identity;

	public Link(T entity) {
		super();
		this.identity = entity.getIdentity();
	}

	public UUID getIdentity() {
		return this.identity;
	}

}
