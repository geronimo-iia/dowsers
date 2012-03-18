/**
 * 
 */
package com.iia.cqrs.events;

import java.util.UUID;

import com.iia.cqrs.Identifier;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public abstract class DomainEvent {
	private final UUID id;
	private final Identifier identifier;

	public DomainEvent(Identifier identifier) {
		this(UUID.randomUUID(), identifier);
	}

	public DomainEvent(UUID id, Identifier identifier) {
		super();
		this.id = id;
		this.identifier = identifier;
	}

 
	public UUID getId() {
		return this.id;
	}
	
	public Identifier getIdentifier() {
		return this.identifier;
	}

}
