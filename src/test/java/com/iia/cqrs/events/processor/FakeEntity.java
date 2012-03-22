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
public class FakeEntity extends Entity {

	private long handledCounter = 0;

	/**
	 * Build a new instance of FakeEntity.
	 */
	public FakeEntity() {
	}

	/**
	 * Build a new instance of FakeEntity.
	 * 
	 * @param identifier
	 */
	public FakeEntity(Identifier identifier) {
		super(identifier);
	}

	/**
	 * Build a new instance of FakeEntity.
	 * 
	 * @param domainEventBusInvoker
	 */
	public FakeEntity(EventProcessor domainEventBusInvoker) {
		super(domainEventBusInvoker);
	}

	/**
	 * Build a new instance of FakeEntity.
	 * 
	 * @param eventProcessor
	 * @param identifier
	 * @throws NullPointerException
	 */
	public FakeEntity(EventProcessor eventProcessor, Identifier identifier) throws NullPointerException {
		super(eventProcessor, identifier);
	}

	@DomainEventHandler
	public Integer badReturnType(FakeDomainEventA a) {
		return null;
	}

	@DomainEventHandler
	public void wrongParameterNumber(FakeDomainEventA a, String hell) {
	}

	@DomainEventHandler
	public void wrongParameterType(String devil) {
	}

	@DomainEventHandler
	protected void theGoodOne(FakeDomainEventA a) {
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
