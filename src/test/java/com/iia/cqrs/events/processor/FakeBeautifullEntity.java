/**
 * 
 */
package com.iia.cqrs.events.processor;

import com.iia.cqrs.annotation.DomainEventHandler;
import com.iia.cqrs.domain.Entity;
import com.iia.cqrs.domain.Identifier;

/**
 * FakeEntity.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class FakeBeautifullEntity extends Entity {

	private long handledCounter = 0;

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
