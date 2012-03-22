/**
 * 
 */
package com.iia.cqrs.events.processor;

import com.iia.cqrs.Entity;
import com.iia.cqrs.Identifier;
import com.iia.cqrs.annotation.DomainEventHandler;

/**
 * FakeEntity.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class FakeBeautifullEntity extends Entity {

	private long handledCounter = 0;

	/**
	 * Build a new instance of FakeEntity.
	 */
	public FakeBeautifullEntity() {
	}

	/**
	 * Build a new instance of FakeEntity.
	 * 
	 * @param identifier
	 */
	public FakeBeautifullEntity(final Identifier identifier) {
		super(identifier);
	}

	@DomainEventHandler
	protected void theGoodOne(final FakeDomainEventA a) {
		// so simple
		handledCounter++;
	}

	/**
	 * @return the handledCounter
	 */
	public long getHandledCounter() {
		return handledCounter;
	}
}
