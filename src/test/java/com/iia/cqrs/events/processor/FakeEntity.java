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
public class FakeEntity extends Entity {

	private long handledCounter = 0;

	/**
	 * Build a new instance of FakeEntity.
	 * 
	 * @param identifier
	 */
	public FakeEntity(final Identifier identifier) {
		super(identifier);
	}

	@DomainEventHandler
	public Integer badReturnType(final FakeDomainEventA a) {
		return null;
	}

	@DomainEventHandler
	public void wrongParameterNumber(final FakeDomainEventA a, final String hell) {
	}

	@DomainEventHandler
	public void wrongParameterType(final String devil) {
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
